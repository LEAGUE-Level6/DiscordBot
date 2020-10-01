package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class TicTacToe extends CustomMessageCreateListener {
	public TicTacToe(String channelName) {
		super(channelName);
	}

	private static final String COMMAND = "!ttt";
	String[] tiles = new String[10];
	boolean gameInProgress = false;
	boolean turn = false; // false = p1, true = p2
	int counter = 0;

	public void makeTiles() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = "   ";
		}
	}

	public boolean gameOver(String[] list) {
		if (list[1] == list[2] && list[2] == list[3] && list[1] != "   ") {
			return true;
		} else if (list[1] == list[4] && list[4] == list[7] && list[1] != "   ") {
			return true;
		} else if (list[2] == list[5] && list[5] == list[8] && list[2] != "   ") {
			return true;
		} else if (list[4] == list[5] && list[5] == list[6] && list[4] != "   ") {
			return true;
		} else if (list[3] == list[6] && list[6] == list[9] && list[3] != "   ") {
			return true;
		} else if (list[7] == list[8] && list[8] == list[9] && list[7] != "   ") {
			return true;
		} else if (list[1] == list[5] && list[5] == list[9] && list[1] != "   ") {
			return true;
		} else if (list[3] == list[5] && list[5] == list[7] && list[3] != "   ") {
			return true;
		}
		return false;
	}

	public void handle(MessageCreateEvent event) throws APIException {
		String message; // gets the message part after !ttt
		char num; // gets tile number in a char value
		int tile; // tile entered to put mark

		if (event.getMessageContent().contains(COMMAND)) {
			message = event.getMessageContent().substring(5, event.getMessageContent().length());
			if (message.contains("start")) {
				makeTiles();
				gameInProgress = true;
				turn = false;
				counter = 0;
				event.getChannel().sendMessage("** 1 | 2 | 3 **");
				event.getChannel().sendMessage("**__     |     |     __**");
				event.getChannel().sendMessage("** 4 | 5 | 6 **");
				event.getChannel().sendMessage("**__     |     |     __**");
				event.getChannel().sendMessage("** 7 | 8 | 9 **");
				event.getChannel().sendMessage("**     |     |     **");
				event.getChannel().sendMessage("Use **!.ttt p1** (tile#) to place an 'X' on that tile");
			} else if (gameInProgress) {
				if (message.contains("p1") | message.contains("p2")) {
					num = message.charAt(3);
					tile = Integer.parseInt(num + "");
					if (message.contains("p1") && !turn) {
						for (int i = 0; i < tiles.length; i++) {
							if (i == tile) {
								if (tiles[i] != "   ") {
									event.getChannel().sendMessage("Sorry, that spot is already taken");
								} else {
									tiles[i] = "X";
									event.getChannel().sendMessage(
											"** " + tiles[1] + " | " + tiles[2] + " | " + tiles[3] + " **");
									event.getChannel().sendMessage("**__     |     |     __**");
									event.getChannel().sendMessage(
											"** " + tiles[4] + " | " + tiles[5] + " | " + tiles[6] + " **");
									event.getChannel().sendMessage("**__     |     |     __**");
									event.getChannel().sendMessage(
											"** " + tiles[7] + " | " + tiles[8] + " | " + tiles[9] + " **");
									event.getChannel().sendMessage("**     |     |     **");
									if (gameOver(tiles)) {
										event.getChannel().sendMessage(
												"Player 1 has gotten 3 in a row! Start a new game using **!.ttt start**");
										gameInProgress = false;
									} else {
										event.getChannel()
												.sendMessage("Use **!.ttt p2** (tile#) to place an 'O' on that tile");
										turn = true;
										counter++;
										if (counter >= 9) {
											event.getChannel()
													.sendMessage("Tie! Start a new game using **!.ttt start**");
											gameInProgress = false;
										}
									}
								}
							}
						}
					} else if (message.contains("p2") && turn) {
						for (int i = 0; i < tiles.length; i++) {
							if (i == tile) {
								if (tiles[i] != "   ") {
									event.getChannel().sendMessage("Sorry, that spot is already taken");
								} else {
									tiles[i] = "O";
									event.getChannel().sendMessage(
											"** " + tiles[1] + " | " + tiles[2] + " | " + tiles[3] + " **");
									event.getChannel().sendMessage("**__     |     |     __**");
									event.getChannel().sendMessage(
											"** " + tiles[4] + " | " + tiles[5] + " | " + tiles[6] + " **");
									event.getChannel().sendMessage("**__     |     |     __**");
									event.getChannel().sendMessage(
											"** " + tiles[7] + " | " + tiles[8] + " | " + tiles[9] + " **");
									event.getChannel().sendMessage("**     |     |     **");
									if (gameOver(tiles)) {
										event.getChannel().sendMessage(
												"Player 2 has gotten 3 in a row! Start a new game using **!.ttt start**");
										gameInProgress = false;
									} else {
										event.getChannel()
												.sendMessage("Use **!.ttt p1** (tile#) to place an 'X' on that tile");
										turn = false;
										counter++;
									}
								}
							}
						}
					} else {
						event.getChannel().sendMessage("Please wait for your opponent, it is not your turn");
					}
				}
			} else {
				event.getChannel().sendMessage("Enter a valid commant or start a new game using **!.ttt start**");
			}
		}
	}
}
