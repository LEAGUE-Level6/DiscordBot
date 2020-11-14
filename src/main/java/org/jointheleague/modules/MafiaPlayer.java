package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class MafiaPlayer extends CustomMessageCreateListener {

	private static final String COMMAND = ".playMafia";
	private static final String vote = ".mafia-vote";
	private static final String rules = ".mafia-rules";
	private static boolean Playing = false;


	
	public MafiaPlayer(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Starts a game of Mafia. If you're unsure what Mafia is, check out this link for an explantion: https://en.wikipedia.org/wiki/Mafia_(party_game) (e.g. !playMafia). Make sure you have the option to allow server members to private message you on.");
	}

	int p = 7;  
	HashMap<String, Long> names = new HashMap<String, Long>();
	HashMap<String, Long> Mafia = new HashMap<String, Long>();
	HashMap<String, Long> Villagers = new HashMap<String, Long>();

	
	@Override
	public void handle(MessageCreateEvent event) {
		String msg = event.getMessageContent();
				
		
		if (!Playing) {
			if (msg.equalsIgnoreCase(COMMAND)) {
				event.getChannel().sendMessage("Welcome To Mafia! Please enter the number of players that will be participating (7-16 players)");
			}
			
			if (msg.equals("7") || msg.equals("8") || msg.equals("9") || msg.equals("10") || msg.equals("11") || msg.equals("12") || msg.equals("13") || msg.equals("14") || msg.equals("15") || msg.equals("16")) {
				playernum(event);
			}
			
			
			if (msg.equalsIgnoreCase("confirm players")) {
				for (int i = 1; i <= p; i++) {
					event.getChannel().sendMessage("Player " + i + ", please type below:   Player " + i);
				}
			} 
			
			if (msg.equalsIgnoreCase("Player 1")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 2")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 3")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 4")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 5")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 6")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 7")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 8")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 9")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 10")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 11")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 12")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 13")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 14")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 15")) {
				confirmPlayers(event);
			}if (msg.equalsIgnoreCase("Player 16")) {
				confirmPlayers(event);
			}
			
			
			if (names.size() == p) {  //names.size-1
				System.out.println(names.values());
				event.getChannel().sendMessage("Setup Done");
				Playing = true;
			}
		}
		
				
		
		if (Playing) {
			if (msg.equalsIgnoreCase("Setup Done")) {
				setRoles(event);
			}
			
			if (msg.equalsIgnoreCase("fdas")) {
				//event.getMessageAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("hello")).queue();
				try {
					event.getChannel().sendMessage("hello");
					event.getMessageAuthor().asUser().get().openPrivateChannel().get().sendMessage("hello");
					event.getMessageAuthor().
					//event.getMessageAuthor().asUser().get().getPrivateChannel().flatMap(channel -> channel.sendMessage("heloooo"));
					//User user = new User();
//					TextChannel textChannel = event.getGuild().getTextChannelsByName("CHANNEL_NAME",true).get(0);
//					textChannel.sendMessage("MESSAGE").queue();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
						
			
			if (event.getMessageContent().equalsIgnoreCase("asdf")) {
				System.out.println("asdf1 " + names.size());
				System.out.println("asdf2 " + names.values().toString());
				Playing = false;
			}
			
		}
	}
	
	//Get user input of number of players playing
	private void playernum(MessageCreateEvent event) {
		String msg = event.getMessageContent();
		try {
			//p = Integer.parseInt(msg);
			p=1;
			event.getChannel().sendMessage("Please type \"confirm players\" (no quotations) to continue.");
		} catch (NumberFormatException e) {
			event.getChannel().sendMessage("Invalid response. Please enter in a number.");
		}
	}
		
	private void confirmPlayers(MessageCreateEvent event) {
		Server server = event.getServer().get();		
		names.put(event.getMessageAuthor().asUser().get().getDisplayName(server), event.getMessageAuthor().asUser().get().getId());		
	}
	
	private void setRoles(MessageCreateEvent event) {
		//chooses mafia
		for (int i = 0; i < (int) names.size()/3; i++) {
			int r = new Random().nextInt(names.size());
			Mafia.put((String) names.keySet().toArray()[r], names.get(names.keySet().toArray()[r]));
			names.remove(names.get(names.keySet().toArray()[r]));
		}
		
		//chooses doctor
		int r = new Random().nextInt(names.size());
		Mafia.put((String) names.keySet().toArray()[r], names.get(names.keySet().toArray()[r]));
		names.remove(names.get(names.keySet().toArray()[r]));
				
		//chooses detective
		int r2 = new Random().nextInt(names.size());
		Mafia.put((String) names.keySet().toArray()[r2], names.get(names.keySet().toArray()[r2]));
		names.remove(names.get(names.keySet().toArray()[r2]));
	}
	
	
}
