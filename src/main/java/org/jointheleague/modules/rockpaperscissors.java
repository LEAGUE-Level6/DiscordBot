package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class rockpaperscissors extends CustomMessageCreateListener{

	private static final String COMMAND = "!play rock paper scissors";
	private static final String COMMAND2 = "!ROCK";
	private static final String COMMAND3 = "!PAPER";
	private static final String COMMAND4 = "!SCISSORS";
	String answer;
	public rockpaperscissors(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "you can play a game of rock paper scissors");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			event.getChannel().sendMessage("starting game of rock paper scissors. type in one of three options, write it in capitals please");
		int number = new Random().nextInt(3);
		if(number == 0) {
			answer = "rock";
		}if(number == 1) {
			answer = "paper";
		}if(number == 2) {
			answer = "scissors";
		}
		System.out.println(number + answer);
		}
		if(event.getMessageContent().contains(COMMAND2)) {
			event.getChannel().sendMessage("you chose rock");
			event.getChannel().sendMessage("the bot chose " + answer);
			if(answer.equals("rock")) {
				event.getChannel().sendMessage("its a tie");
			}
			if(answer.equals("paper")) {
				event.getChannel().sendMessage("you lost");
			}
			if(answer.equals("scissors")) {
				event.getChannel().sendMessage("you win");
			}
		}
		if(event.getMessageContent().contains(COMMAND3)) {
			event.getChannel().sendMessage("you chose paper");
			event.getChannel().sendMessage("the bot chose " + answer);
			if(answer.equals("rock")) {
				event.getChannel().sendMessage("you win");
			}
			if(answer.equals("paper")) {
				event.getChannel().sendMessage("its a tie");
			}
			if(answer.equals("scissors")) {
				event.getChannel().sendMessage("you lose");
			}
		}
		if(event.getMessageContent().contains(COMMAND4)) {
			event.getChannel().sendMessage("you chose scissors");
			event.getChannel().sendMessage("the bot chose " + answer);
			if(answer.equals("rock")) {
				event.getChannel().sendMessage("you lose");
			}
			if(answer.equals("paper")) {
				event.getChannel().sendMessage("you win");
			}
			if(answer.equals("scissors")) {
				event.getChannel().sendMessage("its a tie");
			}
		}
		}
	}

