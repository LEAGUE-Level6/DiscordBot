package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class RandomBoxGuess extends CustomMessageCreateListener {
	private static final String COMMAND = "!RandomBoxGuess";
	private static final String COMMAND1 ="!RandomBoxStart";
	Random r = new Random();
	String[] disguise = new String[5];
	int rando;
	String rand;
	int counter;

	public RandomBoxGuess(String channelName) {
		super(channelName);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains(COMMAND1)) {
			rando = r.nextInt(5);
			System.out.println(rando);
			rand = Integer.toString(rando);
			counter=0;
			for (int i = 0; i < disguise.length; i++) {
				disguise[i] = ":disguised_face:";
			}
			event.getChannel().sendMessage(disguise[0] + disguise[1] + disguise[2] + disguise[3] + disguise[4]);
			event.getChannel().sendMessage("What person is it?");
		}

		if (event.getMessageContent().contains(COMMAND)) {
			int inp = Integer.parseInt(event.getMessageContent().substring(16));
			if (counter==3) {
				event.getChannel().sendMessage("The game is over!");
			}
			else if (event.getMessageContent().contains(rand)) {
				disguise[rando] = ":cold_sweat:";
				counter=3;
				event.getChannel().sendMessage(disguise[0] + disguise[1] + disguise[2] + disguise[3] + disguise[4]);
				event.getChannel().sendMessage("You caught him, good job!");
			} else {
				disguise[inp] = ":face_with_symbols_over_mouth:";
				event.getChannel().sendMessage(disguise[0] + disguise[1] + disguise[2] + disguise[3] + disguise[4]);
				event.getChannel().sendMessage("Wrong person! He's mad! Try again!");
				counter++;
				event.getChannel().sendMessage("You guessed "+ counter+ " times. You have "+ (3-counter) + " more guesses.");
				if (counter==3) {
					event.getChannel().sendMessage("You lost! Please try again!");
				}
			}

		}
	}
}
