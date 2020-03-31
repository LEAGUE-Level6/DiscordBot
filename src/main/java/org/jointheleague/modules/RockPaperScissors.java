package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class RockPaperScissors extends CustomMessageCreateListener {
	private static final String rpsCommand = "!RPS";

	private static final String rock = "!rock";
	private static final String paper = "!paper";
	private static final String scissor = "!scissors";

	private static final String start = "!start";

	public RockPaperScissors(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {

		String compChoice = "";
		int computer = (int) (Math.random() * 3);

		if (computer == 0) {
			compChoice = "rock";
		} else if (computer == 1) {
			compChoice = "paper";
		} else {
			compChoice = "scissor";
		}
		
		if (event.getMessageContent().equals(rpsCommand)) {
			event.getChannel().sendMessage(
					"Let's play rock paper scissors!\n-For scissors enter ** !scissors **- \n-For rock enter ** !rock **- \n-For paper enter ** !paper **-");

		} 
		if (compChoice.equals("rock") && event.getMessageContent().equals(rock)) {
			event.getChannel().sendMessage("! Draw Game !");

		}
		if (compChoice.equals("rock") && event.getMessageContent().equals(scissor)) {
			event.getChannel().sendMessage("! I win <<Rock Beaks Scissors>> !");

		}
		if (compChoice.equals("paper") && event.getMessageContent().equals(rock)) {
			event.getChannel().sendMessage("! I win <<Paper Covers Rock>> !");
		}
		if (compChoice.equals("scissor") && event.getMessageContent().equals(paper)) {
			event.getChannel().sendMessage("! I win <<Scissors Cuts Paper>> !");
		}
		if (compChoice.equals("scissor") && event.getMessageContent().equals(rock)) {
			event.getChannel().sendMessage("! You win <<Rock Beaks Scissors>> !");
		}
		if (compChoice.equals("rock") && event.getMessageContent().equals(paper)) {
			event.getChannel().sendMessage("! You win <<Paper Covers Rock>> !");
		}
		if (compChoice.equals("paper") && event.getMessageContent().equals(scissor)) {
			event.getChannel().sendMessage("! You win <<Scissors Cuts Paper>> !");
		}

	}

}