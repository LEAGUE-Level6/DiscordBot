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
	String uncoverCommand = "!uncover";
	String guessLetter = "!guessl";
	String guessPhrase = "!guessp";
	String startCommand = "!start";
	String phrase = "";
	String begspaces = "";
	int pointTotal = 10;

	public AdvancedHangmanListener(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
	
		if (event.getMessageContent().startsWith(startCommand)) {
			pointTotal = 10;
			event.getChannel().sendMessage(
					"Welcome to Advanced Hangman! Here is how you play: \n 1) You will be presented with a random cliche phrase and given the same amount of boxes as the letters in each word.");
			event.getChannel().sendMessage(
					"2) Here are your commands: \n '!start'-starts a new game \n '!help'-shows all commands for reference \n '!uncover-use to uncover a letter; enter guesses in format (Word#,Letter#) \n '!guessl'-guess a letter \n '!guessp' -guess a phrase");

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
				event.getChannel().sendMessage(R + "");
				for (int i = 0; i < R; i++) {
					phrase = br2.readLine();
				}
					event.getChannel().sendMessage(phrase);
				
				
				for (int i = 0; i < phrase.length(); i++) {
					if (phrase.charAt(i) == ' ') {
						begspaces = begspaces + "  ";
					} else {
						begspaces = begspaces + "_ ";
					}
				}
				event.getChannel().sendMessage("`"+ begspaces + "`");
				event.getChannel().sendMessage("Your points: "+ pointTotal);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if(event.getMessageContent().startsWith("!help")){
			event.getChannel().sendMessage(
					"Here are your commands: \n '!start'-starts a new game \n '!help'-shows all commands for reference \n '!uncover-use to uncover a letter; enter guesses in format (Word#,Letter#) \n '!guessl'-guess a letter \n '!guessp' -guess a phrase");
		}
		if(event.getMessageContent().startsWith("!uncover")) {
			int numLetters = 0;
			String uncoverletter = "";
			int word = Integer.parseInt(event.getMessageContent().substring(9,10));
			int letter = Integer.parseInt(event.getMessageContent().substring(11,12));
			int currentword = 1;
			for(int i = 0; i<phrase.length();i++) {
				numLetters++;
				if(phrase.charAt(i)==' ') {
					currentword++;
				}
				if(currentword == word) {
						if(word == 1) {
							numLetters = letter;
							if(letter == 1) {
							uncoverletter = phrase.substring(i,i+1);
							numLetters = 1;
							}else {
							uncoverletter = phrase.substring(i+letter-1,i+letter);
							}
						}else {
							uncoverletter = phrase.substring(i+letter,i+letter+1);
							numLetters = numLetters + letter;
							
						}
						event.getChannel().sendMessage(uncoverletter);
						break;
				}
				
			}
			event.getChannel().sendMessage(numLetters + "");
			pointTotal = pointTotal-1;
			event.getChannel().sendMessage("Your points: " + pointTotal);
			int cl = 0;
			String newString = "";
			for(int i = 0; i<begspaces.length()-2; i++) {
				if(i%2 == 1) {
				cl++;
					if(cl==numLetters) {
					newString = newString + uncoverletter + " ";
					}else {
					newString = newString + (begspaces.substring(i,i+2));	
					}
			//	}else {
				//	if(i==0) {
						
				//	}else {
						
				//	}
				}
			}
			event.getChannel().sendMessage(newString);
			begspaces = newString;
		
		}

	}}
