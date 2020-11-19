package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class Greeter extends CustomMessageCreateListener {

	private static final String COMMAND = "!greet";
	
	public Greeter(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Says a random greeting to you. If you write your name it will use your name in the greeting (e.g. !greet bob)");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!greet","");
			Random r = new Random();
			int num = r.nextInt(5);
			if(cmd.equals("")) {
			
				
				if(num == 0) {
					event.getChannel().sendMessage("Welcome to this Discord channel!");
				} else if(num == 1) {
					event.getChannel().sendMessage("Hey! How are you doing?");
				} else if(num == 2) {
					event.getChannel().sendMessage("Hi!");
				} else if(num == 3) {
					event.getChannel().sendMessage("Hello!");
				} else if(num == 4) {
					event.getChannel().sendMessage("Hello! How are you?");
				}
				
				
			} else {
				
				String name = cmd;
				
				if(num == 0) {
					event.getChannel().sendMessage("Welcome to this Discord channel, "+name+"!");
				} else if(num == 1) {
					event.getChannel().sendMessage("Hey, "+name+"! How are you doing?");
				} else if(num == 2) {
					event.getChannel().sendMessage("Hi "+name+"!");
				} else if(num == 3) {
					event.getChannel().sendMessage("Hello "+name+"!");
				} else if(num == 4) {
					event.getChannel().sendMessage("Hello "+name+"! How are you?");
				}
				
			}
			
		}
	}
	
}
