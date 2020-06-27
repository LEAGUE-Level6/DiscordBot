package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;


public class Marxist extends CustomMessageCreateListener {

	public Marxist(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String a = event.getMessageContent();
		if(a.contains("my ")) {
			int j = a.indexOf("my ");
			event.getChannel().sendMessage("Do you mean" + " _our_  " + a.substring(j+3) + "? In a perfect society, the means of production are owned communally. If we want to progress as a society, we must embrace collective ownership.");
		}
	}
}


