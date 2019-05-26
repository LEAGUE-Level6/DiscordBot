package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class FactMessageListener extends CustomMessageCreateListener {
	
	String[] facts; // Our fun facts

	public FactMessageListener(String channelName) {
		super(channelName);
		facts = new String[] {
				"As of 2019, J.K. Rowling is a BILLIONARE. *whoa*"
		};
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!fact")) {
			event.getChannel().sendMessage("Dude, I am summoning fun facts up from the depths of my ESSENCE. Here I go...");
			event.getChannel().sendMessage(facts[new Random().nextInt(facts.length)]);
		}
	}

}
