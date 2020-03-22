
package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class Dhruv extends CustomMessageCreateListener {

	private static final String COMMAND = "!Dhruv";

	public Dhruv(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith(COMMAND)) {
			if(event.getMessageContent().equals(COMMAND)) {
				event.getChannel().sendMessage("Type a sentence ");
			}
			 else {
				String calc = event.getMessageContent().substring(7); 
				sentenceCalculator(calc, event);
				
			}
			
		}
		}

	public void sentenceCalculator(String calc, MessageCreateEvent event) {
		// TODO Auto-generated method stub
		String[] words = calc.split("//s+");
		event.getChannel().sendMessage("This sentence has" + calc.length() + "characters");
		event.getChannel().sendMessage("This sentence has" + words.length + "words");
	}
		}	
