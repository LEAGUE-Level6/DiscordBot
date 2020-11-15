package org.jointheleague.modules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class MaxTicTacToe extends CustomMessageCreateListener {
	private char[][] board = new char[3][3];
	private String turn;
	boolean playing;

	public MaxTicTacToe(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed("!playTicTacToe",
				"Play TicTacToe with the computer." + " Strict keyword usage follows: upperleft, top, upperright, "
						+ "left, center, right, lowerleft, bottom, and lowerright.");
// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains("!playTicTacToe")) {
			resetBoard();
			turn = "X";
			playing = true;
			while (true) {
				event.getChannel()
						.sendMessage("Enter row then column number (Ex. 1 2 for top middle or 3 3 for bottom right");
				String[] input = event.getMessageContent().split(" ");
				int row = Integer.parseInt(input[0]);
				int col = Integer.parseInt(input[1]);
				if (input.length == 2) {
					if (row > 0 && row <= 3 && col > 0 && col <= 3) {
						break;
					}

				}
				board[row][col] = 'x';
			}
		}
	}

	public void printBoard() {
	}

	public void resetBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = '-';
			}
		}
	}

	public String checkWinner() {
		return "";

	}
}
