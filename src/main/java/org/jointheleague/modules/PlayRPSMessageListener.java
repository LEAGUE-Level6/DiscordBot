package org.jointheleague.modules;

import java.util.Random;


import org.javacord.api.event.message.MessageCreateEvent;


public class PlayRPSMessageListener extends CustomMessageCreateListener {

	private static final String COMMAND = "!playrps";
	private static boolean inPlay = false;

	public PlayRPSMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (!inPlay) {
			if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
				inPlay = true;
				event.getChannel().sendMessage("Welcome To Rock Paper Scissors! I Can't Wait To Play You!");
				event.getChannel().sendMessage("Type Rock, Paper, Or Scissors And I Will Say What I Chose!");
			}
		}
		if (inPlay) {
			if (event.getMessageContent().equalsIgnoreCase("Rock")) {
				inPlay = false;
				Random g = new Random();
				int r = g.nextInt(99);
				if(r%3==0) {
					event.getChannel().sendMessage("I Chose Rock! We Tied!");
				}
				if(r%3==1) {
					event.getChannel().sendMessage("I Chose Paper! You Lost!");
				}
				if(r%3==2) {
					event.getChannel().sendMessage("I Chose Scissors! You Won!");
				}
			}
			if (event.getMessageContent().equalsIgnoreCase("Paper")) {
				inPlay = false;
				Random g = new Random();
				int r = g.nextInt(99);
				if(r%3==0) {
					event.getChannel().sendMessage("I Chose Rock! You Won!");
				}
				if(r%3==1) {
					event.getChannel().sendMessage("I Chose Paper! We Tied!");
				}
				if(r%3==2) {
					event.getChannel().sendMessage("I Chose Scissors! You Lost!");
				}
			}
			if (event.getMessageContent().equalsIgnoreCase("Scissors")) {
				inPlay = false;
				Random g = new Random();
				int r = g.nextInt(99);
				if(r%3==0) {
					event.getChannel().sendMessage("I Chose Rock! You Lost!");
				}
				if(r%3==1) {
					event.getChannel().sendMessage("I Chose Paper! You Won!");
				}
				if(r%3==2) {
					event.getChannel().sendMessage("I Chose Scissors! We Tied!");
				}
			}
		}
	}
}
