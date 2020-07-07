package org.jointheleague.modules;

public class Card {

	public int displayValue;
	public int value;
	public int suit;
	public int declaredSuit;
	
	public Card(int v, int s) {
		displayValue = v;
		suit = s;
		
		if(v >= 11 && v <= 13) value = 10;
		else value = v;

		declaredSuit = 0;
	}
	
	public Card(int v, int s, int d) {
		displayValue = v;
		suit = s;
		declaredSuit = d;
	}
	
	public String toString() {
		String fin = "";
		

		

		switch(declaredSuit) {
		case 1:
			return "♤";
		case 2:
			return "♧";
		case 3:
			return "♡";
		case 4:
			return "♢";
		}
		
		switch(displayValue) {
		case 1:
			fin += "A";
			break;
		case 11:
			fin += "J";
			break;
		case 12:
			fin += "Q";
			break;
		case 13: 
			fin += "K";
			break;
		default:
			fin += displayValue;
			break;
		}
		
		fin += "/";
		

		switch(suit) {
		case 1:
			fin += "♤";
			break;
		case 2:
			fin += "♧";
			break;
		case 3:
			fin += "♡";
			break;
		case 4:
			fin += "♢";
			break;
		}
		
		return fin;
	}
	//♠♣♥♦ ♤♧♡♢
}
