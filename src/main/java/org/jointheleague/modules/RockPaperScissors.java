package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class RockPaperScissors extends CustomMessageCreateListener {
	private static final String rpsCommand = "!RPS";

	private static final String rock = "!rock";
	private static final String paper = "!paper";
	private static final String scissor = "!scissor";

	private static final String start = "!start";

	String compChoice;

	public RockPaperScissors(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {

		if (event.getMessageContent().equals(rpsCommand)) {
			event.getChannel().sendMessage(
					"Let's play rock paper scissors!\n-For scissors enter ** !scissors **- \n-For rock enter ** !rock **- \n-For paper enter ** !paper **- \n-Type ** !start ** to start the game-");

		} else if (event.getMessageContent().equals(start)) {
			event.getChannel().sendMessage("Enter your weapon");
			
//			if(event.getMessageContent().equals(rock)) {
//				event.getChannel().sendMessage("You chose rock");
//			}
//			if (event.getMessageContent().equals(rock) && botPick().equals("rock")) {
//				event.getChannel().sendMessage("!Draw Game!");
//
//			} 
//			else if (botPick().equals("rock") && event.getMessageContent().equals(scissor)) {
//				event.getChannel().sendMessage("!I win <<Rock Beaks Scissors>>!");
//
//			} 
//			else if (botPick().equals("paper") && event.getMessageContent().equals(rock)) {
//				event.getChannel().sendMessage("!I win <<Paper Covers Rock>>!");
//			} 
//			else if (botPick().equals("scissor") && event.getMessageContent().equals(paper)) {
//				event.getChannel().sendMessage("!I win <<Scissors Cuts Paper>>!");
//			} 
//			else if (botPick().equals("scissor") && event.getMessageContent().equals(rock)) {
//				event.getChannel().sendMessage("!You win <<Rock Beaks Scissors>>!");
//			} 
//			else if (botPick().equals("rock") && event.getMessageContent().equals(paper)) {
//				event.getChannel().sendMessage("!You win <<Paper Covers Rock>>!");
//			} 
//			else if (botPick().equals("paper") && event.getMessageContent().equals(scissor)) {
//				event.getChannel().sendMessage("!You win <<Scissors Cuts Paper>>!");
//			}

		}
	}

	public String botPick() {
		int computer = (int) (Math.random() * 3);

		if (computer == 0) {
			compChoice = "rock";
		} else if (computer == 1) {
			compChoice = "paper";
		} else {
			compChoice = "scissor";
		}
		return compChoice;
	}


}