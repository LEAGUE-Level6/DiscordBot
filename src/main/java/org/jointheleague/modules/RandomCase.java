package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class RandomCase extends CustomMessageCreateListener {

	private static final String COMMAND = "!RandomCase";
	Random rand = new Random();

	public RandomCase(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			String input = event.getMessageContent();

			String message = input.replace(COMMAND, "");
			String newMessage = "";

			
			System.out.println(message);


			for (int i = 0; i < message.length(); i++) {
				if (rand.nextBoolean())
					newMessage = newMessage + Character.toLowerCase(message.charAt(i));
				else
					newMessage = newMessage + Character.toUpperCase(message.charAt(i));
			}
			event.getChannel().sendMessage(newMessage);
			//System.out.println(event.getChannel().getCreationTimestamp().getNano());
		}
	}

}
