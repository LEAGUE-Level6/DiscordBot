package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class HeHe extends CustomMessageCreateListener{
	private static final String COMMAND = "HeHe";

	public HeHe(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(!event.getMessageContent().contains(COMMAND)) {
		event.getChannel().sendMessage("HeHe");
		}
	}

}
