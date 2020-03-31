package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class MusicBot extends CustomMessageCreateListener{
	public MusicBot(String channelName) {
		super(channelName);
	}

	private static final String alek = "!music";

	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().equals(alek)) {
			event.getChannel().sendMessage("https://open.spotify.com/playlist/642SZFOJeAR1ZSuVItytDT?si=HY1hfkztR9ippbg8Q0ELhg");
		} 
		
	}
	
}
