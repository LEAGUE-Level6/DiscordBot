package org.jointheleague.modules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class MaxTicTacToe extends CustomMessageCreateListener {
	private char[][] board = new char[3][3];

	public MaxTicTacToe(String channelName) {
		super(channelName);
		resetBoard();
		helpEmbed = new HelpEmbed("!ttt",
				"Play TicTacToe with the computer." + " Type the command followed by two integers for the row and column"
						+ " (ex. '!ttt 1 2' to place your piece on the top middle spot or '!ttt 2 2 for the center spot.");
// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		Random r = new Random();
		if (message.contains("!ttt")) {
			String input = message.substring(5);
			String[] nums = input.split(" ");
			if(nums.length!=2) return;
			else boardInput(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]));
			int[] botInput = {r.nextInt(3),r.nextInt(3)};
			while(true) {
				if(validInput(botInput[0],botInput[1])) {
					boardInput(botInput[0],botInput[1]);
					break;
				}
				else {
					botInput[0] = r.nextInt(3);
					botInput[1] = r.nextInt(3);
				}
			}
			event.getChannel().sendMessage("There, I played my turn. Use the !ttt command to place your next token	");
		}
	}

	public void boardInput(int row, int col) {
		board[row][col] = 'x';
	}
	public boolean validInput(int row, int col) {
		if(board[row][col]=='-') 
			return true;
		return false;
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
