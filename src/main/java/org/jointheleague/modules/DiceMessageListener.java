package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class DiceMessageListener extends CustomMessageCreateListener{

	public DiceMessageListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!dice")) {
			Random random = new Random(6);
			int m = random.nextInt();
				event.getChannel().sendMessage("Rolled a(n) "+m);
			
			}
		}

}
