package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class Repeat extends CustomMessageCreateListener {

	private static final String START = "!repeat";
	private static final String END = "!stopit";
	boolean onoff = false;

	public Repeat(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(START)) {
			event.getChannel().sendMessage("He he he he...");
			onoff = true;
		}
		else if(event.getMessageContent().contains(END)) {
			event.getChannel().sendMessage("That was fun!");
			onoff = false;
		}
		if(onoff && !event.getMessageAuthor().isYourself()) {
			event.getChannel().sendMessage(event.getReadableMessageContent());
		}
	}
	
}