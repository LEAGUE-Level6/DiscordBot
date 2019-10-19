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
	RandomText rt;
	
	private boolean GAME_RUNNING = false;
	private int GAME_STATE; //0 = choosing players, 1 = night, 2 = accuse, 3 = trial
	private int num_guilty; //count to see how many people voted guilty
	private int num_votes; //count for total number of people voting
	
	private int NUM_PLAYERS; //number of players alloted per game
	private final int NUM_ACCUSATIONS = 2; //number of accusations necessary to move on to trial period
	
	Player ap;
	
	
	public Mafia(String channelName) {
		super(channelName);
		rt = new RandomText();
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageAuthor().isUser())
		{
			
			if(event.getMessageContent().equals("!mafia help"))
			{
				event.getChannel().sendMessage("Use the command \"!begin mafia <player number>\" to start a new game. Conversely, \"!stop mafia\" ends the game prematurely."
						+ " To join an existing game, use the command \"!join mafia\"."
						+ "\nThe game exists in three phases: night, accusation, and trial. "
						+ "\nAt night, mafia members can use the command \"!kill <player name>\" to kill a player of their choice. Only one player can be killed per night phase. "
						+ "\nAfter night comes the accusation phase. In this phase, players can discuss who they believe the killer to be. Each player may accuse one other player through the command \"!accuse <player name>\". "
						+ "To remove a past accusation, use the command \"!remove accusation\". This phase ends when a player has accumulated two accusations."
						+ "\nAfter this, the accused player is put on trial. Each player can either \"!vote guilty\" or \"!vote innocent\". "
						+ "If the majority of players vote guilty, then the accused is put to death, and the game returns to night phase. Otherwise, it returns to the accusation phase."
						+ "\nOverall, the command \"!mafia player list\" can be used to see the currently alive players. Mafia wins if all innocents are killed, and innocents win if all mafia members are killed.");
			}
			
			else if(event.getMessageContent().startsWith("!begin mafia ") && !GAME_RUNNING)
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
			
			else if(GAME_RUNNING)
			{
				if(event.getMessageContent().equals("!stop mafia"))
				{
					GAME_RUNNING = false;
					event.getChannel().sendMessage("The game has been cancelled!");
				}
				
				else if(event.getMessageContent().equals("!mafia player list"))
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
	}

	public void resetAccusations()
	{
		for(Player p: players) {
			p.removeAccusation();
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
						p.getName().asUser().get().sendMessage("The other mafia members are " + mafia_string + ". You can use the command \"!kill <player name>\" in the main channel to kill <player name>. This can be done with or without talking to the other mafia members.");
					}
				}
				
				event.getChannel().sendMessage("The first night falls. Everyone goes to sleep, unwary of what is going to happen.");
				
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
					
					if(checkWin(event) == 0) {
						event.getChannel().sendMessage(rt.murderText(p1));
					}
					
					players.remove(p1);
					
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
						event.getChannel().sendMessage("After discussion, it appears that " + p1.getName().getDisplayName() + " looks the most suspicious. Against their will, and pleading the entire way, they are dragged to the courtroom.");
					}
				}
			}
		}
		
		if(event.getMessageContent().equals("!remove accusation")){p.removeAccusation();}
	}
	
	public void trial(MessageCreateEvent event)
	{
		if(event.getMessageContent().equals("!vote guilty") && !event.getMessageAuthor().equals(ap.getName())) {
			num_guilty++;
			num_votes++;
			if(num_guilty >= players.size()/2)
			{
				if(checkWin(event) == 0)
				{
					players.remove(ap);
					event.getChannel().sendMessage("In the end, " + ap.getName().getDisplayName() + " was found guilty and sentenced to death. You were all satisified. After their death new evidence cropped up that "  + ap.getName().getDisplayName() + " was " + ap.getRole() + ". Night falls again and everyone goes home, a little more wary than the night before.");
					GAME_STATE = 1;
				}
			}
			
			else if(num_votes == players.size())
			{
				GAME_STATE = 2;
				event.getChannel().sendMessage("In the end, " + ap.getName().getDisplayName() + " was found innocent and set free. Everyone looks for the next suspect.");
				resetAccusations();
			}
			
			
		}
		
	if(event.getMessageContent().equals("!vote innocent") && !event.getMessageAuthor().equals(ap.getName())) {
		num_votes++;
		
		if(num_votes == players.size())
		{
			GAME_STATE = 2;
			event.getChannel().sendMessage("In the end, " + ap.getName().getDisplayName() + " was found innocent and set free. Everyone looks for the next suspect.");
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
		
		if(num_innocent == 0) {event.getChannel().sendMessage("The last innocent person perishes, leaving only the mafia. Satisfied, they also depart from the town, seeking their next targets."); GAME_RUNNING = false; return -1;}
		else if(num_mafia == 0) {event.getChannel().sendMessage("With the last mafia member killed, the survivors return to their home wearily. Hopefully this never happens again."); GAME_RUNNING = false; return 1;}
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
