package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Hello extends CustomMessageCreateListener {

	private static final String COMMAND = "!Hello";

	public Hello(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "This is a simple command that gives you a friendly Hello! To use it just type \\\"!Hello\\\"");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains(COMMAND)) {
			String user = event.getMessageAuthor().getDisplayName().toString();
			event.getChannel().sendMessage("Hello! " + user);

		}
	}

}
