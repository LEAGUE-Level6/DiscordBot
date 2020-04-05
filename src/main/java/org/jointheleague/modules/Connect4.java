package org.jointheleague.modules;

import java.util.Scanner;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Connect4 extends CustomMessageCreateListener {
	public Connect4(String channelName) {
		super(channelName);
	}

	private static final String gameCommand = "!game";

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
				int play;
				do {
					event.getChannel().sendMessage(display(grid));
					event.getChannel().sendMessage("Player: " + player + " Enter a column: ");

					inputPlay += event.getMessageContent();
					play = Integer.parseInt(inputPlay);

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
