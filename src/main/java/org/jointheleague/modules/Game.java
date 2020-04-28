package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class Game {

	private static boolean gameState = false;
	private int lettersLeft = 0;
	private int guessesLeft = 0;
	private String word = "";
	private ArrayList<Character> wrongGuesses = new ArrayList<Character>();
	private char[] rightGuesses;
	private char[] shownWord;
	private float score = 0;
	private int guessesTotal = 0;
	private Random r = new Random();
	
	public Game(Random r, int guessCount) {
		this.r = r;
		guessesTotal = guessCount;
		guessesLeft = guessCount;
	}
	
	
	public boolean guess(MessageCreateEvent event) {
		
		char guess = event.getMessageContent().charAt(9);
		System.out.println(guess);
		if(word.contains(guess+"")) {
			for (int i = 0; i < shownWord.length; i++) {
				if(guess==word.charAt(i)) {
					shownWord[i] = guess;
				}
			}
			
			
		}
		else {
			if (!wrongGuesses.contains(guess)) {
				wrongGuesses.add(guess);
				guessesLeft-=1;
				if(guessesLeft <= 0) {
					event.getChannel().sendMessage("```\nYou ran out of guesses!  :(\n\nThe game has ended```");
					return false;
				}
			}
			else event.getChannel().sendMessage("```\nYou've already guessed that!```");
		}
		return display(event);
	}
	public boolean display(MessageCreateEvent event) {
		StringBuilder wrong = new StringBuilder();
		for (char c : wrongGuesses) {
			wrong.append(c).append(", ");
		}
		StringBuilder guessed = new StringBuilder();
		lettersLeft = 0;
		for (char c : shownWord) {
			if(c=='_') lettersLeft+=1;
			guessed.append(c).append(" ");
		}
		if(lettersLeft <= 0) {
			event.getChannel().sendMessage("```\nYou won! \nThe word was  " + word + "```"); 
			score = 100*guessesLeft/(guessesTotal);
			return false;
		}
		
		else event.getChannel().sendMessage("```\nYour wrong guesses:\n" + wrongGuesses + "\n\n\n" + guessed + "\n\nGuesses Left:  " + guessesLeft + "```");
		return true;
	}
	
	public void start(MessageCreateEvent event) {
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
			lettersLeft = word.length();
			
			System.out.println(guessesLeft);
			System.out.println(lettersLeft);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
