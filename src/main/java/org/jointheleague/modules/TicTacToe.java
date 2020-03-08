package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class TicTacToe extends CustomMessageCreateListener {
	
	//--return--  =  something that will get sent as a message 
	
	String[][] board;
	int stage;
	
	String player; //player type X or O
	String computer; //computer type 
	
	final int Start = 0;
	final int Deciding = 1;
	final int Game = 2;
	final int End = 3;
	
	public TicTacToe(String channelName) {
		super(channelName);
		board = new String[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				board[i][j] = "	";
			}
		}
		stage = Start;
		player = "";
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String input = event.getMessageContent();
		String output = "";//all output
		
		if(input.startsWith("!TicCommands")) {
			output += "\"!StartTic\" : begins new tic tac toe game \n"
					+ " \"TicChoose:\'	\' \" : lets you select whether you are X or O \n"
					+ " \"TicPos:(	1-3	,	1-3	)\" : is how you input your choice of spot";//--return--
		}else if(input.startsWith("!StartTic")) {//--stageChange--
			output += "Do you want to be X or O";//--return--
			stage = Deciding;
		}else if(stage == Deciding && input.contains("TicChoose")) {//--stageChange--
			if(input.contains("O")) {
				player = "O";
				computer = "X";
				stage = Game;
				computerMove();
				PrintBoard(event);//--boardAccess--
				output += "Now choose a position";//--return--
			}else if(input.contains("X")){
				player = "X";
				computer = "O";	
				stage = Game;
				output += "Now choose a position";//--return--
			}else {
				output += "That is not a valid choice";//--return--
			}
		}else if(stage == Game && input.startsWith("TicPos")) {//--stageChange--
			String inputs = input.substring(input.indexOf("(")+1, input.indexOf(")"));
			int posX = 0;
			int posY = 0;
			try {
				posX = Integer.parseInt(inputs.substring(0, inputs.indexOf(",")).trim());
				System.out.println(posX);
				posY = Integer.parseInt(inputs.substring(inputs.indexOf(","), inputs.length()).trim());
				System.out.println(posY);
			}catch (Exception e) {
				output += "That is not a valid choice";//--return--
			}
			if(board[posX][posY].trim().equals("")) {
				board[posX][posY] = player;
				PrintBoard(event);//--boardAccess--
				testGameOver();		
				computerMove();
				PrintBoard(event);//--boardAccess--
				testGameOver();
			}else {
				output += "That is an invalid location there is already a " + board[posX][posY] + " there";//--return--
			}
			
		}
		
		event.getChannel().sendMessage(output);
	}
	
	private void testGameOver() {//--stageChange--
		//do
	}
	
	private void computerMove() {
		//do
	}
	
	private void PrintBoard(MessageCreateEvent event) {
		event.getChannel().sendMessage(MakeBoard(event));		
	}
	
	private String MakeBoard(MessageCreateEvent event) {
		String output = ""; 
		//do
		output += "|	" + board[0][0] + "  |   " + board[1][0] + "   |  " + board[2][0] + "\n";
		output += "|	" + "-	+	-	+	-" + "\n";
		output += "|	" + board[0][1] + "  |   " + board[1][1] + "   |  " + board[2][1] + "\n";
		output += "|	" + "-	+	-	+	-" + "\n";
		output += "|	" + board[0][2] + "  |   " + board[1][2] + "   |  " + board[2][2] + "\n";
		
		return output;
	}
}
