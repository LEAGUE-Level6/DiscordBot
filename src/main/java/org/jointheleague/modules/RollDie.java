package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class RollDie extends CustomMessageCreateListener {

	public RollDie(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	private static final String COMMAND = "!rollDie";

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {
			String message = event.getMessageContent().substring(9, event.getMessageContent().length());
			String lim = "";
			for (int i = 0; i < message.length()+1; i++) {
				if(message.substring(i, i+1).equals(" ")) {
					lim = message.substring(0, i);
					
				}
			}
			int limit = Integer.parseInt(lim) - 1;
			Random rand = new Random();
			int num = 1 + rand.nextInt(limit);
			event.getChannel().sendMessage("" + num);
		}
	}
}
