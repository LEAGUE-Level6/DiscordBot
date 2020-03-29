package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class cardGame extends CustomMessageCreateListener{
	private static final String COMMAND = "!play";
	private static final String COMMAND2 = "!deal";

ArrayList<String> SUITS ;
List<String> suitTypes = Arrays.asList("Hearts","Spades","Clubs", "Diamonds");
List<String> cardTypes =  Arrays.asList("2", "3","4","5","6","7","8", "9","10", "Jack", "Queen", "King", "Ace");
    ArrayList<String> deck ;
ArrayList<String> player1;
ArrayList<String> computer;
String currentCard;
	public cardGame(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
	
	     if(event.getMessageContent().contains(COMMAND)){
	 		event.getChannel().sendMessage("Welcome to WAR! The rules of the game are simple. !deal a card, if your card is greater than the bot's, you get to keep both cards. If the cards are the same, declare !war and each player deals another card. The first to get all the cards wins. ");
	 		

	     }
	     else if(event.getMessageContent().contains(COMMAND2)) {
	    	 event.getChannel().sendMessage("Your card is : "+ currentCard);
shuffleCards(player1);	     }
	     
	    }
	public void shuffleCards(ArrayList<String> player) {
	deck.addAll(suitTypes);
		deck.addAll(cardTypes);
		 int n = SUITS.size() * player.size();
		 ArrayList<String> deck = new ArrayList<String>(n);
	        for (int i = 0; i < player.size(); i++) {
	            for (int j = 0; j < SUITS.size(); j++) {
	            	String deckAtLoop = deck.get(SUITS.size()*i+j);
	        deckAtLoop   = player.get(i) + " of " + SUITS.get(j);
	            }
	        }

	       for (int i = 0; i < n; i++) {
	            int r = i + (int) (Math.random() * (n-i));
	            String temp = deck.get(r);
	            String rTemp = deck.get(r);
	            String iTemp = deck.get(i);
	        rTemp = deck.get(i);
	        iTemp = temp;	        }

	        // print shuffled deck
	        for (int i = 0; i < n; i++) {
	            System.out.println(deck.get(i));
	        }
	
    }
	void playWar() {
splitUpDeck();
}
void splitUpDeck() {

}

 
}


