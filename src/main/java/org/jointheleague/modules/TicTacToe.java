package org.jointheleague.modules;

import java.awt.Point;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class TicTacToe extends CustomMessageCreateListener { 
	
	String[][] board = {{"   ", "   ", "   "},{"   ", "   ", "   "},{"   ", "   ", "   "}};
	
	int stage;
	
	boolean shortcuts;
	
	String player; 
	String computer;
	
	final int Start = 0;
	final int Deciding = 1;
	final int GamePlayer = 2;
	final int GameComputer = 3;
	
	public TicTacToe(String channelName) {
		super(channelName);
		stage = Start;
		shortcuts = true;
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String input = "";
		if(!event.getMessageAuthor().isYourself()) {
			input = event.getMessageContent();
		}
		
		String output = "";
		
		if(input.startsWith("!TicCommands") || input.startsWith("!TC")) {
			output += "!Tic.start \n> begins new tic tac toe game \n\n"
					+ "TicChoose \'	X(or)O	\' \n> lets you select whether you are X or O \n\n"
					+ "TicPos (	0-2	,	0-2	) \n> is how you input where your mark goes \n\n"
					+ "Tic.display \n> re-draws the board to the server \n\n"
					+ "Tic.end \n> quits the game \n\n"
					+ "!Tic.enableShort '	true(or)false	' \n> enables or disables shorcuts which are: (!TC,  !T.s,  TC,  TP,  T.d,  T.e,  !T.eS)";//--return--
		}else if(input.startsWith("!Tic.enableShort") || (input.startsWith("!T.eS") && shortcuts == true) ) {
			if(input.contains("true")) {
				shortcuts = true;
				event.getChannel().sendMessage("shortcut status changed to true");
			}else if(input.contains("false")) {
				shortcuts = false;
				event.getChannel().sendMessage("shortcut status changed to false");
			}
		}else if(input.startsWith("!StartTic") || (input.startsWith("!T.s") && shortcuts == true)) {//--stageChange--
			output += "Do you want to be X or O";//--return--
			stage = Deciding;
		}else if(stage == Deciding && (input.startsWith("TicChoose") || (input.startsWith("TC") && shortcuts == true))) {//--stageChange--
			if(input.contains("O")) {
				player = "O";
				computer = "X";
				stage = GamePlayer;
				doMove(computer, 0, 0, true);
				printBoard(event);//--boardAccess--
				output += "Now choose a position";//--return--
			}else if(input.contains("X")){
				player = "X";
				computer = "O";	
				stage = GamePlayer;
				output += "Now choose a position";//--return--
			}else {
				output += "That is not a valid choice";//--return--
			}
		}else if(stage == GamePlayer && (input.startsWith("TicPos") || (input.startsWith("TP") && shortcuts == true))) {//--stageChange--
			if(input.contains("cheat")) {
				doMove(player, 0, 0, true);
				stage = GameComputer;
			}else {
				String inputs = input.substring(input.indexOf("(")+1, input.indexOf(")"));
				try {	
					int posX = Integer.parseInt(inputs.substring(0, inputs.indexOf(",")).trim());
					int posY = Integer.parseInt(inputs.substring(inputs.indexOf(",")+1, inputs.length()).trim());
					if(board[posX][posY].isBlank()) {
						doMove(player, posX, posY, false);
						stage = GameComputer;
					}else {
						output += "That is an invalid location there is already a " + board[posX][posY] + " there";//--return--
					}
				}catch (Exception e) {
					output += "That is not a valid choice";//--return--
				}
			}
		}else if(stage == GamePlayer && (input.startsWith("Tic.display") || (input.startsWith("T.d") && shortcuts == true))){
			printBoard(event);//--boardAccess--
		}else if((stage == GamePlayer || stage == Deciding) && (input.startsWith("Tic.end") || ( input.startsWith("T.e")&& shortcuts == true))) {
			event.getChannel().sendMessage("The game has been ended");
			endGame();
		}
		if(stage == GameComputer) {
			dealIfGameOver(event);	
			if(stage == GameComputer) {//testing again in case game is over from previous
				doMove(computer, 0, 0, true);
				dealIfGameOver(event);
				stage = GamePlayer;
			}
		}
		event.getChannel().sendMessage(output);// final return
	}
	
	private void dealIfGameOver(MessageCreateEvent event) {																				System.out.println("dealIfGameOver");
		String s = getWinner();
		if(!s.isBlank()) {
			if(s.contains(player)) {
				printBoard(event);//--boardAccess--
				event.getChannel().sendMessage(s + " (the player) " + "WON!");
				endGame();
			}else if(s.contains(computer)) {
				printBoard(event);//--boardAccess--
				event.getChannel().sendMessage(s + " (the computer) " + "WON!");
				endGame();
			}else {
				printBoard(event);//--boardAccess--
				Random r = new Random();
				int i = r.nextInt(3);
				if(i == 0) {
					event.getChannel().sendMessage("Perfectly balanced, as all things should be");
				}else if(i == 1) {
					event.getChannel().sendMessage("That IS the law of equivalent exchange");
				}else {
					event.getChannel().sendMessage("its a tie!");
				}
				endGame();
			}
		}else {
			printBoard(event);//--boardAccess--
		}
	}
	
	private String getWinner() {
		for(int i = 0; i < 3; i ++) {
			if(equals3(board[i][0],board[i][1],board[i][2])) {
				return board[i][0];
			}else if(equals3(board[0][i],board[1][i],board[2][i])) {
				return board[0][i];
			}
		}
		if(equals3(board[0][0],board[1][1],board[2][2])) {
			return board[0][0];
		}else if(equals3(board[0][2],board[1][1],board[2][0])) {
			return board[0][2];
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j].isBlank()) {
					i = 3;
					j = 3;
				}else if(j == 2 && i == 2) {
					return "tie";
				}
			}
		}
		return "";
	}
	
	private boolean equals3(String a, String b, String c) {
		if(a.equals(b) && b.equals(c) && !a.isBlank()) {
			return true;
		}
		return false;
	}
	
	private void endGame() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				board[i][j] = "   ";
			}
		}
		stage = Start;
		computer = "";
		player = "";
	}
	
	private void doMove(String currentPlayer, int x, int y, boolean b) {
		if(b) {
			findBestMove(currentPlayer);
		}else {
			board[x][y] = currentPlayer;
		}
	}
	
	private void findBestMove(String currentPlayer) {
		int bestScore = -100;
		Point bestMove = new Point(0,0);
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j].isBlank()) {
					board[i][j] = currentPlayer;
					int score = minimax(board, false, currentPlayer, 0);
					board[i][j] = "   ";
					if(score == 1) {
						bestMove.setLocation(i, j);
						i = 3;
						j = 3;
					}else if(score > bestScore) {
						bestScore = score;
						bestMove.setLocation(i, j);
					}
				}
			}
		}
		board[bestMove.x][bestMove.y] = currentPlayer;
	}
	
	private int minimax(String[][] arr, boolean isMaximizing, String currentPlayer, int depth) {
		String s = getWinner();
		if(!s.isBlank()) {
			if(s.equals(currentPlayer)) {
				return 10;
			}else if(s.equals("tie")) {
				return 0;
			}else {
				return -10;
			}
		}
		if(isMaximizing){
			int bestScore = -100;
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(board[i][j].isBlank()) {
						board[i][j] = currentPlayer;
						bestScore = Math.max(minimax(board, false, currentPlayer, depth+1), bestScore);
						board[i][j] = "   ";
					}
				}
			}
			return bestScore;
		}else {
			int worstScore = 100;
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(board[i][j].isBlank()) {
						if(currentPlayer.contentEquals("X")) {
							board[i][j] = "O";
						}else {
							board[i][j] = "X";
						}
						worstScore = Math.min(minimax(board, true, currentPlayer, depth +1), worstScore);
						board[i][j] = "   ";
					}
				}
			}
			return worstScore;
		}
	}
	
	private void printBoard(MessageCreateEvent event) {
		String output = "\n "
					  + "|		 " + board[0][0] + "    |    " + board[1][0] + "    |    " + board[2][0] + "\n"
					  + "|		  " + "-	+	-	+	-"                                          + "\n"
					  + "|		 " + board[0][1] + "    |    " + board[1][1] + "    |    " + board[2][1] + "\n"
					  + "|		  " + "-	+	-	+	-"                                          + "\n"
					  + "|		 " + board[0][2] + "    |    " + board[1][2] + "    |    " + board[2][2] + "\n"
					  + "\n ";		
		event.getChannel().sendMessage(output);
	}
}
