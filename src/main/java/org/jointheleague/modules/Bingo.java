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
	String discordCard = "\n\t  ";
	String newNumber = "";
	
	public Bingo(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();

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
			randomNumArr.get(random);
			randomNumArr.remove(random);
			newNumber += random;
			event.getChannel().sendMessage(newNumber);
		}
	}
}
