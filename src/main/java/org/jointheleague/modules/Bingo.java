package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Bingo extends CustomMessageCreateListener {

	private static final String BINGO = "!bingo";
	private static final String NEW = "!new";
	String bingoCard[][] = new String[5][5];
	ArrayList<Integer> randomNumbers = new ArrayList<Integer>(); // keep numbers the same
	ArrayList<Integer> randomNumArr; // remove numbers from
	int random;
	int index;
	String discordCard = "\n\t  ";
	String newOutput = "";

	public Bingo(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		Random r = new Random();

		if (message.equalsIgnoreCase("!bingo")) {
			while (randomNumbers.size() < 60) {
				random = (int) (Math.random() * 99 + 1);
				if (!randomNumbers.contains(random)) {
					randomNumbers.add(random);
				}
			}
			System.out.println(randomNumbers);
			randomNumArr = new ArrayList<Integer>(randomNumbers);

			for (int i = 0; i < bingoCard.length; i++) {
				for (int j = 0; j < bingoCard[i].length; j++) {
					if (i == 2 && j == 2) {
						bingoCard[i][j] = "" + "FREE" + " ";
					} else {
						int tempRandom = randomNumArr.remove((int) (Math.random() * randomNumArr.size() - 1));
						if (tempRandom > 9) {
							bingoCard[i][j] = " " + tempRandom + "\t";
						} else {
							bingoCard[i][j] = " 0" + tempRandom + "\t";
						}
					}
					discordCard += bingoCard[i][j] + "   ";
				}
				discordCard += "\n";
			}

			event.getChannel().sendMessage(discordCard);
		}

		if (message.equalsIgnoreCase("!new")) {
			//getting the index of a random number from the arraylist with all of the numbbers
			index = r.nextInt(randomNumbers.size());
			int newRandom = randomNumbers.get(index);
			//puts 0 in front of single digits
			if (newRandom > 9) {
				newOutput += newRandom;
			} else {
				newOutput += "0" + newRandom;
			}
			//sends the new number to the discord chat
			event.getChannel().sendMessage(newOutput);

			// using for loop to find index of the new random number (newOutput)
			for (int i = 0; i < bingoCard.length; i++) {
				for (int j = 0; j < bingoCard[i].length; j++) {
					// if the bingo card has the number in the card, replace it with a check mark
					if (bingoCard[i][j].contains(newOutput)) {
						bingoCard[i][j] = bingoCard[i][j].replace(newOutput, ":white_check_mark:");
						updateDiscordCard();
						event.getChannel().sendMessage(discordCard);
					}
				}
			}

			System.out.println("no match");

			// removes the new random number from the array and sets the text equal to an
			// empty String
			randomNumbers.remove(index);
			newOutput = "";
		}
	}

	// updates the discord card by adding the numbers to the discordCard string
	public void updateDiscordCard() {
		discordCard = "";
		for (int i = 0; i < bingoCard.length; i++) {
			for (int j = 0; j < bingoCard[i].length; j++) {
				discordCard += bingoCard[i][j] + "   ";
			}
			discordCard += "\n";
		}
	}
}