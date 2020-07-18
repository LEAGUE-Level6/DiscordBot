package org.jointheleague.modules;

import java.util.Random;


import org.javacord.api.event.message.MessageCreateEvent;



public class CoinFlipMessageListener extends CustomMessageCreateListener {

	private static final String COMMAND = "!coinflip";

	public CoinFlipMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			Random r = new Random();
			int f = r.nextInt(100);
			if (f % 2 == 0) {
				event.getChannel().sendMessage("You Got Heads");
			} else {
				event.getChannel().sendMessage("You Got Tails");
			}
		}
	}
}
