package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class GuessTheNumber extends CustomMessageCreateListener {

	int range = 100;
	int randomNum = 0;

	public GuessTheNumber(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	private static final String COMMAND = "!GuessNumber";

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		if (event.getMessageContent().equals("!GuessNumber help")
				|| event.getMessageContent().equals("!GuessNumber Help")) {
			event.getChannel().sendMessage(
					"Guess the Number is a guessing game to see if you can guess the number the computer has. The number is generated randomly between 1-100 by default. To change parameters, do !GuessNumber range (number)");
		} else if (event.getMessageContent().startsWith("!GuessNumber range")) {
			String str = event.getMessageContent(); str = str.replace("!GuessNumber range ", "");
			// System.out.println("LINE" + str);
			int number = Integer.parseInt(str); range = number;
			event.getChannel().sendMessage("Range has been changed to " + range);
		} else if (event.getMessageContent().startsWith("!GuessNumber")) {
			String str = event.getMessageContent();
			str = str.replace("!GuessNumber ", "");
			int number = Integer.parseInt(str);
			randomNum = rand.nextInt(range);
			if (number == randomNum) {
					event.getChannel().sendMessage("Nice, you guessed the number, which was " + randomNum);
			}
			else { event.getChannel().sendMessage("Nope, bad guess! The number you guessed was, " + number + " but the answer was " + randomNum);}
		}

	}

}
