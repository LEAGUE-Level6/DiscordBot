package org.jointheleague.modules;

import java.io.File;

import java.time.Instant;
import java.util.Random;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;


public class RollDiceMessageListener extends CustomMessageCreateListener {

	private static final String COMMAND = "!rolldice";

	public RollDiceMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			MessageBuilder mb = new MessageBuilder();
			Random r = new Random();
			int n = r.nextInt(6);
			if (n == 0) {
				mb.addAttachment(new File("DicePics/Dice1-300x300.gif"));
			} else if (n == 1) {
				mb.addAttachment(new File("DicePics/Dice2-300x300.gif"));
			} else if (n == 2) {
				mb.addAttachment(new File("DicePics/Dice3-300x300.gif"));
			} else if (n == 3) {
				mb.addAttachment(new File("DicePics/Dice4-300x300.gif"));
			} else if (n == 4) {
				mb.addAttachment(new File("DicePics/Dice5-300x300.gif"));
			} else if (n == 5) {
				mb.addAttachment(new File("DicePics/Dice6-300x300.gif"));
			}		
			mb.send(event.getChannel());
		}
	}
}
