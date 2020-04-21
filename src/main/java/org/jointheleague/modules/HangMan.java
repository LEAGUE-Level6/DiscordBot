package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class HangMan extends CustomMessageCreateListener {

	private static boolean gameState = false;
	private int lettersLeft = 0;
	private int guessesLeft = 0;
	private String word = "";
	private ArrayList<Character> wrongGuesses = new ArrayList<Character>();
	private char[] rightGuesses;
	private char[] shownWord;
	private Random r = new Random();
	
	public HangMan(String channelName) {
		super(channelName);
	}

	public void handle(MessageCreateEvent event) {
		String input = event.getMessageContent();
		if (!input.startsWith("!hangman"))
			return;
		
		if(!gameState) {
			if(input.equals("!hangman")) {
				event.getChannel().sendMessage("```Hello and Welcome to Hangman\nThe Rules are simple. Guess a letter and see if it's a part of the word.\n"
						+ "For each letter you get wrong, the hangman will get closer to being complete.\nIf he's complete, you lose!\n\nHere are some commands you can use."
						+ "```\n```!hangman start  (will start a game)\n!hangman end  (will end the game)\n!hangman  (shows this message) \nEach message you type "
						+ "while a game is going will be counted as a guess.```");
			}
			if(input.contains("!hangman start")) {
				gameState = true;
				start(event);
			}

		}
		
		else {
			
			if (input.length() == 10) guess(event);
			System.out.println(input.length());
			
			
			if(guessesLeft<=0||lettersLeft<=0) {
				gameState = false;
			}
			
			if(input.contains("!hangman end"))
				gameState = false;
		}
		
		
	}

	private void guess(MessageCreateEvent event) {
		
		String guess = event.getMessageContent();
		System.out.println(guess.charAt(9));
		if(word.contains(guess.charAt(9)+"")) {
			
		}
		else {
			wrongGuesses.add(guess.charAt(9));
			guessesLeft-=1;
			if(guessesLeft == 0) {
				event.getChannel().sendMessage("```\nYou ran out of guesses!  :(\n\nThe game has ended```");
				gameState = false;
				return;
			}
		}
		StringBuilder wrong = new StringBuilder();
		for (char c : wrongGuesses) {
			wrong.append(c).append(", ");
		}
		StringBuilder guessed = new StringBuilder();
		for (char c : shownWord) {
			guessed.append(c).append(" ");
		}
		
		
		event.getChannel().sendMessage("```\nYour wrong guesses:\n" + wrongGuesses + "\n\n\n" + guessed + "\n\nGuesses Left:  " + guessesLeft + "```");
		
		
	}

	private void start(MessageCreateEvent event) {
		event.getChannel().sendMessage("The game has started!");
		try(BufferedReader br = new BufferedReader(new FileReader(new File("src/main/java/org/jointheleague/modules/wordbank.csv")));) {
			String wordChoices = br.readLine();
			String[] words = wordChoices.split(",");
			for(String w : words) System.out.println(w);
			word = words[r.nextInt(words.length)];
			shownWord = new char[word.length()];
			for (int i = 0; i < shownWord.length; i++) {
				shownWord[i] = '_';
			}
			guessesLeft = 6;
			lettersLeft = word.length();
			
			System.out.println(guessesLeft);
			System.out.println(lettersLeft);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
