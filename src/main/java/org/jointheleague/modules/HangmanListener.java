package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class HangmanListener extends CustomMessageCreateListener{
	
	final String command = "!startHangman";
	final String command2 = "!guess";
	final String command3 = "!guessWord";
	int lengthOfDictionary;
	String word;
	String displayed;
	boolean gameStarted;
	
    String guessedLetters;
    String guessedLettersWrong;
    int onPic;

	public HangmanListener(String channelName) {
		super(channelName);
		gameStarted = false;
		guessedLetters = "";
		guessedLettersWrong = "";

		onPic = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		//need to check for IS A BOT OR NOT if(event.getMessageAuthor().getId()) {return;};
		
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(command) && !gameStarted) {
			try {
				startGame(event);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(gameStarted) {
			try {
				runGame(event);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public void runGame(MessageCreateEvent event) throws InterruptedException {
		if(event.getMessageContent().contains(command2) && !event.getMessageContent().contains(command3)) {
			char letter = event.getMessageContent().substring(event.getMessageContent().indexOf(command2) + command2.length()).trim().charAt(0);
			if(word.contains(letter + "") && !guessedLetters.contains(letter + "")) {
				guessedLetters += letter;
				for(int i = 0; i < word.length(); i++) {
					if(word.charAt(i) == letter) {
						if(i == 0) {
							displayed = letter + displayed.substring(1);
						}
						else {
							displayed = displayed.substring(0, i * 2) + letter + displayed.substring(i*2 + 1);
						}
					}
				}
				event.getChannel().sendMessage(guessedLettersWrong);
				event.getChannel().sendMessage(new File("src/main/resources/HangmanResources/deathStage " + onPic + ".png"));
				Thread.sleep(10);
				event.getChannel().sendMessage(displayed);
				if(displayed.replaceAll(" ", "").equals(word)) {
					event.getChannel().sendMessage("You Win");
					gameStarted = false;
					onPic = 0;
					guessedLettersWrong = "";
					guessedLetters = "";
				}
			}
			else if(guessedLetters.contains(letter + "")) {
				event.getChannel().sendMessage("You already guessed " + letter);
			}
			else {
				if(onPic < 5) {
					guessedLettersWrong += letter + " ";
					onPic ++;
					event.getChannel().sendMessage(guessedLettersWrong);
					event.getChannel().sendMessage(new File("src/main/resources/HangmanResources/deathStage " + onPic + ".png"));
					Thread.sleep(10);
					event.getChannel().sendMessage(displayed);
				}
				else {
					onPic ++;
					event.getChannel().sendMessage(guessedLettersWrong);
					event.getChannel().sendMessage(new File("src/main/resources/HangmanResources/deathStage " + onPic + ".png"));
					Thread.sleep(10);
					event.getChannel().sendMessage("Game Over" + "\nThe correct word was " + word );
					gameStarted = false;
					onPic = 0;
					guessedLettersWrong = "";
					guessedLetters = "";
					
				}
				
			}
			guessedLetters += letter;
		}
		else if(event.getMessageContent().contains(command3)) {
			String wordG = event.getMessageContent().substring(event.getMessageContent().indexOf(command3) + command3.length()).trim();
			if(word.equals(wordG)) {
				displayed = "";
				for(int i = 0; i < word.length(); i++) {
					displayed += word.charAt(i) + " ";
				}
				event.getChannel().sendMessage(guessedLettersWrong);
				event.getChannel().sendMessage(new File("src/main/resources/HangmanResources/deathStage " + onPic + ".png"));
				Thread.sleep(10);
				event.getChannel().sendMessage(displayed);
				event.getChannel().sendMessage("You Win");
				gameStarted = false;
				onPic = 0;
				guessedLettersWrong = "";
				guessedLetters = "";
				
			}
			else {
				if(onPic < 5) {
					onPic ++;
					event.getChannel().sendMessage(guessedLettersWrong);
					event.getChannel().sendMessage(new File("src/main/resources/HangmanResources/deathStage " + onPic + ".png"));
					Thread.sleep(10);
					event.getChannel().sendMessage(displayed);
				}
				else {
					onPic ++;
					event.getChannel().sendMessage(guessedLettersWrong);
					event.getChannel().sendMessage(new File("src/main/resources/HangmanResources/deathStage " + onPic + ".png"));
					Thread.sleep(10);
					event.getChannel().sendMessage("Game Over" + "\nThe correct word was " + word );
					gameStarted = false;
					onPic = 0;
					guessedLettersWrong = "";
					guessedLetters = "";
					
				}
				
			}
		}
		
	}
	
	public void startGame(MessageCreateEvent event) throws InterruptedException {
		event.getChannel().sendMessage("started");
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/HangmanResources/HangmanWords.txt"));
			int length = 0;
			try {
				String line = reader.readLine();
				
				while(line != null) {
					length++;
					line = reader.readLine();
				}
				reader.close();
				reader = new BufferedReader(new FileReader("src/main/resources/HangmanResources/HangmanWords.txt"));
				Random random = new Random();
				for(int i = 0; i < random.nextInt(length + 1); i++) {
					reader.readLine();
				}
				word = reader.readLine();

				reader.close();
				displayed = "";
				for(int i = 0; i < word.length(); i++) {
					displayed = displayed + "- ";
				}
				event.getChannel().sendMessage(new File("src/main/resources/HangmanResources/deathStage 0.png"));
				Thread.sleep(10);
				event.getChannel().sendMessage(displayed);
				gameStarted = true;
			} catch(IOException e) {
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			event.getChannel().sendMessage("Dictionary not found.");
		}
	}

}
