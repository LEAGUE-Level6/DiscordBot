package org.jointheleague.modules;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.javacord.api.event.message.MessageCreateEvent;

public class ClockMessageListener extends CustomMessageCreateListener {

	public ClockMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!date")) {
			SimpleDateFormat formatter = new SimpleDateFormat("'The date is 'MM-dd-YYYY'. The time is 'HH:mm:ss z'.'");  
			Date date = new Date(System.currentTimeMillis());
			
			event.getChannel().sendMessage(formatter.format(date));
		}
	}

}
