package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class BEEP extends CustomMessageCreateListener {

	private static final String BEEP = "!beep";
	private static final String ZEBRA = "!dance";

	public BEEP(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(BEEP)) {
			event.getChannel().sendMessage("BEEP");
		}
		else if(event.getMessageContent().contains(ZEBRA)) {
			event.getChannel().sendMessage("https://www.youtube.com/watch?v=-Z668Qc0P4Q");
		}
	}
	
}