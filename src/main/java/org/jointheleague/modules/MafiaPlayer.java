package org.jointheleague.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class MafiaPlayer extends CustomMessageCreateListener {

	private static final String COMMAND = ".playMafia";
	private static final String vote = ".mafia-vote";
	private static final String instructions = ".mafia-intructions";
	private static boolean Playing = false;


	
	public MafiaPlayer(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Starts a game of Mafia. If you're unsure what Mafia is, check out this link for an explantion: https://en.wikipedia.org/wiki/Mafia_(party_game) (e.g. !playMafia). **Make sure all players have the option to allow server members to message you on. Bot must also have permission to message others. ");
	}

	int p = 7;  
	HashMap<String, Long> names = new HashMap<String, Long>();
	HashMap<String, Long> Mafia = new HashMap<String, Long>();
	HashMap<String, Long> Detective = new HashMap<String, Long>();
	HashMap<String, Long> Doctor = new HashMap<String, Long>();
	HashMap<String, Long> Villagers = new HashMap<String, Long>();
	String[] deathStorylines = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

	
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
			
			if (msg.equalsIgnoreCase("1")) {
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
			
			
			if (names.size()-1 == p) {  
				System.out.println(names.values());
				event.getChannel().sendMessage("Setup Done");
				Playing = true;
			}
		}
		
				
		
		if (Playing) {
			if (msg.equalsIgnoreCase("Setup Done")) {
				setRoles(event);
				System.out.println("setroles");
			}
			
			if (msg.equalsIgnoreCase("list")) {
				//try {
					event.getChannel().sendMessage(Doctor.keySet().toString());
					event.getChannel().sendMessage(Detective.keySet().toString());
					event.getChannel().sendMessage(Mafia.keySet().toString());
					event.getChannel().sendMessage(Villagers.keySet().toString());
					
					//event.getMessageAuthor().asUser().get().openPrivateChannel().get().sendMessage("hello");
					
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			
			//vote
			if (msg.startsWith(vote)) {
				String username = msg.replaceAll(" ", "").replace(vote,"");
				vote(event, username);		
			}
			
			
			
			
			
			
			//mafia command
			
			
			//doctor command
			
			
			//detective command
			if (msg.equalsIgnoreCase(anotherString)) {
				
			}
		}
		
		//instructions			
		if (msg.equalsIgnoreCase(instructions)) {
			EmbedBuilder instructions = new EmbedBuilder();
			
			instructions.setColor(new Color(255, 40, 20));
			instructions.setTitle("Mafia Game Instructions");
			instructions.setDescription(
					"This is a version of the game Mafia. \n" + "If you need to know the rules, enter the command **" + "" + "**\n");
//			instructions.addField("To play the game, use these commands:", 
//							"**" + COMMAND + "** - start the game\n" +
//							"**" + hit + " ** - hit\n" +
//							"**" + stand + "** - stand\n" +
//							"**" + getBoard + "** - get the current board if it got lost/deleted\n"+
//							"**" + end + "** - end the current game\n" +
//							"**" + again + "** - used only directly after the last game ends, it allows you to play a new game in quick succession");
		}
	}
	
	//Get user input of number of players
	private void playernum(MessageCreateEvent event) {
		String msg = event.getMessageContent();
		try {
			p = Integer.parseInt(msg);
			event.getChannel().sendMessage("Please type \"confirm players\" (no quotations) to continue.");
		} catch (NumberFormatException e) {
			event.getChannel().sendMessage("Invalid response. Please enter in a number.");
		}
	}
		
	private void confirmPlayers(MessageCreateEvent event) {
		Server server = event.getServer().get();		
		names.put(event.getMessageAuthor().asUser().get().getDisplayName(server), event.getMessageAuthor().asUser().get().getId());		
		System.out.println(names.size());
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
		Doctor.put((String) names.keySet().toArray()[r], names.get(names.keySet().toArray()[r]));
		names.remove(names.get(names.keySet().toArray()[r]));
				
		//chooses detective
		int r2 = new Random().nextInt(names.size());
		Detective.put((String) names.keySet().toArray()[r2], names.get(names.keySet().toArray()[r2]));
		names.remove(names.get(names.keySet().toArray()[r2]));
	}
	
	private void Mafia(MessageCreateEvent event) {
		String msg = event.getMessageContent();
		
	}
	
	private void vote(MessageCreateEvent event, String username) {
		//try {
			Villagers.remove(username);
		//} catch () {
			Detective.remove(username);
		//} catch () {
			Doctor.remove(username);
		//}
		
	}
	
	
}
