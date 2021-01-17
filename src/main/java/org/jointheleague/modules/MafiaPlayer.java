package org.jointheleague.modules;

import java.awt.Color;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.channel.user.PrivateChannelCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.channel.user.PrivateChannelCreateListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jointheleague.discord_bot_example.Bot;
import org.jointheleague.discord_bot_example.BotInfo;
import org.jointheleague.discord_bot_example.Utilities;
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
	
	String[] beginningStorylines = {"beginningStoryline"/*You are all passengers on a plane when suddenly, the engine gets stuck and the plane must take a crash landing. Luckily, the pilot(s) crash the plane onto a small, deserted island in the middle of the ocean, but the pilot is gone. " +
		"Now, you must all work together to survive until help arrives. However, there are imposters among the group. Collaborate together to find these imposters and survive until the end."*/};
	String[] deathStorylines = {" was chased by a flock of angry pelicans.", " went for a swim and never came back.", " got crushed by a rock.", " hit their head too hard on a tree."};
	
	int p = -1;
	boolean assigningPlayers = false;
	int currentPlayer = 2;
	
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
			if (msg.startsWith(COMMAND)) {
				p = Integer.parseInt(msg.replace(COMMAND + " ", "").trim());
				if (7 <= p && p <= 16) {
					event.getChannel().sendMessage("Welcome To Mafia! Starting game with " + p + " players...");
					event.getChannel().sendMessage("Player 1, please type below:   Player 1");
					assigningPlayers = true;
					return;
				} 
			}			
			
			if (msg.startsWith("Welcome To Mafia!")) {
				bot = event.getMessageAuthor().getId();
			}
			
			if (assigningPlayers) {
				if (event.getMessageAuthor().getId() != bot) {
					if (currentPlayer > p) {
						assigningPlayers = false;
					} else {
						event.getChannel().sendMessage("Player " + currentPlayer + ", please type below:");
					}
					confirmPlayers(event);
					currentPlayer++;
				}
			}
			
			if (names.size() == p) {  
				System.out.println("names" + names.toString());
				Playing = true;
			}
		}
		
			
		//Playing		
		if (Playing) {
			boolean settingRoles = true;
			if (settingRoles) {
				setRoles(event);
				settingRoles = false;
			}
			
			//run game
				try {
					Mafia(event);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				if (Mafia.isEmpty()) {
					event.getChannel().sendMessage("Villagers win!\n" + Villagers.toString());	
					endGame();
				} else if (Villagers.size() == 1) {
					event.getChannel().sendMessage("Mafia win!\n" + Mafia.toString());	
					endGame();
				} else {
					System.out.println("not there yet");
					Playing = false;
					names.clear();
				}	
		
		}
		//end of Playing
		
		
		//instructions (can be at any time while MafiaPlayer is running)			
		if (msg.equalsIgnoreCase(instructions)) {
			EmbedBuilder instructions = new EmbedBuilder();
			
			instructions.setColor(new Color(255, 40, 20));
			instructions.setTitle("Mafia Game Instructions");
			instructions.setDescription(
					"This is a version of the game Mafia. \n" + "If you need to know the rules, enter the command **" + "" + "**\n");
//			instructions.addField("To play the game, use these commands:", 
//							"**" + COMMAND + "** - start the game\n" +
//							...
//			
//							"**" + again + "** - used only directly after the last game ends, it allows you to play a new game in quick succession");
		}		
	}
	
	//end the game
	private void endGame(){
		Playing = false;
	}
	
	//Get user input to confirm players
	private void confirmPlayers(MessageCreateEvent event) {
		names.add(event.getMessageAuthor().asUser().get());	
	}
	
	//Randomly assign roles
	private void setRoles(MessageCreateEvent event) {
		System.out.println("setting roles");

		backup = names;
		
		//chooses mafia (can be from 1-3 mafia **every 5 players, one is added**)
		for (int i = 0; i < ((int) backup.size()/5); i++) {  
			int r = new Random().nextInt(backup.size());
			Mafia.add(backup.get(r));
			//backup.remove(backup.get(r));
		}
		
		//chooses doctor (only 1)
		int r2 = new Random().nextInt(backup.size());
		Doctor.add(backup.get(r2));
		Villagers.add(backup.get(r2));
		//backup.remove(backup.get(r2));
				
		//chooses detective (only 1)
		int r3 = new Random().nextInt(backup.size());
		Detective.add(backup.get(r3));
		Villagers.add(backup.get(r3));
		//backup.remove(backup.get(r3));
		
		backup = names;
		
		System.out.println("n" + names.toString() + "\n");
		System.out.println("m" + Mafia.toString());
		System.out.println("doc" + Doctor.toString());
		System.out.println("d" + Detective.toString());
		System.out.println("v" + Villagers.toString());
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////
	
	long pmchannelID;
	DiscordApi api;
	
	int day = 1;
	private void Mafia(MessageCreateEvent event) throws InterruptedException {
		BotInfo n = Utilities.loadBotsFromJson();
//		for (int i = 0; i < 3; i++) {
//			new Bot(n.getToken(), "").connect(false);
//		}
// 		Bot pmBot = new Bot(n.getToken(), "rachel");
//		pmBot.connect(false);
		api = new DiscordApiBuilder().setToken(n.getToken()).login().join();
		MafiaPlayer mp = new MafiaPlayer(channelName);
				
		String msg = event.getMessageContent();
		
		event.getChannel().sendMessage("Day " + day);
				
		if (day == 1) {
			event.getChannel().sendMessage(beginningStorylines[(int) new Random().nextInt(beginningStorylines.length)]);
			event.getChannel().sendMessage("The night cycle now begins.");
			mafiatime = true;
		} else {
			event.getChannel().sendMessage("The night cycle now begins.");
			mafiatime = true;
		}		
		
		//get Mafia input
		if (mafiatime) {
			try {
				org.javacord.api.entity.user.User user = null;
				pmchannelID = Mafia.get(0).openPrivateChannel().get().getId();
				api.getPrivateChannelById(pmchannelID).get().sendMessage("Bot Cooonenencnntend");
				api.getPrivateChannelById(pmchannelID).get().addMessageCreateListener(mp);
				Mafia.get(0).openPrivateChannel().get().sendMessage("Who would you like to kill?");

				Message uiop = event.getMessage();
				uiop.emoj
				
//				if (event.getMessageContent().equals("a")) {
//					Mafia.get(0).openPrivateChannel().get().sendMessage("A");
//				}
//				
//				CompletableFuture<MessageSet> aasfdasdfasdf = api.getPrivateChannelById(pmchannelID).get().getMessagesAfter(1, 1);
//				System.out.println(aasfdasdfasdf);
//				
//				api.addMessageCreateListener(MessageCreateEvent -> {
//		            Message pm = event.getMessage();
//		            if (pm.getContent().startsWith(mafiaKill)) {
//		                event.getChannel().sendMessage("Pong!");
//		            }
//		        });

				
				if (event.getMessageContent().startsWith(mafiaKill) && event.getMessageAuthor() == Mafia.get(0)) {
					for (int i1 = 0; i1 < names.size(); i1++) {
						if (names.get(i1).getName().equalsIgnoreCase(msg.replaceAll(" ", "").replace(mafiaKill,""))) {
							user = names.get(i1);
						}
					}
					kill(event, user);
					doctortime = true;
					mafiatime = false;
				} else {
					System.out.println(event.getMessageContent() + " n " + event.getMessageAuthor());
//					doctortime = true;
//					mafiatime = false;
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
			try {
				org.javacord.api.entity.user.User user = null;
				Detective.get(0).openPrivateChannel().get().sendMessage("Who would you like to inspect?");
				if (msg.startsWith(detectiveInspect) && event.getMessageAuthor() == Detective.get(0)) {
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
		
		day+=1;
		System.out.println("Mafia method done");
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
