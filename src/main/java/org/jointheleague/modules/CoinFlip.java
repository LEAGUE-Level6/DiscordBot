package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class CoinFlip extends CustomMessageCreateListener{

	public CoinFlip(String channelName) {
		super(channelName);
	}


	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String m = event.getMessageContent();
		if(m.toLowerCase().contains("yes")) {
			event.getChannel().sendMessage("no");
		}
		
	}

}
