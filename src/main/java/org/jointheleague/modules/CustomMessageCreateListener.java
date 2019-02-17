package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public abstract class CustomMessageCreateListener implements MessageCreateListener {
	protected String channelName;

	public CustomMessageCreateListener(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		event.getServerTextChannel().ifPresent(e -> {
			if (e.getName().equals(channelName)) {
				handle(event);
			}
		});
	}

	public abstract void handle(MessageCreateEvent event);
}