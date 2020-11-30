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
			String lim = message;
			String max = "1";
			String output = "";
			int start = 0;
			for (int i = 0; i < message.length(); i++) {
				if (message.charAt(i) == ' ') {
					lim = message.substring(start, i);
					max = message.substring(i + 1, message.length()) + "";
				}
			}
			int limit = Integer.parseInt(lim) - 1;
			int numberOfDie = Integer.parseInt(max);
			Random rand = new Random();
			while (numberOfDie > 0) {
				int random = 1 + rand.nextInt(limit);
				output += random + " ";
				numberOfDie--;
			}
			event.getChannel().sendMessage(output);
		}
	}
}
