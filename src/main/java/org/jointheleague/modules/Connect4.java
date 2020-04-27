package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Connect4 extends CustomMessageCreateListener {
	public Connect4(String channelName) {
		super(channelName);
	}

	String[][] grid = new String[6][7];
	private static final String gameCommand = "!game";
	private static final String zero = "0";
	private static final String one = "1";
	private static final String two = "2";
	private static final String three = "3";
	private static final String four = "4";
	private static final String five = "5";
	private static final String six = "6";

	public void handle(MessageCreateEvent event) {
		int play = 0;
		boolean validPlay = false;
		String inputPlay = "";
		int turn = 1;
		boolean winner = false;
		boolean numEntered = false;

		String player = "🔴";
		if (event.getMessageContent().equals(gameCommand)) {
			// initialize array
			for (int row = 0; row < grid.length; row++) {
				for (int col = 0; col < grid[0].length; col++) {
					grid[row][col] = "⚪\t";
				}
			}

			event.getChannel().sendMessage(display(grid));
			event.getChannel().sendMessage("Player: " + player + " Enter a column: ");
		} else if (event.getMessageContent().equals(zero)) {
			play = 0;
			numEntered = true;
			validPlay = validate(play, grid);
		} else if (event.getMessageContent().equals(one)) {
			play = 1;
			numEntered = true;
			validPlay = validate(play, grid);
		} else if (event.getMessageContent().equals(two)) {
			play = 2;
			numEntered = true;
			validPlay = validate(play, grid);
		} else if (event.getMessageContent().equals(three)) {
			play = 3;
			numEntered = true;
			validPlay = validate(play, grid);
		} else if (event.getMessageContent().equals(four)) {
			play = 4;
			numEntered = true;
			validPlay = validate(play, grid);
		} else if (event.getMessageContent().equals(five)) {
			play = 5;
			numEntered = true;
			validPlay = validate(play, grid);
		} else if (event.getMessageContent().equals(six)) {
			play = 6;
			numEntered = true;
			validPlay = validate(play, grid);
		}
		if (numEntered == true && validPlay == true) {
			for (int row = grid.length - 1; row >= 0; row--) {
				if (grid[row][play] == "⚪\t") {
					grid[row][play] = player += "\t";
					break;
				}
			}
			event.getChannel().sendMessage(display(grid));
		} else if (numEntered == true && validPlay == false) {
			event.getChannel().sendMessage("Please enter a valid play");
		}

	}

	public static String display(String[][] grid) {
		String matrix = "\n\n";
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				matrix += grid[row][col] + "\t";
			}
			matrix += "\n";
		}
		matrix += "--------------------------------------------------------\n";
		matrix += "0\t\t\t1\t\t\t2\t\t\t3\t\t\t4\t\t\t5\t\t\t6\n\n  ";

		return matrix;
	}

	public static boolean validate(int column, String[][] board) {
		// checking to see if this column is in bounds
		if (column < 0 || column > board[0].length) {
			return false;
		}

		// checking to see if column is full
		if (board[0][column] != "⚪\t") {
			return false;
		}

		return true;
	}
}
