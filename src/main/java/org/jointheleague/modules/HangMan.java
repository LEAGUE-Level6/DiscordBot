package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.javacord.api.event.message.MessageCreateEvent;

public class HangMan extends CustomMessageCreateListener {

	private static boolean gameState = false;
	private static int lettersLeft = 0;
	private static int guessesLeft = 0;
	private static String word = "";
	
	public HangMan(String channelName) {
		super(channelName);
	}

	public void handle(MessageCreateEvent event) {
		String input = event.getMessageContent();		
		
		if(!gameState) {
			if(input.equals("!hangman")) {
				event.getChannel().sendMessage("```Hello and Welcome to Hangman\nThe Rules are simple. Guess a letter and see if it's a part of the word.\n"
						+ "For each letter you get wrong, the hangman will get closer to being complete.\nIf he's complete, you lose!\n\nHere are some commands you can use."
						+ "```\n```!hangman start  (will start a game)\n!hangman end  (will end the game)\n!hangman  (shows this message) \nEach message you type "
						+ "while a game is going will be counted as a guess.```");
			}
			if(input.contains("!hangman start")) {
				gameState = true;
				start();
			}

		}
		
		if(gameState) {
			if(guessesLeft<=0||lettersLeft<=0) {
				gameState = false;
			}
			
			if(input.contains("!hangman end"));
			gameState = false;
		}
		
		
	}

	private void start() {
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File("src/main/java/org/jointheleague/modules/wordbank.csv")));) {
			String wordChoices = br.readLine();
			String[] words = wordChoices.split(",");
			for(String w : words) System.out.println(w);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
