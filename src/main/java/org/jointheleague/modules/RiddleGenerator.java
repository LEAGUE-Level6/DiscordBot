package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.aksingh.owmjapis.api.APIException;

public class RiddleGenerator extends CustomMessageCreateListener {
	private static final String COMMAND = "!riddle";

	public RiddleGenerator(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
		
		if (event.getMessageContent().contains(COMMAND)) {
			Random r = new Random();
			event.getChannel().sendMessage("Your random number is " + r.nextInt(20));
		}
	}

}
