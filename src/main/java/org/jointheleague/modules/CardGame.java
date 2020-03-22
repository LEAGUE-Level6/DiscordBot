package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class CardGame extends CustomMessageCreateListener {
	
	private ArrayList<Card> deck;
	
	private final String start = "!start";
	private final String help = "!help";
	private final String rules = "!rules";
	
	public CardGame(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		String message = event.getMessageContent();
		
		//start or restart the game
		if(message.startsWith(start)) {
			event.getChannel().sendMessage("Shuffling decks...");
			
			newDeck();
			deck = shuffle(deck);
			
			deal();
		}
		//get help
		else if(message.startsWith(help)) {
			
		}
		//get rules
		else if(message.startsWith(rules)) {
			
		}
	}
		

	//makes new deck
	public void newDeck() {
		deck = new ArrayList<Card>();
		for(int i = 0; i < 52; i++) {
			deck.add(new Card(i%13 +1, i/13 +1));
		}
	}
	
	//shuffles given list
	public ArrayList<Card> shuffle(ArrayList<Card> cards) {
		ArrayList<Card> shuffled = new ArrayList<Card>();
		Random rand = new Random();
		int size = cards.size();
		
		for(int i = 0; i < size; i++) {
			int index = rand.nextInt(cards.size());
			shuffled.add(cards.remove(index));
		}
		
		return shuffled;
	}
	
	//deals necessary cards
	public void deal() {
		
		
		
	}
	
	//takes top card of deck
	public Card pull() {
		return deck.remove(0);
	}
	
	//returns string version of hand
	public String handToString(ArrayList<Card> hand) {
		String fin = "\t| ";
		int i = 1;
		
		for(Card c : hand) {
			fin += "**" + i + ":**   " + c + "\t| ";
			i++;
		}
		
		return fin;
	}
	
	//check if given hand has won
	public boolean hasWon() {
		return false;
	}
}
