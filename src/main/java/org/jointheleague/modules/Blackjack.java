package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class Blackjack extends CustomMessageCreateListener {
	
	private ArrayList<Card> deck;
	private ArrayList<Card> playerHand;
	private ArrayList<Card> botHand;
	
	private final String start = "!start";
	private final String hit = "!hit";
	private final String stand = "!stand";
	private final String setAce = "!setAce";
	private final String help = "!help";
	private final String rules = "!rules";
	
	/* To do:
	 * add hit
	 * add stand
	 * add set Ace
	 * add help
	 * 
	 * finish rules
	 * finish handValue()
	 * finsih checkBust()
	 * 
	 * test
	 */
	
	public Blackjack(String channelName) {
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
			playerHand = new ArrayList<Card>();
			botHand = new ArrayList<Card>();
			
			deal();
			
			event.getChannel().sendMessage(getBoard());
			
			if(handValue(playerHand) == 10 && hasAce(playerHand) && handValue(botHand) == 10 && hasAce(botHand)) 
				event.getChannel().sendMessage("Both you and the bot have naturals. You tie.");
			if(handValue(playerHand) == 10 && hasAce(playerHand)) 
				event.getChannel().sendMessage("You have a natural! You win!");
			if(handValue(botHand) == 10 && hasAce(botHand)) 
				event.getChannel().sendMessage("The bot has a natural. You lose.");
		}
		//hit
		if(message.startsWith(hit)) {
			
			
			
		}
		//get help
		else if(message.startsWith(help)) {
			
		}
		//get rules
		else if(message.startsWith(rules)) {
			event.getChannel().sendMessage(
					"This is a modified version of Blackjack. \n" +
					"If you need help playing in discord, enter the command **" + help + "**\n" +
					"\n" +
					"If you have played Blackjack, this version does not have a dealer. It is also more simplistic. \n" +
					"Each player is dealt 2 cards face up. \n" +
					"The goal is to have a hand as close to 21 as possible. \n" +
					"On your turn, you have the option to hit or stand. Hit means you get a new card, stand means you don't. Once you stand, you can draw no more cards. \n"
					);
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
		playerHand.add(pull());
		botHand.add(pull());
		playerHand.add(pull());
		botHand.add(pull());
	}
	
	//takes top card of deck
	public Card pull() {
		return deck.remove(0);
	}
	
	//returns string version of hand
	public String handToString(ArrayList<Card> hand) {
		String fin = "\t| ";
		
		for(Card c : hand) {
			fin += "   " + c + "\t| ";
		}
		
		return fin;
	}
	
	//returns string version of board without values
		public String getBoard() {
			return	"Bot: " + handToString(botHand) + "\n" +
					"You: " + handToString(playerHand) + "\n"; 
		}
	
	//returns string version of board with values
	public String getValueBoard() {
		return	"Bot: " + handToString(botHand) + "\n" +
				"Bot value: " + handValue(botHand) + "\n" +
				"You: " + handToString(playerHand) + "\n" +
				"Your value: " + handValue(playerHand);
	}

	//returns hand value
	public int handValue(ArrayList<Card> hand) {
		int total = 0;
		
		for(Card c : hand)
			total += c.value;
		
		//if(hasAce(hand)) return -total;
		return total;
	}
	
	public boolean hasAce (ArrayList<Card> hand) {
		for(Card c : hand)
			if(c.displayValue == 1) return true;
		
		return false;
	}
	
	//check if given hand has bust
	public boolean checkBust(ArrayList<Card> hand) {
		//add code
		
		return false;
	}
}
