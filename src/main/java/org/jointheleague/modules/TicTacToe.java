package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class TicTacToe extends CustomMessageCreateListener {
	public TicTacToe(String channelName) {
		super(channelName);
	}

	private static final String COMMAND = "!ttt";

	public void handle(MessageCreateEvent event) throws APIException {
		String[] tiles = new String[10];
		String message;
		char num;
		int tile;
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = " ";
		}
		if (event.getMessageContent().contains(COMMAND)) {
			message = event.getMessageContent().substring(5, event.getMessageContent().length());
			if (message.contains("start")) {
				event.getChannel().sendMessage("__ 1 | 2 | 3 __");
				event.getChannel().sendMessage("__4 | 5 | 6 __");
				event.getChannel().sendMessage(" 7 | 8 | 9 ");
				event.getChannel().sendMessage("Use ! ttt p1 (tile#) to place an 'X' on that tile");
				event.getChannel().sendMessage("Use ! ttt p2 (tile#) to place an 'O' on that tile");
			} else if (message.contains("p1") | message.contains("p2")) {
				num = message.charAt(3);
				tile = Integer.parseInt(num + "");
				if (message.contains("p1")) {
					for (int i = 0; i < tiles.length; i++) {
						if (i == tile) {
							System.out.println(tiles[i]);
							if (tiles[i] != " ") {
								event.getChannel().sendMessage("Sorry, that spot is already taken :(");
							} else {
								tiles[i] = "X";
								event.getChannel()
										.sendMessage("__ " + tiles[1] + " | " + tiles[2] + " | " + tiles[3] + " __");
								event.getChannel()
										.sendMessage("__ " + tiles[4] + " | " + tiles[5] + " | " + tiles[6] + " __");
								event.getChannel()
										.sendMessage("** **" + tiles[7] + " | " + tiles[8] + " | " + tiles[9] + "** **");
							}
						}
					}
				} else if (message.contains("p2")) {
					for (int i = 0; i < tiles.length; i++) {
						if (i == tile) {
							if (tiles[i] != " ") {
								event.getChannel().sendMessage("Sorry, that spot is already taken :(");
							} else {
								tiles[i] = "O";
								event.getChannel()
										.sendMessage("__ " + tiles[1] + " | " + tiles[2] + " | " + tiles[3] + " __");
								event.getChannel()
										.sendMessage("__ " + tiles[4] + " | " + tiles[5] + " | " + tiles[6] + " __");
								event.getChannel()
										.sendMessage("** **" + tiles[7] + " | " + tiles[8] + " | " + tiles[9] + "** **");
							}
						}
					}
				}
			}
		}
	}
}
