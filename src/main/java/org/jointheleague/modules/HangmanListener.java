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
	int lengthOfDictionary;
	String word;
	String usedWords;
	String displayed;
	boolean gameStarted;

	public HangmanListener(String channelName) {
		super(channelName);
		gameStarted = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		usedWords = "";
		if(event.getMessageContent().contains(command) && !gameStarted) {
			startGame(event);
		}
		else {
			event.getChannel().sendMessage(event.getMessageContent());
		}
		
	}
	public void startGame(MessageCreateEvent event) {
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
					displayed += "_ ";
				}
				displayed = displayed.trim();
				
				event.getChannel().sendMessage(new File("src/main/resources/HangmanResources/deatStage 0"));
				event.getChannel().sendMessage(displayed);
			} catch(IOException e) {
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			event.getChannel().sendMessage("Dictionary not found.");
		}
	}

}
