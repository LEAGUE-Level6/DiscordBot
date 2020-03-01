package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class RockPaperScissorsListener extends CustomMessageCreateListener{

	public static final int rock = 1;
	public static final int paper = 2;
	public static final int scissors = 3;
	
	public RockPaperScissorsListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		Random randy = new Random();
		int you;
		if (event.getMessageContent().startsWith("!rock")) {
			you = rock;
			int them = randy.nextInt(3)+1;
			if(them == you) {
				event.getChannel().sendMessage("It's a tie!");
			}else if(them==paper) {
				event.getChannel().sendMessage("You Lose!");
			}else {
				event.getChannel().sendMessage("You Win!");
			}
		}else if (event.getMessageContent().startsWith("!paper")) {
			you = paper;
			int them = randy.nextInt(3);
			if(them == you) {
				event.getChannel().sendMessage("It's a tie!");
			}else if(them==scissors) {
				event.getChannel().sendMessage("You Lose!");
			}else {
				event.getChannel().sendMessage("You Win!");
			}
		}else if (event.getMessageContent().startsWith("!scissors")) {
			you = scissors;
			int them = randy.nextInt(3);
			if(them == you) {
				event.getChannel().sendMessage("It's a tie!");
			}else if(them==rock) {
				event.getChannel().sendMessage("You Lose!");
			}else {
				event.getChannel().sendMessage("You Win!");
			}
		}
		
		
		
	}

}
