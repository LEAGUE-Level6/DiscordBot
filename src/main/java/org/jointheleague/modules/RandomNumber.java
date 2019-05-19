package org.jointheleague.modules;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class RandomNumber implements MessageCreateListener {

	private static final String PREFIX = "!weather";
	
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		if(event.getMessageContent().startsWith(PREFIX)) {
			
		}
	}
	
}
