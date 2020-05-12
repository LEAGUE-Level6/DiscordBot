package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
					event.getChannel().sendMessage("```\nYou ran out of guesses!  :(\n\nThe game has ended. The word was:  " + word + "```");
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
			event.getChannel().sendMessage("```\nYou won! \nThe word was:  " + word + "```"); 
			score = 100*guessesLeft/(guessesTotal);
			saveScore(event);
			return false;
		}
		
		else event.getChannel().sendMessage("```\nYour wrong guesses:\n" + wrongGuesses + "\n\n\n" + guessed + "\n\nGuesses Left:  " + guessesLeft + "```");
		return true;
	}
	
	public static ArrayList<PlayerScore> currentScores() {
		ArrayList<PlayerScore> scores = new ArrayList<PlayerScore>();
		File scoreFile = new File("scoreList");
		if(!scoreFile.exists()) {
			return scores;
		}
		
		try(BufferedReader scoreReader = new BufferedReader(new FileReader(scoreFile));) {
		String scoreData = scoreReader.readLine();	
		if(scoreData==null) {
			return scores;
		}
		String[] userScoreList = scoreData.split(";");
		for (String str : userScoreList) {
			if(!str.equals("")) {
				String[] currentScore = str.split(",");
				scores.add(new PlayerScore(Float.parseFloat(currentScore[1]), currentScore[0]));
			}
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scores;
	}
	
	public void saveScore(MessageCreateEvent event) {
		ArrayList<PlayerScore> scoreList = currentScores();
		
		PlayerScore finalScore = new PlayerScore(score, event.getMessageAuthor().getName());
		scoreList.add(finalScore);
		Collections.sort(scoreList);
		try(BufferedWriter scoreWriter = new BufferedWriter(new FileWriter(new File("scoreList")))) {
			for (int i = 0; i < scoreList.size() && i < 10; i++) {
				scoreWriter.write(scoreList.get(i).toString());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
