
package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class GuessingRandomNumber extends CustomMessageCreateListener {

	private static final String COMMAND = "!Guess";

	public GuessingRandomNumber(String channelName) {
		super(channelName);
	}
	int rand = 0; 
	int counter = 0;
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith(COMMAND)) {
			if(event.getMessageContent().equals(COMMAND)) {
				Random r = new Random(); 
				rand = r.nextInt(100);
				event.getChannel().sendMessage("A random number bewteen 1-100 has been generated");
				event.getChannel().sendMessage("Make guess by typing !Guess <your guess>");
			}
			int guess = Integer.parseInt(event.getMessageContent().substring(7));
			if(guess>rand) {
				event.getChannel().sendMessage("too high");
				counter++;
			}
			else if (guess < rand) {
				event.getChannel().sendMessage("too low");
				counter++; 
			}
			else {
				counter++;
				event.getChannel().sendMessage("You guessed it in " + counter + " tries");
			}
		}
	}
}
		
		

