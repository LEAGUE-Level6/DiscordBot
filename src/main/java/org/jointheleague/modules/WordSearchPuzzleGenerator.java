package org.jointheleague.modules;

import java.util.Arrays;
import java.util.Collections;
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
		
		int voh[] = new int[words.length];
		for(int i = 0; i < voh.length; i++) {
			voh[i] = 0; //new Random().nextInt(3);
		}
		// 0 = vertical, 1 = horizontal, 2 = diagonal
		//printing
		for(int i = 0; i < words.length; i++) {
			System.out.println(words[i] + " has " + voh[i]);
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
				puzzle[i][j] = 'a';//(char)('a' + amidoingthisright[i][j]);
			}
		}
		
		//printing	
		for(int o = 0; o < puzzle.length; o++) {
			System.out.println();
			for(int j = 0; j < puzzle[o].length;j++) {
				System.out.print(puzzle[o][j] + " ");
			}
		}
		boolean[][] taken = new boolean[10][10];
		for(int i = 0; i < taken.length; i++) {
			for(int j = 0; j < taken[i].length; j++) {
				taken[i][j] = false;
			}
		}
		int[] locations = new int[words.length];
		for(int i = 0; i < locations.length; i++) {
			locations[i] = new Random().nextInt(10);
		}
		for(int h = 0; h < locations.length; h++) {
			System.out.println(locations[h]);
		}
		
		for(int h = 0; h < locations.length; h++) {
			System.out.println(locations[h]);
		}
		for(int p = 0; p < words.length; p++) {	
			//vertical
			System.out.println(locations[p]);
			if(voh[p] == 0) {
			char j[] = words[p].toCharArray();
			//int sp = new Random().nextInt(10-j.length);
			//int test = j.length + 1;
				for(int i = 0; i < j.length; i++) {
				puzzle[i][locations[p]]=j[i];
				}
			}
			
			//horizontal
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


