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
		int now = 0;
		for(int i = 0; i < response.length(); i++) {
			if(response.charAt(i)== ',') {
				now++;
			}
		}
		
		//making string array
		String[] words = response.split(",");
		for(int i = 0; i < words.length;i++) {
			System.out.println(words[i]);
		}
		
		int bruh[] = new int[words.length];
		for(int i = 0; i < bruh.length; i++) {
			bruh[i] = new Random().nextInt(3);
		}
		// 0 = vertical, 1 = horizontal, 2 = diagonal
		//printing
		for(int i = 0; i < words.length; i++) {
			System.out.println(words[i] + " has " + bruh[i]);
		}
		
		char[][] puzzle = new char[10][10];
		int[][] amidoingthisright = new int[10][10];
		
		//putting a random letter in for each place
		for(int bah=0; bah < amidoingthisright.length; bah++) {
			for(int poiuytrewq = 0; poiuytrewq < amidoingthisright[bah].length; poiuytrewq++) {
			amidoingthisright[bah][poiuytrewq] = new Random().nextInt(26);
		}
		}
		for(int i = 0; i < puzzle.length; i++) {
			for(int j = 0; j < puzzle[i].length; j++) {
				puzzle[i][j] = (char)('a' + amidoingthisright[i][j]);
			}
		}
		
		//printing	
		for(int o = 0; o < puzzle.length; o++) {
			System.out.println();
			for(int j = 0; j < puzzle[o].length;j++) {
				System.out.print(puzzle[o][j] + " ");
			}
		}
		
		for(int p = 0; p < words.length; p++) {	
			//vertical
			
			if(bruh[p] == 0) {
			char j[] = words[p].toCharArray();
			int gaaah = new Random().nextInt(10);
				for(int i = new Random().nextInt(10-j.length); i < j.length; i++) {
			puzzle[i][gaaah] = j[i];
			}
			}
			
			//horizontal
			/*
			if(bruh[p] == 1) {
				char itsraining[] = words[p].toCharArray();
				int idwtwmd = new Random().nextInt(10);
					for(int i = new Random().nextInt(10-itsraining.length); i < itsraining.length; i++) {
				puzzle[idwtwmd][i] = itsraining[i];
				}
				*/
			//diagonal >>:(
			//}
		}
		System.out.println();
		for(int o = 0; o < puzzle.length; o++) {
			System.out.println();
			for(int j = 0; j < puzzle[o].length;j++) {
				System.out.print(puzzle[o][j] + " ");
			}
		}
		String[] answer = new String[10];
		for(int o = 0; o < puzzle.length; o++) {
			answer[o] = "";
			for(int j = 0; j < puzzle[o].length;j++) {
				answer[o] = answer[o] + puzzle[o][j] + " ";
			}
		}
		for(int i = 0; i < answer.length; i++) {
		event.getChannel().sendMessage(answer[i]);
		}
	}
	}
	}


