package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class GoogleSearch extends CustomMessageCreateListener {
	String searchURL = "https://lmgtfy.com/?q=";
	String userSearch = "";
	public GoogleSearch(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().startsWith("!search(")) {
			userSearch = event.getMessageContent().substring(8);
			searchURL+=userSearch;
			event.getChannel().sendMessage(searchURL);
		}
		
		
	}
	
}

