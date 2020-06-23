package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;


public class QuitMessageListener extends CustomMessageCreateListener {

	private static final String COMMAND = "!quit";
	private static final String RESPONSE = "Tide Off!";

	public QuitMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			event.getChannel().sendMessage(RESPONSE);
			System.exit(0);
		}
	}
}
