package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class rockpaperscissors extends CustomMessageCreateListener{

	private static final String COMMAND = "!play rock paper scissors";
	private static final String COMMAND2 = "rock";
	private static final String COMMAND3 = "paper";
	private static final String COMMAND4 = "scissors";

	public rockpaperscissors(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "you can play a game of rock paper scissors");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String answer = "";
		if (event.getMessageContent().contains(COMMAND)) {
			event.getChannel().sendMessage("starting game of rock paper scissors. type in one of the three options");
		int number = new Random().nextInt(3);
		if(number == 0) {
			answer = "rock";
		}if(number == 1) {
			answer = "paper";
		}if(number == 2) {
			answer = "scissors";
		}
		}
		if(event.getMessageContent().contains(COMMAND2)) {
			if(answer == "paper") {
				event.getChannel().sendMessage("the bot chose paper. its a tie.");
			}if(answer == "rock") {
				event.getChannel().sendMessage("the bot chose rock. you win!");
			}if(answer == "scissors") {
				event.getChannel().sendMessage("the bot chose scissors. you lose.");
			}
		}
		if(event.getMessageContent().contains(COMMAND3)) {
			if(answer == "paper") {
				event.getChannel().sendMessage("the bot chose paper. you lose..");
			}if(answer == "rock") {
				event.getChannel().sendMessage("the bot chose rock. its a tie.");
			}if(answer == "scissors") {
				event.getChannel().sendMessage("the bot chose scissors. you win!");
			}
		}
		if(event.getMessageContent().contains(COMMAND4)) {
			if(answer == "paper") {
				event.getChannel().sendMessage("the bot chose paper. you win!");
			}if(answer == "rock") {
				event.getChannel().sendMessage("the bot chose rock. you lose.");
			}if(answer == "scissors") {
				event.getChannel().sendMessage("the bot chose scissors. its a tie.");
			}
		}
		}
	}

