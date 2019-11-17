package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class AllCaps extends CustomMessageCreateListener{

	public AllCaps(String channelName) {
		super(channelName);
	}

	@Override
	
	
	
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String a = event.getMessageContent();
		String b = a.toUpperCase();
		event.getChannel().sendMessage(b);
		
	}

}
