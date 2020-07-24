package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class UH_HUH extends CustomMessageCreateListener {

	private static final String START = "!story";
	private static final String END = "!endstory";
	boolean onoff = false;

	public UH_HUH(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(START)) {
			event.getChannel().sendMessage("Oh God, here we go again!");
			onoff = true;
		}
		else if(event.getMessageContent().contains(END)) {
			event.getChannel().sendMessage("Phew. That was long!");
			onoff = false;
		}
		if(onoff && !event.getMessageAuthor().isYourself()) {
			event.getChannel().sendMessage("Uh huh.");
		}
	}
	
}