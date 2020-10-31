package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;


public class WordSearchPuzzleGenerator extends CustomMessageCreateListener{

	public static final String COMMAND = "!generate word search puzzle:";
	public WordSearchPuzzleGenerator(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Creates a 10 x 10 random word search puzzle with the words you want. except the words need to be less than 11 letters long");
		
	}

	@Override
	public void handle(MessageCreateEvent event){
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {
		String response = event.getMessageContent().replace("!generate word search puzzle: ", "");
		System.out.println(response);
		int now = 0;
		for(int i = 0; i < response.length(); i++) {
			if(response.charAt(i)== ',') {
				now++;
			}
		}
		//System.out.println(now + " words");
		String[] words = response.split(",");
		
		for(int i = 0; i < words.length;i++) {
			System.out.println(words[i]);
		}
		int bruh[] = new int[words.length];
		for(int i = 0; i < bruh.length; i++) {
			bruh[i] = new Random().nextInt(3);
		}
		// 0 = vertical, 1 = horizontal, 2 = diagonal
		char[][] puzzle = new char[10][10];
		for(int i = 0; i < puzzle.length; i++) {
			for(int j = 0; j < puzzle[i].length; j++) {
				//puzzle[i][j] = 
			}
		}
		for(int p = 0; p < words.length; p++) {
			
			if(bruh[p] == 0) {
			char j[] = words[p].toCharArray();
			int gaaah = new Random().nextInt(10);
				for(int i = 0; i < j.length; i++) {
			puzzle[i][gaaah] = j[i];
			}
			}
	
		}
			}
		}
		
	}


