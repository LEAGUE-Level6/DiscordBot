package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Frown extends CustomMessageCreateListener {

	private static final String COMMAND1 = "🙂";
	private static final String COMMAND2 =  "🙃";

	public Frown(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND1) && event.getMessageAuthor().isYourself() == false) {
			event.getChannel().sendMessage("Turn that smile upside down!");
		}
		if (event.getMessageContent().contains(COMMAND2) && event.getMessageAuthor().isYourself() == false) {
			event.getChannel().sendMessage("Good job!");
		}
	}

}
