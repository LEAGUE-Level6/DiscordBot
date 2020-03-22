package org.jointheleague.modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javacord.api.event.message.MessageCreateEvent;

public class Test extends CustomMessageCreateListener {

	public Test(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!8ball")) {
			if (event.getMessageContent().equals("!8ball")) {
				event.getChannel().sendMessage("Hello World");
			}
	}
}
}
