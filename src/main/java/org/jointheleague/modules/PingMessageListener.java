package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class PingMessageListener extends CustomMessageCreateListener {

	private static final String COMMAND = "!ping";
	private static final String RESPONSE = "pong";

	public PingMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			event.getChannel().sendMessage(RESPONSE);
		}
	}
}
