package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class UltraRPS extends CustomMessageCreateListener {

	private static final String COMMAND = "!playUltraRPS";
	private static boolean Playing = false;
	
	public UltraRPS(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "A very complicated version of Rock, Paper, Scissors with 13 options (Rock, Gun, Lightning, Dragon, Water, Air, Paper, Sponge, Tree, Human, Snake, Scissors, or Fire). In this order, each element beats half of the 6 other choices left and looses to the other 6 choices.   ex: Rock beats sponge, human, snake, scissors, and fire. It looses to gun, lightning, dragon, water, air and paper.");
	}

	@Override
	public void handle(MessageCreateEvent event) {
			
		if (!Playing) {
			if (event.getMessageContent().contains(COMMAND)) {
				Playing = true;
				event.getChannel().sendMessage("Welcome to a super complicated version of Rock, Paper, Scissors! Please input your selection (Rock, Gun, Lightning, Dragon, Water, Air, Paper, Sponge, Tree, Human, Snake, Scissors, or Fire)");			
			}
		}
		
		
		String housechoice = "";
		
		if (Playing) {						
			int house = new Random().nextInt(13);
			switch (house) {
				case 0:	housechoice = "rock";		break;
			
				case 1:	housechoice = "lightning";		break;

				case 2:	housechoice = "dragon";		break;

				case 3:	housechoice = "water";		break;

				case 4:	housechoice = "air";		break;

				case 5:	housechoice = "paper";		break;

				case 6:	housechoice = "sponge";		break;

				case 8:	housechoice = "tree";		break;

				case 9:	housechoice = "human";		break;

				case 10: housechoice = "snake";		break;

				case 11: housechoice = "scissors";		break;

				case 12: housechoice = "fire";		break;

				default: housechoice = "gun"; 		break;
			}
			
			
			if (event.getMessageContent().contains("rock")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("fire") || housechoice.equalsIgnoreCase("scissors") || housechoice.equalsIgnoreCase("snake") || housechoice.equalsIgnoreCase("human") || housechoice.equalsIgnoreCase("tree") || housechoice.equalsIgnoreCase("sponge")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("gun")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("rock") || housechoice.equalsIgnoreCase("fire") || housechoice.equalsIgnoreCase("scissors") || housechoice.equalsIgnoreCase("snake") || housechoice.equalsIgnoreCase("human") || housechoice.equalsIgnoreCase("tree")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("lightning")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("gun") || housechoice.equalsIgnoreCase("rock") || housechoice.equalsIgnoreCase("fire") || housechoice.equalsIgnoreCase("scissors") || housechoice.equalsIgnoreCase("snake") || housechoice.equalsIgnoreCase("human")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("dragon")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("lightning") || housechoice.equalsIgnoreCase("gun") || housechoice.equalsIgnoreCase("rock") || housechoice.equalsIgnoreCase("fire") || housechoice.equalsIgnoreCase("scissors") || housechoice.equalsIgnoreCase("snake")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("water")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("dragon") || housechoice.equalsIgnoreCase("lightning") || housechoice.equalsIgnoreCase("gun") || housechoice.equalsIgnoreCase("rock") || housechoice.equalsIgnoreCase("fire") || housechoice.equalsIgnoreCase("scissors")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("air")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("water") || housechoice.equalsIgnoreCase("dragon") || housechoice.equalsIgnoreCase("lightning") || housechoice.equalsIgnoreCase("gun") || housechoice.equalsIgnoreCase("rock") || housechoice.equalsIgnoreCase("fire")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("paper")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("air") || housechoice.equalsIgnoreCase("water") || housechoice.equalsIgnoreCase("dragon") || housechoice.equalsIgnoreCase("lightning") || housechoice.equalsIgnoreCase("gun") || housechoice.equalsIgnoreCase("rock")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("sponge")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("paper") || housechoice.equalsIgnoreCase("air") || housechoice.equalsIgnoreCase("water") || housechoice.equalsIgnoreCase("dragon") || housechoice.equalsIgnoreCase("lightning") || housechoice.equalsIgnoreCase("gun")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("tree")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("sponge") || housechoice.equalsIgnoreCase("paper") || housechoice.equalsIgnoreCase("air") || housechoice.equalsIgnoreCase("water") || housechoice.equalsIgnoreCase("dragon") || housechoice.equalsIgnoreCase("lightning")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("human")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("tree") || housechoice.equalsIgnoreCase("sponge") || housechoice.equalsIgnoreCase("paper") || housechoice.equalsIgnoreCase("air") || housechoice.equalsIgnoreCase("water") || housechoice.equalsIgnoreCase("dragon")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("snake")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("human") || housechoice.equalsIgnoreCase("tree") || housechoice.equalsIgnoreCase("sponge") || housechoice.equalsIgnoreCase("paper") || housechoice.equalsIgnoreCase("air") || housechoice.equalsIgnoreCase("water")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("scissors")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("snake") || housechoice.equalsIgnoreCase("human") || housechoice.equalsIgnoreCase("tree") || housechoice.equalsIgnoreCase("sponge") || housechoice.equalsIgnoreCase("paper") || housechoice.equalsIgnoreCase("air")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
			if (event.getMessageContent().contains("fire")) {
				Playing = false;
				if (housechoice.equalsIgnoreCase("scissors") || housechoice.equalsIgnoreCase("snake") || housechoice.equalsIgnoreCase("human") || housechoice.equalsIgnoreCase("tree") || housechoice.equalsIgnoreCase("sponge") || housechoice.equalsIgnoreCase("paper")) {
					event.getChannel().sendMessage("I chose " + housechoice + "! You won!");
				} else {
					event.getChannel().sendMessage("I chose " + housechoice + "! You lost!");
				}
			}
			
		}	
	}
	
}
