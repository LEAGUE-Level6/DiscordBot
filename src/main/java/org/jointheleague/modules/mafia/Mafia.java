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
	private int num_guilty;
	private int num_votes;
	
	private final int NUM_PLAYERS = 7;
	private final int NUM_ACCUSATIONS = 2;
	
	Player ap;
	
	
	public Mafia(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().equals("!begin mafia"))
		{
			event.getChannel().sendMessage("A game of mafia has started! Waiting for 7 players to join.");
			
			GAME_RUNNING = true;
			GAME_STATE = 0;
			
			players = new ArrayList<Player>();
			roles = new Stack<String>();
			
			num_guilty = 0;
			num_votes = 0;
			
			fillRoles();
			
		}
		
		if(GAME_RUNNING)
		{
			if(GAME_STATE == 0) //join
			{
				if(event.getMessageContent().equals("!join mafia")) {joinGameLoop(event);}
			}
			else
			{
				for(Player p: players)
				{
					if(p.getName().equals(event.getMessageAuthor()))
					{
						if(GAME_STATE == 1 && p.getRole().equals("mafia")) //night
						{
							night(event);
						}
						
						else if(GAME_STATE == 2) //accusatory period
						{
							accuse(event, p);
						}
						
						else if(GAME_STATE == 3) //trial period
						{
							if(event.getMessageContent().equals("!vote guilty")) {
								num_guilty++;
								num_votes++;
								if(num_guilty >= players.size()/2)
								{
									event.getChannel().sendMessage(ap.getName().getDisplayName() + " has been convicted of murder! He was " + ap.getRole() + ". Night falls again.");
									GAME_STATE = 1;
								}
								
								if(num_votes == players.size())
								{
									GAME_STATE = 2;
									//finish resetting stuff next time
								}
								
								
							}
							
						if(event.getMessageContent().equals("!vote innocent")) {
							num_votes++;
							
							//add condition for returning to game state 2
						}
						
						break;
					}
				}
				
				}
			}
		}
	}
	
	public void joinGameLoop(MessageCreateEvent event)
	{
		if(players.size() < NUM_PLAYERS)
		{
			players.add(new Player(event.getMessageAuthor(), roles.pop()));
			
			if(players.size() == NUM_PLAYERS) {
				GAME_STATE = 1;
				event.getChannel().sendMessage("All players have joined the game! Generating roles now.");
				
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
						p.getName().asUser().get().sendMessage("The other mafia members are " + mafia_string + ". Create a channel if you wish.");
					}
				}
				
			}
			else {event.getChannel().sendMessage("A player has joined the game! Waiting for " + (NUM_PLAYERS - players.size()) + " more players.");} 
		}
	}
	
	public void night(MessageCreateEvent event)
	{
		if(event.getMessageContent().contains("!kill "))
		{
			String killed = event.getMessageContent().replace("!kill ", "");
			event.deleteMessage();
			for(Player p1: players)
			{
				if(p1.getName().getDisplayName().equals(killed))
				{
					GAME_STATE = 2;
					for(Player p2: players)
					{
						p2.setAccusations(0);
						p2.hasAccused = false;
						p2.removeAccusation();
					}
					num_guilty = 0;
					
					event.getChannel().sendMessage("Oh no! " + killed + " has been killed! Who should be accused?");
				}
			}
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
					event.getChannel().sendMessage(p.getName().getDisplayName() + " has accused " + p1.getName() + " of being the killer!");
					if(p1.getAccusations() >= NUM_ACCUSATIONS)
					{
						GAME_STATE = 3;
						ap = p1;
						event.getChannel().sendMessage(p1.getName().getDisplayName() + " has been put on trial for murder. Please vote guilty or not guilty.");
					}
				}
			}
		}
		
		if(event.getMessageContent().equals("remove accusation") && p.hasAccused){p.removeAccusation();}
	}
	
	public int winner()
	{
		int num_innocent = 0;
		int num_mafia = 0;
		for(Player p: players)
		{
			if(p.getRole().equals("mafia")) {num_mafia++;}
			else if(p.getRole().equals("innocent")) {num_innocent++;}
		}
		
		if(num_innocent == 0) {return -1;}
		if(num_mafia == 0) {return 1;}
		return 0;
	}
	
	public void fillRoles()
	{
		roles.clear();
		for(int i = 0; i < 5; i++) {roles.add("innocent");}
		for(int i = 0; i < 2; i++) {roles.add("mafia");}
		Collections.shuffle(roles);
	}
}
