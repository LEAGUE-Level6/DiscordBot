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
import org.javacord.api.event.channel.user.PrivateChannelCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class MafiaPlayer extends CustomMessageCreateListener {

	private static final String COMMAND = ".playMafia";
	private static final String vote = ".mafia-vote";
	private static final String instructions = ".mafia-intructions";
	private static final String end = ".mafia-end";
	private static final String ready = ".mafia-ready";
	
	private static final String mafiaKill = ".mafia-kill";
	private static final String detectiveInspect = ".mafia-inspect";
	private static final String doctorSave = ".mafia-save";
	
	private static boolean Playing = false;
	private static boolean mafiatime = false;
	private static boolean detectivetime = false;
	private static boolean doctortime = false;

	ArrayList<org.javacord.api.entity.user.User> names = new ArrayList<org.javacord.api.entity.user.User>();
	ArrayList<org.javacord.api.entity.user.User> backup = new ArrayList<org.javacord.api.entity.user.User>();

	ArrayList<org.javacord.api.entity.user.User> Mafia = new ArrayList<org.javacord.api.entity.user.User>();
	ArrayList<org.javacord.api.entity.user.User> Doctor = new ArrayList<org.javacord.api.entity.user.User>();
	ArrayList<org.javacord.api.entity.user.User> Detective = new ArrayList<org.javacord.api.entity.user.User>();
	ArrayList<org.javacord.api.entity.user.User> Villagers = new ArrayList<org.javacord.api.entity.user.User>();
	
	String[] beginningStorylines = {"begin1", "begin2"};
	String[] deathStorylines = {"d1", "d2", "d3", "d4"};
	
	int p = 0;
	boolean assigningPlayers = false;
	int currentPlayer = 1;
	
	long bot;
	
	public MafiaPlayer(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Starts a game of Mafia //(e.g. !playMafia 8). **Make sure all players enable messages from server members. Bot must also have permission to message server members. ");
	}

	
	@Override
	public void handle(MessageCreateEvent event) { //occurs whenever a msg is sent
		String msg = event.getMessageContent();
				
		//!Playing
		if (!Playing) {
			if (msg.contains(COMMAND)) {
				p = Integer.parseInt(msg.replace(COMMAND + " ", "").trim());
				if (7 <= p && p <= 16) {
					event.getChannel().sendMessage("Welcome To Mafia! Starting game with " + p + " players...\n");
					event.getChannel().sendMessage("Player 1, please type below:   Player 1");
					assigningPlayers = true;
					return;
				} 
			}			
			
			if (msg.equals("Player 1, please type below:   Player 1")) {
				bot = event.getMessageAuthor().getId();
			}
			
			if (assigningPlayers) {
				System.out.println(event.getMessageAuthor().getId() + " " + bot);
				if (event.getMessageAuthor().getId() != bot) {
					confirmPlayers(event);
					currentPlayer++;
					if (currentPlayer > p) {
						assigningPlayers = false;
					} else {
						event.getChannel().sendMessage("Player " + currentPlayer + ", please type below:   Player " + currentPlayer);
					}
				}
			}
		}
	}
			
//			if (msg.equalsIgnoreCase("Player 1")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 2")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 3")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 4")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 5")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 6")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 7")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 8")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 9")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 10")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 11")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 12")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 13")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 14")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 15")) {
//				confirmPlayers(event);
//			}if (msg.equalsIgnoreCase("Player 16")) {
//				confirmPlayers(event);
//			}
//			
//			
//			if (names.size() == p) {  
//				System.out.println("jsutincase" + names.toString());
//				Playing = true;
//			}
//		}
//		
//		
//		boolean mafia = false;
//		
//		//Playing		
//		boolean start = true;
//		if (Playing) {
//			if (start) {
//				setRoles(event);
//				start = false;
//				mafia = true;
//			}
//			
//				
//			if (msg.equalsIgnoreCase("list")) {
//				System.out.println("n" + names.toString());
//				System.out.println("\n");
//				System.out.println("m" + Mafia.toString());
//				System.out.println("doc" + Doctor.toString());
//				System.out.println("d" + Detective.toString());
//				System.out.println("v" + Villagers.toString());
//			}			
//			
//			//run game
//			while (mafia) {
//				Mafia(event);
//				if (Mafia.isEmpty()) {
//					event.getChannel().sendMessage("Villagers win!\n" + Villagers.toString());	
//					mafia = false;
//				} else if (Villagers.size() == 1) {
//					event.getChannel().sendMessage("Mafia win!\n" + Mafia.toString());	
//					mafia = false;
//				} else {
//					System.out.println("not there yet");
//					mafia = false;
//				}
//			}
//			
//			if (msg.equalsIgnoreCase("end")) {
//				Playing = false;
//			}
//		} 
//		
//		//Playing ends
//		
//		
//		//instructions (AT)			
//		if (msg.equalsIgnoreCase(instructions)) {
//			EmbedBuilder instructions = new EmbedBuilder();
//			
//			instructions.setColor(new Color(255, 40, 20));
//			instructions.setTitle("Mafia Game Instructions");
//			instructions.setDescription(
//					"This is a version of the game Mafia. \n" + "If you need to know the rules, enter the command **" + "" + "**\n");
////			instructions.addField("To play the game, use these commands:", 
////							"**" + COMMAND + "** - start the game\n" +
////							...
////			
////							"**" + again + "** - used only directly after the last game ends, it allows you to play a new game in quick succession");
//		}
//		
//		//end game (AT)
//		if (msg.equalsIgnoreCase(end)) {
//			mafia = false;
//			Playing = false;
//		}
//	}
	
	
	
//	//Get user input of number of players
//	private void playernum(MessageCreateEvent event) {
//		try {
//			p = 1; //delete
//		} catch (NumberFormatException e) {
//			event.getChannel().sendMessage("Invalid response. Please enter in a number.");
//		}
//	}
	
	//Get user input to confirm players
	private void confirmPlayers(MessageCreateEvent event) {
		System.out.println(event.getMessageContent());
		names.add(event.getMessageAuthor().asUser().get());		
		System.out.println(names.size() + " " + names.get(names.size()-1));
	}
	
	//Randomly assign roles
	private void setRoles(MessageCreateEvent event) {
		System.out.println("setting roles");

		backup = names;
		
		//chooses mafia
		for (int i = 0; i < ((int) backup.size()/4)+1; i++) {  //fix back to i < (int) backup.size()/3
			int r = new Random().nextInt(backup.size());
			Mafia.add(backup.get(r));
			//backup.remove(backup.get(r));
		}
		
		//chooses doctor
		int r2 = new Random().nextInt(backup.size());
		Doctor.add(backup.get(r2));
		Villagers.add(backup.get(r2));
		//backup.remove(backup.get(r2));
				
		//chooses detective
		int r3 = new Random().nextInt(backup.size());
		Detective.add(backup.get(r3));
		Villagers.add(backup.get(r3));
		//backup.remove(backup.get(r3));
		
		backup = names;
		
		System.out.println("n" + names.toString());
		System.out.println("m" + Mafia.toString());
		System.out.println("doc" + Doctor.toString());
		System.out.println("d" + Detective.toString());
		System.out.println("v" + Villagers.toString());
		
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////
	
	//GAME
	int round = 1;
	private void Mafia(MessageCreateEvent event) {
		System.out.println("Round " + round);
		
		String msg = event.getMessageContent();
		boolean start2 = false;
		boolean discussing = false;
		if (round == 1) {
			event.getChannel().sendMessage(beginningStorylines[(int) new Random().nextInt(beginningStorylines.length)]);
		}
		
		
		if (start2) {
			event.getChannel().sendMessage("The night cycle now begins.");
			mafiatime = true;
			start2 = false;
		}
		
		
		//get Mafia input
		if (mafiatime) {
			System.out.println("maf");
			boolean replied = false;
			try {
				org.javacord.api.entity.user.User user = null;
				Mafia.get(0).openPrivateChannel().get().sendMessage("Who would you like to kill?");
				if (msg.startsWith(mafiaKill) && event.getMessageAuthor() == Mafia.get(0)) {
					for (int i1 = 0; i1 < names.size(); i1++) {
						if (names.get(i1).getName().equalsIgnoreCase(msg.replaceAll(" ", "").replace(mafiaKill,""))) {
							user = names.get(i1);
						}
					}
					System.out.println("mk");
					kill(event, user);
					doctortime = true;
					mafiatime = false;
				} 
				
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		} 
		
		//get Doctor input
		if (doctortime) {
			System.out.println("doc");
			try {
				org.javacord.api.entity.user.User user = null;
				Doctor.get(0).openPrivateChannel().get().sendMessage("Who would you like to save?");
				if (msg.startsWith(doctorSave) && event.getMessageAuthor() == Doctor.get(0)) {
					for (int i1 = 0; i1 < names.size(); i1++) {
						if (names.get(i1).getName() == msg.replaceAll(" ", "").replace(doctorSave,"")) {
							user = names.get(i1);
						}
					}
					save(event, user);
					detectivetime = true;
					doctortime = false;
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		//get Detective input
		if (detectivetime) {
			System.out.println("dec");
			try {
				org.javacord.api.entity.user.User user = null;
				Detective.get(0).openPrivateChannel().get().sendMessage("Who would you like to inspect?");
				if (msg.startsWith(detectiveInspect)) {
					for (int i1 = 0; i1 < names.size(); i1++) {
						if (names.get(i1).getName() == msg.replaceAll(" ", "").replace(detectiveInspect,"")) {
							user = names.get(i1);
						}
					}
					inspect(event, user);
					detectivetime = false;
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		
		//vote
		if (msg.startsWith(vote)) {
			org.javacord.api.entity.user.User user = null;
			for (int i = 0; i < names.size(); i++) {
				if (names.get(i).getName() == msg.replaceAll(" ", "").replace(vote,"")) {
					user = names.get(i);
				}
			}
			vote(event, user);		
		}
		
		round+=1;
		System.out.println("end");
	}
	
	//////////////////////////////////////////////////////////////////////////////
	
	
	private void vote(MessageCreateEvent event, org.javacord.api.entity.user.User user) {
		try {
			Villagers.remove(user);
		} catch (Exception e) {e.printStackTrace();}
		try {
			Mafia.remove(user);
		} catch (Exception e) {e.printStackTrace();}
	
		event.getChannel().sendMessage(user.getName() + " has been voted out!");
	}
	
	
	//kill
	org.javacord.api.entity.user.User killed = null;
	private void kill(MessageCreateEvent event, org.javacord.api.entity.user.User user) {
		killed = user;
		Villagers.remove(user);
	}
	
	//save
	private void save(MessageCreateEvent event, org.javacord.api.entity.user.User user) {
		if (killed == user) {
			for (int i = 0; i < names.size(); i++) {
				if (names.get(i) == user) {
					Villagers.add(names.get(i));
				}
			}
		} 		
	}

	//inspect
	private void inspect(MessageCreateEvent event, org.javacord.api.entity.user.User c) throws InterruptedException, ExecutionException {
		for (int i = 0; i < Mafia.size(); i++) {
			if (Mafia.get(i) != c) {
				if (Villagers.contains(c)) {
					Detective.get(0).openPrivateChannel().get().sendMessage(Villagers.get(i).getName() + " is a Villager.");
				}
			} else {
				Detective.get(0).openPrivateChannel().get().sendMessage(Villagers.get(i).getName() + " is a Mafia.");
			}
		} 
	}
	
	
}
