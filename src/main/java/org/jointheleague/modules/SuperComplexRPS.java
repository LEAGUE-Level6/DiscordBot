package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class SuperComplexRPS extends CustomMessageCreateListener {

	private static final String COMMAND = "!playSuperComplexRPS";
	
	public SuperComplexRPS(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		
		String housechoice = "";
		
		if (event.getMessageContent().contains(COMMAND)) {
			event.getChannel().sendMessage("Welcome to a super complicated version of Rock, Paper, Scissors! Please input your selection (Rock, Gun, Lightning, Dragon, Water, Air, Paper, Sponge, Wolf, Tree, Human, Snake, Scissors, or Fire)");			
		
			int house = new Random().nextInt(14);
			switch (house) {
				case 0:	housechoice = "rock";		break;
			
				case 1:	housechoice = "lightning";		break;

				case 2:	housechoice = "dragon";		break;

				case 3:	housechoice = "water";		break;

				case 4:	housechoice = "air";		break;

				case 5:	housechoice = "paper";		break;

				case 6:	housechoice = "sponge";		break;

				case 7:	housechoice = "wolf";		break;

				case 8:	housechoice = "tree";		break;

				case 9:	housechoice = "human";		break;

				case 10: housechoice = "snake";		break;

				case 11: housechoice = "scissors";		break;

				case 12: housechoice = "fire";		break;

				default: housechoice = "gun"; 		break;
			}
			
			if (event.getMessageContent().contains("rock")) {
				if (housechoice == "fire" || housechoice == "scissors" || housechoice == "snake" || housechoice == "human" || housechoice == "wolf" || housechoice == "sponge") {
					event.getChannel().sendMessage("You chose rock! You won!");
				}
			}
		
		
		}
	}
	
}
