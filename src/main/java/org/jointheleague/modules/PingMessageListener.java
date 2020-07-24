package org.jointheleague.modules;

import java.time.Instant;


import org.javacord.api.event.message.MessageCreateEvent;


public class PingMessageListener extends CustomMessageCreateListener {

	private static final String COMMAND = "!ping";

	public PingMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		int currentNano = Instant.now().getNano();
		int pastNano = event.getMessage().getCreationTimestamp().getNano();
		if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			event.getChannel().sendMessage("Your Current Ping Is " + ((currentNano - pastNano) / 500000) + " Milliseconds!");
		}
	}
}
