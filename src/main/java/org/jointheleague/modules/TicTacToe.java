package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class TicTacToe extends CustomMessageCreateListener {

	int[][] board;
	int stage;
	
	String player;
	
	final int Start = 0;
	final int Deciding = 1;
	final int Game = 2;
	final int End = 3;
	
	public TicTacToe(String channelName) {
		super(channelName);
		board = new int[3][3];
		stage = Start;
		player = "";
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String input = event.getMessageContent();
		String output = "";
		
		if(input.startsWith("!TicCommands")) {
			event.getChannel().sendMessage("\"!StartTic\" : begins new tic tac toe game \n"
					+ " \"TicChoose:\'	\' \" : lets you select whether you are X or O \n"
					+ " \"Ticpos:(	,	)\" : is how you input your choice of spot");
		}
		
		if(input.startsWith("!StartTic")) {
			event.getChannel().sendMessage("Do you want to be X or O");
			stage = Deciding;
		}
		
		if(stage == Deciding && input.contains("TicChoose:")) {
			if(input.contains("X")){
				player = "X";
				stage = Game;
			}else if(input.contains("O")){
				player = "O";
				stage = Game;
			}
		}
		
		if(stage == Game && input.startsWith("Ticpos:")) {
			//if() {
				
			//}
		}
	}
	
	public void testGameOver() {
		//do
	}
	
	public String MakeBoard(MessageCreateEvent event) {
		String output = ""; 
		//do
		return output;
	}
	
	public void PrintBoard(MessageCreateEvent event) {
		event.getChannel().sendMessage(MakeBoard(event));		
	}
}
