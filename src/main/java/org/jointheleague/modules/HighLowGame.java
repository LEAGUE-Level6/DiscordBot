
package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class HighLowGame extends CustomMessageCreateListener {

	private static final String COMMAND = "!HighLow";

	public HighLowGame(String channelName) {
		super(channelName);
	}
	int rand = 0; 
	int counter;
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith(COMMAND)) {
			if(event.getMessageContent().equals(COMMAND)) {
				Random r = new Random(); 
				rand = r.nextInt(100);
				counter = 0;
				event.getChannel().sendMessage("A random number bewteen 1-100 has been generated");
				event.getChannel().sendMessage("Make guess by typing !HighLow <your guess>");
				event.getChannel().sendMessage("You will have 6 tries to guess the number");
			}
			int guess = Integer.parseInt(event.getMessageContent().substring(9));
			if(guess>rand) {
				event.getChannel().sendMessage("too high");
				counter++;
			}
			else if (guess < rand) {
				event.getChannel().sendMessage("too low");
				counter++; 
			}
			else if(guess == rand){
				event.getChannel().sendMessage("You guessed it in " + (counter + 1) + " tries");
				event.getChannel().sendMessage("Type !HighLow to play again");
			
			}
			
			if (counter > 5) {
				event.getChannel().sendMessage("You are out of tries. The number was " + rand);
				event.getChannel().sendMessage("Type !HighLow to play again");
			}
		}
	}
}
		
		

