package org.jointheleague.modules;

public class Card {

	public int value;
	public int suit;
	
	public Card(int v, int s) {
		value = v;
		suit = s;
	}
	
	public String toString() {
		String fin = "";
		
		switch(value) {
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
			fin += value;
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
