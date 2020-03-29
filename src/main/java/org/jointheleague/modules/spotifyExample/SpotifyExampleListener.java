package org.jointheleague.modules.spotifyExample;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.CustomMessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class SpotifyExampleListener extends CustomMessageCreateListener{

	public SpotifyExampleListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		
	}

}
