package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class SemaphoreFlashCards extends CustomMessageCreateListener {
final String sem = "!semaphore";
	public SemaphoreFlashCards(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		
			if(event.getMessageContent().contains(sem)) {
			event.getChannel().sendMessage("This is a Semaphore study tool");
			event.getChannel().sendMessage("type !help for instructions");
		
	}
	}

}
