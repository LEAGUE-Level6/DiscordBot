package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class CoinFlip extends CustomMessageCreateListener{

	public CoinFlip(String channelName) {
		super(channelName);
	}


	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
		String m = event.getMessageContent();
		if(m.toLowerCase().contains("!flipacoin")) {
			Random rand = new Random();
			int n = rand.nextInt(2);
				if(n==0) {
					event.getChannel().sendMessage("Heads");
				}
				else {
					event.getChannel().sendMessage("Tails");
				}
		}
		
	}

}
