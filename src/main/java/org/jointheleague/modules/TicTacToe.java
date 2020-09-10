package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class TicTacToe {
	public TicTacToe() {
	// TODO Auto-generated constructor stub
	}
	
	private static final String COMMAND = "!ttt";

	public void handle(MessageCreateEvent event) throws APIException{
		String[] tiles = null;
		String message = event.getMessageContent().substring(5, event.getMessageContent().length());
		String num = message.charAt(3)+"";
		int tile = Integer.parseInt(num);
		for (int i = 1; i < 10; i++) {
			tiles[i] = " ";
		}
		if (event.getMessageContent().contains(COMMAND)) {
			if(message.contains("start")) {
				event.getChannel().sendMessage(" 1 | 2 | 3 ");
				event.getChannel().sendMessage("___|___|___");
				event.getChannel().sendMessage(" 4 | 5 | 6 ");
				event.getChannel().sendMessage("___|___|___");
				event.getChannel().sendMessage(" 7 | 8 | 9 ");
				event.getChannel().sendMessage("   |   |   ");
				event.getChannel().sendMessage("Use /ttt p1 (tile#) to place an 'X' on that tile");
				event.getChannel().sendMessage("Use /ttt p2 (tile#) to place an 'O' on that tile");
			}
			else if(message.contains("p1")|message.contains("p2")) {
				if(message.contains("p1")){
					for (int i = 1; i < tiles.length+1; i++) {
						if(i == tile) {
							if(tiles[i] != " ") {
								tiles[i] = "X";
								 event.getChannel().sendMessage(" "+tiles[0]+" | "+tiles[1]+" | "+tiles[2]+" ");
								 event.getChannel().sendMessage("___|___|___");
								 event.getChannel().sendMessage(" "+tiles[3]+" | "+tiles[4]+" | "+tiles[5]+" ");
								 event.getChannel().sendMessage("___|___|___");
								 event.getChannel().sendMessage(" "+tiles[6]+" | "+tiles[7]+" | "+tiles[8]+" ");
								 event.getChannel().sendMessage("   |   |   ");
							}
							else {
								event.getChannel().sendMessage("Sorry, that spot is already taken :(");
							}
						}
					}
				}
				else if(message.contains("p2")){
					for (int i = 1; i < tiles.length+1; i++) {
						if(i == tile) {
							if(tiles[i] != " ") {
								tiles[i] = "O";
								 event.getChannel().sendMessage(" "+tiles[0]+" | "+tiles[1]+" | "+tiles[2]+" ");
								 event.getChannel().sendMessage("___|___|___");
								 event.getChannel().sendMessage(" "+tiles[3]+" | "+tiles[4]+" | "+tiles[5]+" ");
								 event.getChannel().sendMessage("___|___|___");
								 event.getChannel().sendMessage(" "+tiles[6]+" | "+tiles[7]+" | "+tiles[8]+" ");
								 event.getChannel().sendMessage("   |   |   ");
							}
							else {
								event.getChannel().sendMessage("Sorry, that spot is already taken :(");
							}
						}
					}
				}
			}
		}
	}
}
