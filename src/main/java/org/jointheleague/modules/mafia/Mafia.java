package org.jointheleague.modules.mafia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.CustomMessageCreateListener;

public class Mafia extends CustomMessageCreateListener {

	private ArrayList<Player> players;
	
	private Stack<String> roles;
	
	private Channel mafia_channel;
	
	private boolean GAME_RUNNING = false;
	private int GAME_STATE; //0 = choosing players, 1 = night, 2 = accuse, 3 = trial
	private int num_guilty; //count to see how many people voted guilty
	private int num_votes; //count for total number of people voting
	
	private int NUM_PLAYERS; //number of players alloted per game
	private final int NUM_ACCUSATIONS = 2; //number of accusations necessary to move on to trial period
	
	Player ap;
	
	
	public Mafia(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().startsWith("!begin mafia ") && !GAME_RUNNING)
		{
			NUM_PLAYERS = Integer.parseInt(event.getMessageContent().replace("!begin mafia", "").replace(" ", ""));
			event.getChannel().sendMessage("A game of mafia has started! Waiting for " + NUM_PLAYERS + " players to join.");
			
			GAME_RUNNING = true;
			GAME_STATE = 0;
			
			players = new ArrayList<Player>();
			roles = new Stack<String>();
			
			num_guilty = 0;
			num_votes = 0;
			GAME_STATE = 0;
			
			fillRoles();
			
		}
		
		if(event.getMessageContent().equals("!mafia help"))
		{
			event.getChannel().sendMessage("Commands: mafia help, begin mafia <player number>, stop mafia, join mafia, mafia player list, kill <player>, accuse <player>, vote guilty, vote innocent, remove accusation"); 
		}
		
		if(GAME_RUNNING)
		{
			if(event.getMessageContent().equals("!stop mafia"))
			{
				GAME_RUNNING = false;
				event.getChannel().sendMessage("The game has been cancelled!");
			}
			
			if(event.getMessageContent().equals("!mafia player list"))
			{
				String ps = "";
				for(Player p: players)
				{
					ps = ps + p.getName().getDisplayName() + ", ";
				}
				event.getChannel().sendMessage("The alive players are " + ps);
			}
			
			else if(GAME_STATE == 0) //join
			{
				if(event.getMessageContent().equals("!join mafia")) {joinGame(event);}
			}
			else
			{
				for(Player p: players)
				{
					if(p.getName().equals(event.getMessageAuthor()))
					{
						if(GAME_STATE == 1) //night
						{
							night(p, event);
						}
						
						else if(GAME_STATE == 2) //accusatory period
						{
							accuse(event, p);
						}
						
						else if(GAME_STATE == 3) //trial period
						{
							trial(event);
						}
						break;
					}
				
				}
			}
		}
	}

	public void resetAccusations()
	{
		for(Player p: players) {
			p.hasAccused = false;
			p.setAccusations(0);
			
		}
		
		num_guilty = 0;
		num_votes = 0;
	}
	
	public void joinGame(MessageCreateEvent event)
	{
		boolean hasJoined = false;
		for(Player p: players)
		{
			if(p.getName().equals(event.getMessageAuthor()))
			{
				hasJoined = true;
				break;
			}
		}
		if(players.size() < NUM_PLAYERS && !hasJoined)
		{
			players.add(new Player(event.getMessageAuthor(), roles.pop()));
			
			if(players.size() == NUM_PLAYERS) {
				GAME_STATE = 1;
				event.getChannel().sendMessage("All players have joined the game! Generating roles now. Please check your private messages.");
				
				//find mafia members
				String mafia_string = "";
				for(Player p: players)
				{
					if (p.getRole().equals("mafia")) {mafia_string = mafia_string + p.getName().getDisplayName() + ", ";}
				}
				mafia_string = mafia_string.substring(0, mafia_string.length() - 2);
				
				for(Player p: players)
				{
					p.getName().asUser().get().sendMessage("You are a " + p.getRole() + ".");
					if(p.getRole().equals("mafia"))
					{
						p.getName().asUser().get().sendMessage("The other mafia members are " + mafia_string + ". Create a channel if you wish. Use the command !kill <player> in the main channel to kill the player.");
					}
				}
				
			}
			else if (players.size() == NUM_PLAYERS - 1) {event.getChannel().sendMessage("A player has joined the game! Waiting for " + (NUM_PLAYERS - players.size()) + " more player.");} 
			else {event.getChannel().sendMessage("A player has joined the game! Waiting for " + (NUM_PLAYERS - players.size()) + " more players.");} 
		}
	}
	
	public void night(Player p, MessageCreateEvent event)
	{
		if(event.getMessageContent().contains("!kill ") && p.getRole().equals("mafia"))
		{
			String killed = event.getMessageContent().replace("!kill ", "");
			event.deleteMessage();
			for(Player p1: players)
			{
				if(p1.getName().getDisplayName().equals(killed))
				{
					GAME_STATE = 2;
					
					resetAccusations();
					players.remove(p1);
					
					if(checkWin(event) == 0) {
						event.getChannel().sendMessage("Oh no! " + killed + " has been killed! Who should be accused?");
					}
					break;
				}
			}
		}
		
		else if(event.getMessageContent().contains("!kill ") && !p.getRole().equals("mafia"))
		{
			event.deleteMessage();
		}
	}
	
	public void accuse(MessageCreateEvent event, Player p)
	{
		if(event.getMessageContent().contains("!accuse ") && !p.hasAccused)
		{
			String accused = event.getMessageContent().replace("!accuse ", "");
			event.deleteMessage();
			for(Player p1: players)
			{
				if(p1.getName().getDisplayName().equals(accused))
				{
					p1.setAccusations(p1.getAccusations()+1);
					event.getChannel().sendMessage(p.getName().getDisplayName() + " has accused " + p1.getName().getDisplayName() + " of being the killer!");
					if(p1.getAccusations() >= NUM_ACCUSATIONS)
					{
						GAME_STATE = 3;
						ap = p1;
						event.getChannel().sendMessage(p1.getName().getDisplayName() + " has been put on trial for murder. Please vote guilty or innocent.");
					}
				}
			}
		}
		
		if(event.getMessageContent().equals("!remove accusation") && p.hasAccused){p.removeAccusation();}
	}
	
	public void trial(MessageCreateEvent event)
	{
		if(event.getMessageContent().equals("!vote guilty")) {
			num_guilty++;
			num_votes++;
			if(num_guilty >= players.size()/2)
			{
				if(checkWin(event) == 0)
				{
					players.remove(ap);
					event.getChannel().sendMessage(ap.getName().getDisplayName() + " has been convicted of murder! He was " + ap.getRole() + ". Night falls again.");
					GAME_STATE = 1;
				}
			}
			
			else if(num_votes == players.size())
			{
				GAME_STATE = 2;
				resetAccusations();
			}
			
			
		}
		
	if(event.getMessageContent().equals("!vote innocent")) {
		num_votes++;
		
		if(num_votes == players.size())
		{
			GAME_STATE = 2;
			resetAccusations();
		}
	}
	
	}
	
	public int checkWin(MessageCreateEvent event)
	{
		int num_innocent = 0;
		int num_mafia = 0;
		for(Player p: players)
		{
			if(p.getRole().equals("mafia")) {num_mafia++;}
			else if(p.getRole().equals("innocent")) {num_innocent++;}
		}
		
		if(num_innocent == 0) {event.getChannel().sendMessage("Mafia has won!"); GAME_RUNNING = false; return -1;}
		else if(num_mafia == 0) {event.getChannel().sendMessage("Innocents have won!"); GAME_RUNNING = false; return 1;}
		return 0;
	}
	
	public void fillRoles()
	{
		//ratio should be roughly 2 mafia to 5 innocents
		int mafia_num = (3*NUM_PLAYERS)/10;
		roles.clear();
		
		for(int i = 0; i < (NUM_PLAYERS - mafia_num); i++) {roles.add("innocent");}
		for(int i = 0; i < mafia_num; i++) {roles.add("mafia");}
		Collections.shuffle(roles);
	}
}
