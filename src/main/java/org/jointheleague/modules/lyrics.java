package org.jointheleague.modules;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class lyrics extends CustomMessageCreateListener{

	public lyrics(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains("!lyrics")) {
		
		}
	}

}
