package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class Ping {
	private static final String RESPONSE = "pong";

	public void handlePing(MessageCreateEvent event) {
		event.getChannel().sendMessage(RESPONSE);
	}
}
