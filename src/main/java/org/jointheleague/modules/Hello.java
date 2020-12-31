package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Hello extends CustomMessageCreateListener {

	private static final String COMMAND = "!Hello";

	public Hello(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains(COMMAND)) {
			String user = event.getMessageAuthor().getDisplayName().toString();
			event.getChannel().sendMessage("Hello! " + user);

		}
	}

}
