package org.jointheleague.discord_bot_example;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MessageListener implements MessageCreateListener {
	String channelName;

	public MessageListener(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		event.getServerTextChannel().ifPresent(e -> {
			if (e.getName().equals(channelName)) {
				if (event.getMessageContent().equalsIgnoreCase("!ping")) {
					event.getChannel().sendMessage("Pong!");
				} else if (event.getMessageContent().equalsIgnoreCase("!bing")) {
					event.getChannel().sendMessage("Bong!");
				}
			}

		});

	
}
}
