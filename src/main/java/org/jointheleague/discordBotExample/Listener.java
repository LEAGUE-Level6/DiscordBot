package org.jointheleague.discordBotExample;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Listener implements MessageCreateListener {

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		if(event.getMessageContent().equals("!hello")) {
			event.getChannel().sendMessage("Hi!");
		}
	}

}
