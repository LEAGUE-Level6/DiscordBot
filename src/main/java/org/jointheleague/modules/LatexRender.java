package org.jointheleague.modules;

import java.util.HashMap;

import org.javacord.api.event.message.MessageCreateEvent;

public class LatexRender extends CustomMessageCreateListener {

	HashMap<String, Integer> convert = new HashMap<>(); 
	
	public LatexRender(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().contains("!math")) {
			String s = event.getMessageContent();
			event.getChannel().sendMessage("https://latex.codecogs.com/gif.latex?" + s.substring(5));
		}
	}
}
