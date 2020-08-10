package org.jointheleague.modules;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;

public class BattleShip extends CustomMessageCreateListener {
	String[][] player1 = new String[10][10];
	String[][] player2 = new String[10][10];
	String[][] currentGrid = new String[10][10];
	int turn = 0;
	String ship = "🚢";

	private static final String gameCommand = "!battleship";
	private static final String instructionCommand = "!battleship-instructions";
	private static final String startCommand = "!start";
	// wave emoji: 🌊
	// ship emoji: 🚢

	public BattleShip(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		int cords = 0;
		boolean validPlay = false;
		boolean numEntered = false;
		boolean testing = false;
		player1 = setPlayerGrid(player1);
		player2 = setPlayerGrid(player2);

		// TODO Auto-generated method stub
		if (event.getMessageContent().equals(gameCommand)) {
			event.getChannel()
					.sendMessage("Enter *!battleship-instructions* for how to play\nEnter *!start* to begin the game");
		}
		if (event.getMessageContent().equals(instructionCommand)) {
			event.getChannel().sendMessage("*[HOW TO PLAY]*");
		}
		if (event.getMessageContent().equals(startCommand)) {
			if (turn % 2 == 0) {
				currentGrid = player1;
			} else if (turn % 2 == 1) {
				currentGrid = player2;
			}
			event.getChannel().sendMessage(display(currentGrid));
			event.getChannel().sendMessage("Player: " + (turn % 2) + " enter coordinates to place ship: ");

		} else if (event.getMessageContent().contentEquals("!test")) {
			cords = 1;
			numEntered = true;
		} else if (event.getMessageContent().contentEquals("!one")) {
			currentGrid = player1;
			testing = true;
		} else if (event.getMessageContent().contentEquals("!two")) {
			currentGrid = player2;
			testing = true;
		}
		if (numEntered == true) {
			currentGrid[2][3] = ship;
			turn++;
			event.getChannel().sendMessage(display(currentGrid));
		} 
		else if (testing == true) {
			event.getChannel().sendMessage(display(currentGrid));
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
		matrix += "-----------------------------------------------------------------------------------\n";
		matrix += "0\t\t\t1\t\t\t2\t\t\t3\t\t\t4\t\t\t5\t\t\t6\t\t\t7\t\t\t8\t\t\t9\n\n ";

		return matrix;
	}

	public static String[][] setPlayerGrid(String[][] playerGrid) {

		for (int row = 0; row < playerGrid.length; row++) {
			for (int col = 0; col < playerGrid[0].length; col++) {
				playerGrid[row][col] = "🌊\t";
			}
		}
		return playerGrid;
	}

	public static boolean validate(int row, int column, String[][] board) {
		// checking to see if column and row is in bounds
		if (column < 0 || column > board[0].length || row < 0 || row > board.length) {
			return false;
		}

//		if (condition) {
//			
//		}

		return true;
	}
}
