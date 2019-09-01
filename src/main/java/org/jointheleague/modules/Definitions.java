package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class Definitions extends CustomMessageCreateListener{
	public Definitions(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String a = event.getMessageContent();
		if(a.startsWith("!define ")) {
			String toBeFound = a.substring(8);
			event.getChannel().sendMessage("https://www.dictionary.com/browse/"+toBeFound+"?s=t");
		}
	}
}
