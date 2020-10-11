package org.jointheleague.modules;

public class ChessPieces {
	String PieceColor="";
	String PieceType="";
	String PieceName="";
	
	
	public ChessPieces(String PieceColor, String PieceType, String PieceName) {
		
		this.PieceColor =PieceColor;
		this.PieceType =PieceType;
		this.PieceName =PieceName;
	}
	
	public String getPieceColor() {
		
		return PieceColor;
	}
	
	public void setPieceColor(String PieceColor) {
		
		this.PieceColor=  PieceColor;
	}
	
	public String getPieceType() {
		
		return PieceType;
	}
	
	public void setPieceType(String PieceType) {
		
		this.PieceType=  PieceType;
	}
	
	public void setPieceName(String PieceName) {
		
		this.PieceName= PieceName;
	}
	
	public String getPieceName() {
		return PieceName;
		
	}
}
