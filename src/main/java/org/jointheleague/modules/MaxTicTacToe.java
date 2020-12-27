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
				"Play TicTacToe with the computer." + " Type the command !ttt followed by a keyword"
						+ " for your square. Valid keywords include topright, "
						+ "topmiddle, topleft, centerleft, center, centerright, bottomleft, "
						+ "bottommiddle, bottomright. "
						+ "If your keyword is invalid, simply nothing will happen and "
						+ "you will have to retype the command");
// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException { 
		
		String message = event.getMessageContent();
		Random r = new Random();
		if(message.contains("!resetBoard")) {
			resetBoard();
			event.getChannel().sendMessage("Board succesfully wiped");
		}
		if (message.contains("!ttt")) {	
			String input = message.substring(5);
			int[] playerInput = {3,3};
			if(input.equals("topleft")) {
				playerInput[0]=0; playerInput[1]=0;
			} else if(input.equals("topmiddle")){
				playerInput[0]=0; playerInput[1]=1;
			} else if(input.equals("topright")){
				playerInput[0]=0; playerInput[1]=2;
			} else if(input.equals("centerleft")){
				playerInput[0]=1; playerInput[1]=0;
			} else if(input.equals("center")){
				playerInput[0]=1; playerInput[1]=1;
			} else if(input.equals("centerright")){
				playerInput[0]=1; playerInput[1]=2;
			} else if(input.equals("bottomleft")){
				playerInput[0]=2; playerInput[1]=0;
			} else if(input.equals("bottommiddle")){
				playerInput[0]=2; playerInput[1]=1;
			} else if(input.equals("bottomright")){
				playerInput[0]=2; playerInput[1]=2;
			}
			
			if(playerInput[0]<0 || playerInput[0]>2 || playerInput[1]<0 || playerInput[1]>2 || !validInput(playerInput[0],playerInput[1])) {
				return;
			} else {
				boardInput(playerInput[0],playerInput[1], 'x');
			}
			
			if(checkWinner()=='x') {
				event.getChannel().sendMessage("You win!");
				event.getChannel().sendMessage(printBoard());
				resetBoard();
				return;
			} else if(checkWinner()=='d') {
				event.getChannel().sendMessage("It's a draw!");
				event.getChannel().sendMessage(printBoard());
				resetBoard();
				return;
			}
			
			
			int[] botInput = {r.nextInt(3),r.nextInt(3)};
			while(true) {
				if(validInput(botInput[0],botInput[1])) {
					boardInput(botInput[0],botInput[1], 'o');
					break;
				}
				else {
					botInput[0] = r.nextInt(3);
					botInput[1] = r.nextInt(3);
				}
			}
			if(checkWinner()=='o') {
				event.getChannel().sendMessage("Ha! I win!");
				event.getChannel().sendMessage(printBoard());
				resetBoard();
				return;
			} else if(checkWinner()=='d') {
				event.getChannel().sendMessage("It's a draw!");
				event.getChannel().sendMessage(printBoard());
				resetBoard();
				return;
			} else if(checkWinner()=='-') {
				event.getChannel().sendMessage("There, I played my turn. Use the !ttt command to place your next token	");
				event.getChannel().sendMessage(printBoard());
			}
			
			
		}
	}

	public void boardInput(int row, int col, char letter) {
		board[row][col] = letter;
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
	
	public String printBoard() {
		String s = "";
		
		s+=("+---+---+\n");
		s+=("| " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " |\n");
		s+=("+---+---+\n");
		s+=("| " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " |\n");
		s+=("+---+---+\n");
		s+=("| " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + " |\n");
		s+=("+---+---+");
		
		
		return s;
	}

	public char checkWinner() { // x for X, o for O, d for Draw, - for continue game
		if(board[0][0] == board[0][1] && board[0][1] == board[0][2] && board [0][2] == 'x') {
			return 'x';
		}
		if(board[1][0] == board[1][1] && board[1][1] == board[1][2] && board [1][2] == 'x') {
			return 'x';
		}
		if(board[2][0] == board[2][1] && board[2][1] == board[2][2] && board [2][2] == 'x') {
			return 'x';
		}
		if(board[0][0] == board[1][0] && board[1][0] == board[2][0] && board [2][0] == 'x') {
			return 'x';
		}
		if(board[0][1] == board[1][1] && board[1][1] == board[2][1] && board [2][1] == 'x') {
			return 'x';
		}
		if(board[0][2] == board[1][2] && board[1][2] == board[2][2] && board [2][2] == 'x') {
			return 'x';
		}
		if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] == 'x') {
			return 'x';
		}
		if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] == 'x') {
			return 'x';
		}
		
		
		if(board[0][0] == board[0][1] && board[0][1] == board[0][2] && board [0][2] == 'o') {
			return 'o';
		}
		if(board[1][0] == board[1][1] && board[1][1] == board[1][2] && board [1][2] == 'o') {
			return 'o';
		}
		if(board[2][0] == board[2][1] && board[2][1] == board[2][2] && board [2][2] == 'o') {
			return 'o';
		}
		if(board[0][0] == board[1][0] && board[1][0] == board[2][0] && board [2][0] == 'o') {
			return 'o';
		}
		if(board[0][1] == board[1][1] && board[1][1] == board[2][1] && board [2][1] == 'o') {
			return 'o';
		}
		if(board[0][2] == board[1][2] && board[1][2] == board[2][2] && board [2][2] == 'o') {
			return 'o';
		}
		if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] == 'o') {
			return 'o';
		}
		if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] == 'o') {
			return 'o';
		}
		
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(board[i][j]=='-')
					return '-';
			}
		}
		
		return 'd';

	}
}
