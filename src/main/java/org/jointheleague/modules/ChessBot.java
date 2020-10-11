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
						Board+=ChessBoard[i][j].PieceType;
						
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
				
					ChessBoard[i][j]= new ChessPieces(white,WhiteSquare,"tile");
					
					ChessBoard[i][j+1]=new ChessPieces(black,BlackSquare,"tile");
					
				
			}
				else {
					ChessBoard[i][j]=new ChessPieces(black,BlackSquare,"tile");
					ChessBoard[i][j+1]= new ChessPieces(white,WhiteSquare,"tile");
				}
			}
		}
		
		
				//black rook
				
				ChessBoard [0][0]=new ChessPieces(black,"  ♜  ","rook");
				ChessBoard [0][7]=new ChessPieces(black,"  ♜  ","rook");
				
				//black knight
				
				
				ChessBoard [0][1]=new ChessPieces(black," ♞  ", "knight");
				ChessBoard [0][6]=new ChessPieces(black," ♞  ","knight");
				
				//black bishop
				
				
				ChessBoard [0][2]=new ChessPieces(black,"  ♝  ", "bishop");
				ChessBoard [0][5]=new ChessPieces(black,"  ♝  ", "bishop");
				
				//black queen
				
				
				ChessBoard [0][3]=new ChessPieces(black,"  ♛  ", "queen");
				
				//black king
				
				
				ChessBoard [0][4]=new ChessPieces(black,"  ♚  ", "king");
				
				//black pawns
				for (int i = 0; i < 8; i++) {
				
					
					ChessBoard[1][i]=new ChessPieces(black," ♟ ", "pawn");
				}
				
				
				
				
				//White rook
				
				
				ChessBoard [7][0]= new ChessPieces(white,"  ♖  ","rook");
				ChessBoard [7][7]= new ChessPieces(white,"  ♖  ", "rook");
						
				//white knight
				
				ChessBoard [7][1]= new ChessPieces(white,"  ♘  ","knight");
				ChessBoard[7][6]= new ChessPieces(white,"  ♘  ","knight");
						
				//white bishop
				
				
				ChessBoard [7][2]= new ChessPieces(white,"  ♗  ","bishop");
				ChessBoard [7][5]= new ChessPieces(white,"  ♗  ", "bishop");
						
				//white queen
				
				
				ChessBoard [7][3]= new ChessPieces(white,"  ♕  ","queen");
						
				//white king
				
				
				ChessBoard [7][4]= new ChessPieces(white,"  ♔  ","king");
				
				//white pawns
				for (int i = 0; i < 8; i++) {
					
					
					ChessBoard[6][i]= new ChessPieces(white,"  ♙  ","pawn");
				}
				
				
				
		
	}
	
	public void MovePieces(int OgRow, int OgColumn, int NewRow, int NewColumn) { 
		
		ChessPieces TempPiece = ChessBoard[OgRow][OgColumn];
		
		ChessBoard[NewRow][NewColumn]=TempPiece;
		
		if(OgRow%2==0 && OgColumn%2==0) {
			ChessBoard[OgRow][OgColumn]= new ChessPieces(white, WhiteSquare,"tile");
			
		}
		else if(OgRow%2!=0 && OgColumn%2==0 ) {
			ChessBoard[OgRow][OgColumn]= new ChessPieces(black, BlackSquare,"tile");
			
		}
		else if (OgRow%2==0 && OgColumn%2!=0 ) {
			ChessBoard[OgRow][OgColumn]= new ChessPieces(black, BlackSquare,"tile");
			
		}
		else if (OgRow%2!=0 && OgColumn%2!=0 ) {
			ChessBoard[OgRow][OgColumn]= new ChessPieces(white, WhiteSquare, "tile");
		}
		
	}
	
	public void TakePiece() {
		
		
		
	}
	
	

	
	
}
