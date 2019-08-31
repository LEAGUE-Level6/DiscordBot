package org.jointheleague.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.aksingh.owmjapis.api.APIException;

public class PollMessageListener extends CustomMessageCreateListener {
	
	JsonObject channelPolls;
	
	public PollMessageListener(String channelName) {
		super(channelName);
	}
	

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().equals("test")) {
			event.getChannel().sendMessage("Preparing...");
		}
		
	}
	
	
}
