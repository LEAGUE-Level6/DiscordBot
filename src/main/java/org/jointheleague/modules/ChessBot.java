package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class ChessBot extends CustomMessageCreateListener{

	String BlackSquare=":black_madium_square:";
	String WhiteSquare=":white_medium_square:";
	String [][] ChessBoard= new String [8][8];
	public ChessBot(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		event.getChannel().sendMessage("switch your Discord to Light Mode, by navigating to 'Appeaarance' in the settings, in order to properly see the chess pieces.");
		
		SetChessBoard();
		
		//spawn chess board
		String Board="";
		for (int i = 0; i < ChessBoard.length; i++) {
			for (int j = 0; j < ChessBoard[i].length; j++) {
				Board+=event.getChannel().sendMessage(ChessBoard[i][j]);
				if (i==7) {
					Board+="/n";
				}
			}
		}
		
		
		
		
	}
	
	void SetChessBoard() {
				//black rook
				ChessBoard [0][0]+="♜";
				ChessBoard [0][7]+="♜";
				
				//black knight
				ChessBoard [0][1]+="♞";
				ChessBoard[0][6]+="♞";
				
				//black bishop
				ChessBoard [0][2]+="♝";
				ChessBoard [0][5]+="♝";
				
				//black queen
				ChessBoard [0][3]+="♛";
				
				//black king
				ChessBoard [0][4]+="♚";
				
				//black pawns
				for (int i = 0; i < 8; i++) {
					ChessBoard[1][i]+="♟";
				}
				
				
				
				
				//White rook
				ChessBoard [7][0]+="♖";
				ChessBoard [7][7]+="♖";
						
				//white knight
				ChessBoard [7][1]+="♘";
				ChessBoard[7][6]+="♘";
						
				//white bishop
				ChessBoard [7][2]+="♗";
				ChessBoard [7][5]+="♗";
						
				//white queen
				ChessBoard [7][3]+="♕";
						
				//white king
				ChessBoard [7][4]+="♔";
				
				//white pawns
				for (int i = 0; i < 8; i++) {
					ChessBoard[6][i]+="♙";
				}
				
		
	}

	
	
}
