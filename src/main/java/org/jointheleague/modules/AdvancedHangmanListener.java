package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class AdvancedHangmanListener extends CustomMessageCreateListener {
	String helpCommand = "!help";
	String guessCommand = "!guess";
	String uncoverCommand = "!uncover";
	String startCommand = "!start";

	public AdvancedHangmanListener(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String phrase = "";
		String begspaces = "";
		if (event.getMessageContent().startsWith(startCommand)) {
			int pointTotal = 10;
			event.getChannel().sendMessage(
					"Welcome to Advanced Hangman! Here is how you play: \n 1) You will be presented with a random cliche phrase and given the same amount of boxes as the letters in each word.");
			event.getChannel().sendMessage(
					"2) Here are your commands: \n '!start'-starts a new game \n '!help'-shows all commands for reference \n '!uncover-use to uncover a letter; enter guesses in format (Word#,Letter#) \n '!guess'-use to make a guess");

			event.getChannel().sendMessage("Here is your phrase:");
			int r = new Random().nextInt();
			try {
				BufferedReader br = new BufferedReader(
						new FileReader("src/main/java/org/jointheleague/modules/cliches"));
				int numLines = 0;
				String line = br.readLine();
				while (line != null) {
					numLines++;
					line = br.readLine();
				}
				BufferedReader br2 = new BufferedReader(
						new FileReader("src/main/java/org/jointheleague/modules/cliches"));
				int R = new Random().nextInt(numLines);
				for (int i = 0; i < R; i++) {
					phrase = br2.readLine();
				}
				
				for (int i = 0; i < phrase.length(); i++) {
					if (phrase.charAt(i) == ' ') {
						begspaces = begspaces + " ";
					} else {
						begspaces = begspaces + "_";
					}
				}
				event.getChannel().sendMessage(begspaces);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
