package org.jointheleague.discord_bot_example;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jointheleague.modules.Ping;

public class MessageListener implements MessageCreateListener {
	private String channelName;
	
	//modules
	Ping ping = new Ping();

	public MessageListener(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		event.getServerTextChannel().ifPresent(e -> {
			if (e.getName().equals(channelName)) {
				if (event.getMessageContent().equalsIgnoreCase("!ping")) {
					ping.handlePing(event);
				} else if (event.getMessageContent().equalsIgnoreCase("!bing")) {
					event.getChannel().sendMessage("Bong!");
				}
			}

		});

	}
}
