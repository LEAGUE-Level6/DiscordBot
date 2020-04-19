package org.jointheleague.modules;

import java.util.Scanner;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Connect4 extends CustomMessageCreateListener {
	public Connect4(String channelName) {
		super(channelName);
	}

	private static final String gameCommand = "!game";
	private static final String zero = "0";
	private static final String one = "1";
	private static final String two = "2";
	private static final String three = "3";
	private static final String four = "4";
	private static final String five = "5";
	private static final String six = "six";

	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().equals(gameCommand)) {

			String[][] grid = new String[6][7];

			// initialize array
			for (int row = 0; row < grid.length; row++) {
				for (int col = 0; col < grid[0].length; col++) {
					grid[row][col] = "0";
				}
			}

			int turn = 1;
			String player = "🔴";
			boolean winner = false;

			// play a turn
			while (winner == false && turn <= 42) {
				boolean validPlay;
				String inputPlay = "";
				int play = 0;
				do {
					event.getChannel().sendMessage(display(grid));
					event.getChannel().sendMessage("Player: " + player + " Enter a column: ");
					if(event.getMessageContent().equals(zero)) {
						play = 0;
					}
					else if(event.getMessageContent().equals(one)) {
						play = 1;
					}
					else if(event.getMessageContent().equals(two)) {
						play = 2;
					}
					else if(event.getMessageContent().equals(three)) {
						play = 3;
					}
					else if(event.getMessageContent().equals(four)) {
						play = 4;
					}
					else if(event.getMessageContent().equals(five)) {
						play = 5;
					}
					else if(event.getMessageContent().equals(six)) {
						play = 6;
					}

					event.getChannel().sendMessage(play + "");
					validPlay = validate(play, grid);
				} while (validPlay == false);
			
				for (int row = grid.length - 1; row >= 0; row--) {
					if (grid[row][play] == " ") {
						grid[row][play] = player;
						break;
					}
				}
			}
			event.getChannel().sendMessage(display(grid));
		}
	}

	public static String display(String[][] grid) {
		String matrix = "\n\n";
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				matrix += grid[row][col] + "	";
			}
			matrix += "\n";
		}
		matrix += "---------------------------\n";
		matrix += "0	1	 2	 3	 4	 5	 6\n\n  ";

		return matrix;
	}

	public static boolean validate(int column, String[][] board) {
		// checking to see if this column is in bounds
		if (column < 0 || column > board[0].length) {
			return false;
		}

		// checking to see if column is full
		if (board[0][column] != "0") {
			return false;
		}

		return true;
	}
}
