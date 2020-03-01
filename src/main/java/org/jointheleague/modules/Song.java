package org.jointheleague.modules;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Song extends CustomMessageCreateListener{
	private static final String COMMAND = "!song";
private static final String COMMAND1 = "!play";
private static final String COMMAND2 = "!pause";
	public Song(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {
		event.getChannel().sendMessage("Type in  !play <song name> to play a song, and !pause to pause it");
		}
		else if(event.getMessageContent().contains(COMMAND1)) {
			event.getChannel().sendMessage("");
		}
	}

}
