package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class RocketCountDown extends CustomMessageCreateListener{
	
	private static final String COMMAND = "!rocket";
	
	public RocketCountDown(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			
				event.getChannel().sendMessage("Ready for lift off in 10");
				event.getChannel().sendMessage("9");
				event.getChannel().sendMessage("8");
				event.getChannel().sendMessage("7");
				event.getChannel().sendMessage("6");
				event.getChannel().sendMessage("5");
				event.getChannel().sendMessage("4");
				event.getChannel().sendMessage("3");
				event.getChannel().sendMessage("2");
				event.getChannel().sendMessage("1");
				
				
				
				event.getChannel().sendMessage("LIFT OFF!!!!!");
		
		}
	}

}

