package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class ticTacToe extends CustomMessageCreateListener {

	public ticTacToe(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	private static final String COMMAND = "!ticTacToe";
	private static final String player1 = "!p1";
	private static final String player2 = "!p2";

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String[] tiles = null;
		String message = event.getMessageContent();
		String num = message.charAt(4)+"";
		int tile = Integer.parseInt(num);
		for (int i = 1; i < 10; i++) {
			tiles[i] = " ";
		}
		if (event.getMessageContent().equals(COMMAND)) {
			event.getChannel().sendMessage("   |   |   ");
			event.getChannel().sendMessage("___|___|___");
			event.getChannel().sendMessage("   |   |   ");
			event.getChannel().sendMessage("___|___|___");
			event.getChannel().sendMessage("   |   |   ");
			event.getChannel().sendMessage("   |   |   ");
		}
		else if(event.getMessageContent().contains(player1)) {
			for (int i = 1; i < tiles.length+1; i++) {
				if(i == tile) {
					if(tiles[i] != " ") {
						tiles[i] = tile+"";
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
