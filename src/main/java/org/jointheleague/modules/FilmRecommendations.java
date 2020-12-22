package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class FilmRecommendations extends CustomMessageCreateListener {

	public FilmRecommendations(String channelName) {
		super(channelName);
		
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
	}

}
