package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class ChessBot extends CustomMessageCreateListener{

	String BlackSquare=" :black_medium_square: ";
	String WhiteSquare=" :white_medium_square: ";
	ChessPieces [][] ChessBoard= new ChessPieces [8][8];
	String BlackPlayer="";
	String WhitePlayer="";
	String black="black";
	String white="white";
	public ChessBot(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		
		
		
		
		if (event.getMessageContent().contains("!chess")) {
			SetChessBoard();
			event.getChannel().sendMessage("switch your Discord to Light Mode, by navigating to 'Appeaarance' in the settings, in order to properly see the chess pieces.");
			event.getChannel().sendMessage(SpawnChessBoard());
			event.getChannel().sendMessage("Type -white to take control of the white pieces, and -black to control the black ones");
			event.getChannel().sendMessage("The Rows of the board are numbered 0-7, and the columns are also numbered 0-7");
			event.getChannel().sendMessage("Type -move to make your move, then type the coordinates of the piece you want to move and the coordinates of where you want to move that piece");
			event.getChannel().sendMessage("For example to move the pawn from (1,0) to (2,0) you would type -move 10 to 20");
			
		}
		
		if (event.getMessageContent().contains("-white")) {
			WhitePlayer+=event.getMessageAuthor();
			
		}
		else if (event.getMessageContent().contains("-black")) {
			BlackPlayer+=event.getMessageAuthor();
		}
		
		if (event.getMessageContent().contains("-move")) {
			String Message= event.getMessageContent();
			
			int OgRow= Integer.parseInt(Message.substring(6, 7));
			int OgColumn= Integer.parseInt(Message.substring(7, 8));
			int NewRow =Integer.parseInt(Message.substring(12, 13));
			int NewColumn =Integer.parseInt(Message.substring(13, 14));
			
			MovePieces(OgRow,OgColumn,NewRow,NewColumn);
			
			event.getChannel().sendMessage(SpawnChessBoard());
		}
		
		
		
		
	}
	
	public String SpawnChessBoard() {
		//spawn chess board
				String Board="";
				for (int i = 0; i < ChessBoard.length; i++) {
					for (int j = 0; j < ChessBoard[i].length; j++) {
						Board+=ChessBoard[i][j];
						
						  if (j==7) { Board+=" \n"; }
						 
					}
				}
				//board
				
		return Board;
		
	}
	
	public void SetChessBoard() {
		//tile pieces
		for (int i = 2; i <= 5; i++) {
			for (int j = 0; j <ChessBoard[i].length-1; j+=2) {
			if(i%2==0 && j%2==0) {
				
					ChessBoard[i][j].setPieceType(WhiteSquare);
					ChessBoard[i][j].setPieceColor(null);
					ChessBoard[i][j+1].setPieceType(BlackSquare);
					ChessBoard[i][j+1].setPieceColor(null);
					
				
			}
				else {
					ChessBoard[i][j].setPieceType(BlackSquare);
					ChessBoard[i][j].setPieceColor(null);
					ChessBoard[i][j+1].setPieceType(WhiteSquare);
					ChessBoard[i][j+1].setPieceColor(null);
				}
			}
		}
		
		
				//black rook
				ChessBoard [0][0].setPieceType("  ♜  ");
				ChessBoard [0][7].setPieceType("  ♜  ");
				
				ChessBoard [0][0].setPieceColor(black);
				ChessBoard [0][7].setPieceColor(black);
				
				//black knight
				ChessBoard [0][1].setPieceType(" ♞  ");
				ChessBoard [0][6].setPieceType(" ♞  ");
				
				ChessBoard [0][1].setPieceColor(black);
				ChessBoard [0][6].setPieceColor(black);
				
				//black bishop
				ChessBoard [0][2].setPieceType("  ♝  ");
				ChessBoard [0][5].setPieceType("  ♝  ");
				
				ChessBoard [0][2].setPieceColor(black);
				ChessBoard [0][5].setPieceColor(black);
				
				//black queen
				ChessBoard [0][3].setPieceType("  ♛  ");
				
				ChessBoard [0][3].setPieceColor(black);
				
				//black king
				ChessBoard [0][4].setPieceType("  ♚  ");
				
				ChessBoard [0][4].setPieceColor(black);
				
				//black pawns
				for (int i = 0; i < 8; i++) {
					ChessBoard[1][i].setPieceType(" ♟ ");
					
					ChessBoard[1][i].setPieceColor(black);
				}
				
				
				
				
				//White rook
				ChessBoard [7][0].setPieceType("  ♖  ");
				ChessBoard [7][7].setPieceType("  ♖  ");
				
				ChessBoard [7][0].setPieceColor(white);
				ChessBoard [7][7].setPieceColor(white);
						
				//white knight
				ChessBoard [7][1].setPieceType("  ♘  ");
				ChessBoard[7][6].setPieceType("  ♘  ");
				
				ChessBoard [7][1].setPieceColor(white);
				ChessBoard[7][6].setPieceColor(white);
						
				//white bishop
				ChessBoard [7][2].setPieceType("  ♗  ");
				ChessBoard [7][5].setPieceType("  ♗  ");
				
				ChessBoard [7][2].setPieceColor(white);
				ChessBoard [7][5].setPieceColor(white);
						
				//white queen
				ChessBoard [7][3].setPieceType("  ♕  ");
				
				ChessBoard [7][3].setPieceColor(white);
						
				//white king
				ChessBoard [7][4].setPieceType("  ♔  ");
				
				ChessBoard [7][4].setPieceColor(white);
				
				//white pawns
				for (int i = 0; i < 8; i++) {
					ChessBoard[6][i].setPieceType("  ♙  ");
					
					ChessBoard[6][i].setPieceColor(white);
				}
				
				
				
		
	}
	
	public void MovePieces(int OgRow, int OgColumn, int NewRow, int NewColumn) { 
		String TempPiece= new String(ChessBoard[OgRow][OgColumn]);
		
		
		ChessBoard[NewRow][NewColumn]=TempPiece;
		
		if(OgRow%2==0 && OgColumn%2==0) {
			ChessBoard[OgRow][OgColumn]= WhiteSquare;
			
		}
		else if(OgRow%2!=0 && OgColumn%2==0 ) {
			ChessBoard[OgRow][OgColumn]= BlackSquare;
			
		}
		else if (OgRow%2==0 && OgColumn%2!=0 ) {
			ChessBoard[OgRow][OgColumn]= BlackSquare;
		}
		else if (OgRow%2!=0 && OgColumn%2!=0 ) {
			ChessBoard[OgRow][OgColumn]= WhiteSquare;
		}
		
	}
	
	public void TakePiece() {
		
		
	}
	
	

	
	
}
