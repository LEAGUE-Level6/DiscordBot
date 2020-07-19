package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Greeter extends CustomMessageCreateListener {

	String[] greetings = { ", hope you are doing well!", ", hello!", ", yo!", ", what's up?", ", good day!",
			", what is new?", ", long time no see!", "it has been a while!", ", how is life?",
			", nice to hear from you!" };

	private static final String COMMAND = "!Greet";

	public Greeter(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed(COMMAND, "It will respond with your name, followed a by a random greeting. Just type !Greet and then the name of who you would like to greet. Do not reply to the greetings");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String message = event.getMessageContent();
		if (message.contains(COMMAND)) {

			String name = message.replace("!Greet", "").replaceAll(" ", "");
			Random r = new Random();
			int rand = r.nextInt(9);
			event.getChannel().sendMessage("Greeting: " + name + greetings[rand] + " " + "\uD83D\uDE00");
			
		}
		
		
	}

}
