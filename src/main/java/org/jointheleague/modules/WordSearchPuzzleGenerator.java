package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class WordSearchPuzzleGenerator extends CustomMessageCreateListener {

	public static final String COMMAND = "!generate word search puzzle:";

	public WordSearchPuzzleGenerator(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Creates a 10 x 10 random word search puzzle with the words you want. word limit is 10, words must not be more than 10 letters");

	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {
			String response = event.getMessageContent().replace("!generate word search puzzle: ", "");
			int now = 0;
			for (int i = 0; i < response.length(); i++) {
				if (response.charAt(i) == ',') {
					now++;
				}
			}

			// making string array
			String[] words = response.split(",");
			for (int i = 0; i < words.length; i++) {
				if(words[i].length()>10) {
					String vocab = words[i];
					System.out.println(words[i]);
					event.getChannel().sendMessage("the word " + vocab + " is more than 10 letters. removing.");
					System.arraycopy(words, i-1, words, i, words.length-i-1);
				}
				// System.out.println(words[i]);
			}

			char[][] puzzle = new char[10][10];
			int[][] amidoingthisright = new int[10][10];
			List<Boolean>column = new ArrayList<>();
			for(int i = 0; i <10; i++) {
				column.add(false);
			}
			// putting a random letter in for each place
			for (int bah = 0; bah < amidoingthisright.length; bah++) {
				for (int poiuytrewq = 0; poiuytrewq < amidoingthisright[bah].length; poiuytrewq++) {
					amidoingthisright[bah][poiuytrewq] = new Random().nextInt(26);
				}
			}
			for (int i = 0; i < puzzle.length; i++) {
				for (int j = 0; j < puzzle[i].length; j++) {
					puzzle[i][j] = '.';//(char)('a' + amidoingthisright[i][j]);
				}
			}

			// printing
			for (int o = 0; o < puzzle.length; o++) {
				// System.out.println();
				for (int j = 0; j < puzzle[o].length; j++) {
					// System.out.print(puzzle[o][j] + " ");
				}
			}
			
			boolean[][] taken = new boolean[10][10];
			for (int i = 0; i < taken.length; i++) {
				for (int j = 0; j < taken[i].length; j++) {
					taken[i][j] = false;
				}
			}
			
			for (int p = 0; p < words.length; p++) {
				// vertical
					char j[] = words[p].toCharArray();
					int yIndex = 0; //new Random().nextInt(column.size());
					System.out.println(yIndex);
					//if(column.get(yIndex)==false) {
					if(taken[0][yIndex]=false) {
					for (int i = 0; i < j.length; i++) {
						char b = j[i];
						puzzle[i][yIndex] = b;
						taken[i][yIndex] = true;
						}
					}else {
						for(int i = 0; i < 10; i++) {
							if(taken[i][yIndex] = false) {
								for(int k = 0; k < j.length; k++) {
									char b = j[i];
									puzzle[i][yIndex] = b;
								}
								}else {
									event.getChannel().sendMessage("unsuccessful");
								}
							}
						}
					}
						//System.out.println(puzzle[i]);
					//column.remove(yIndex);
					//}
			
			
			// System.out.println();
			for (int o = 0; o < puzzle.length; o++) {
				 System.out.println();
				for (int j = 0; j < puzzle[o].length; j++) {
				 System.out.print(puzzle[o][j] + " ");
				}
			}
			String[] answer = new String[10];
			for (int o = 0; o < puzzle.length; o++) {
				answer[o] = "";
				for (int j = 0; j < puzzle[o].length; j++) {
					answer[o] = answer[o] + puzzle[o][j] + " ";
				}
			}
			for (int i = 0; i < answer.length; i++) {
				event.getChannel().sendMessage(answer[i]);
			}
		}
	}
}
