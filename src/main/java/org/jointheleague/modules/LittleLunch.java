package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class LittleLunch extends CustomMessageCreateListener {

	private static final String CHAR = "!llcharacter";
	private static final String EP = "!llepisode";

	public LittleLunch(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(CHAR)) {
			event.getChannel().sendMessage("Oh God, here we go again!");
		}
		else if(event.getMessageContent().contains(EP)) {
			event.getChannel().sendMessage("Phew. That was long!");
		}
	}
	
}