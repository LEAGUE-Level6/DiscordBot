package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class PingMessageListener implements MessageCreateListener {
	private String channelName;
	private static final String RESPONSE = "pong";

	public PingMessageListener(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		event.getServerTextChannel().ifPresent(e -> {
			if (e.getName().equals(channelName)) {
				handlePing(event);
			}
		});
	}

	public void handlePing(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase("!ping")) {
			event.getChannel().sendMessage(RESPONSE);
		} 
	}
}
