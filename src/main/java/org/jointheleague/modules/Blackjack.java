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
	
	private final String start = "bj!start";
	private final String hit = "!hit";
	private final String stand = "!stand";
	private final String help = "bj!help";
	private final String rules = "bj!rules";
	
	public boolean playingBlackjack = false;
	private boolean playerStanding = false;
	private boolean botStanding = false;
	
	/* To do:
	 * testing
	 */
	
	public Blackjack(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		String message = event.getMessageContent();
		
		//start or restart the game
		if(message.startsWith(start)) {
			playingBlackjack = true;
			event.getChannel().sendMessage("Shuffling decks...");
			
			newDeck();
			deck = shuffle(deck);
			playerHand = new ArrayList<Card>();
			botHand = new ArrayList<Card>();
			
			playerStanding = false;
			botStanding = false;
			
			deal();
			
			if(handValue(playerHand) == 11 && hasAce(playerHand) && handValue(botHand) == 11 && hasAce(botHand)) {
				event.getChannel().sendMessage(getBoard() + "\nBoth you and the bot have naturals. You tie. To play again, enter **" + start + "**");
				playingBlackjack = false;
				return;
			}
			else if(handValue(playerHand) == 11 && hasAce(playerHand)) {
				event.getChannel().sendMessage(getBoard() + "\nYou have a natural! You win! To play again, enter **" + start + "**");
				playingBlackjack = false;
				return;
			}
			else if(handValue(botHand) == 11 && hasAce(botHand)) { 
				event.getChannel().sendMessage(getBoard() + "\nThe bot has a natural. You lose. To play again, enter **" + start + "**");
				playingBlackjack = false;
				return;
			}
			
			if(hasAce(playerHand)) {
				playerHand.get(aceLoc(playerHand)).value = 11;
			}
			if(hasAce(botHand)) {
				botHand.get(aceLoc(botHand)).value = 11;
			}
			
			event.getChannel().sendMessage(getValueBoard());
		}
		//hit
		else if(message.startsWith(hit) && playingBlackjack) {
			event.getChannel().sendMessage(hit());
		}
		//stand
		else if(message.startsWith(stand) && playingBlackjack) {
			event.getChannel().sendMessage(stand());
		}
		//get help
		else if(message.startsWith(help)) {
			newDeck();
			deck = shuffle(deck);
			playerHand = new ArrayList<Card>();
			botHand = new ArrayList<Card>();
			
			deal();
			
			event.getChannel().sendMessage(
					"This is a modified version of Blackjack.\n" +
					"If you need the rules, enter the command **" + rules + "**\n"+
					"\n" +
					"To play the game, use these commands:\n" +
					"**" + start + "** to start \n" +
					"**" + hit + "** to hit\n" +
					"**" + stand + "** to stand\n" +
					"\n" +
					"While playing, you will use a board such as this:\n"+
					getValueBoard() + "\n" +
					"It will indicate if one of you is standing." 
					);
		}
		//get rules
		else if(message.startsWith(rules)) {
			event.getChannel().sendMessage(
					"This is a modified version of Blackjack. \n" +
					"If you need help playing in discord, enter the command **" + help + "**\n" +
					"\n" +
					"If you have played Blackjack, this version does not have a dealer. Some rules have also been simplified. \n" +
					"Each player is dealt 2 cards face up. \n" +
					"The goal is to have a hand as close to 21 as possible. \n" +
					"On your turn, you have the option to hit or stand. Hit means you get a new card, stand means you don't. Once you stand, you can draw no more cards. \n" +
					"If you are originally dealt an ace and 10/face card, you have a natural and automatically win. \n" +
					"An ace dealt at the start is counted as an 11 (unless you go over 21) and aces dealt throughout the game are counted as 1.\n" +
					"If you or the bot goes over 21, it is called going bust and that player loses."
					);
		}
	}
		
	//makes a new deck
	//makes new deck
	public void newDeck() {
		deck = new ArrayList<Card>();
		for(int i = 0; i < 52; i++) {
			deck.add(new Card(i%13 +1, i/13 +1));
		}
	}
	
	//shuffles given list
	
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
	
	//deals necessary cards
	public void deal() {
		playerHand.add(pull());
		botHand.add(pull());
		playerHand.add(pull());
		botHand.add(pull());
	}
	
	//takes top card of the deck
	
	//takes top card of deck
	public Card pull() {
		return deck.remove(0);
	}
	
	//returns a string version of the hand
	
	//returns string version of hand
	public String handToString(ArrayList<Card> hand) {
		String fin = "\t| ";
		
		for(Card c : hand) {
			fin += "   " + c + "\t| ";
		}
		
		return fin;
	}
	
	//returns string version of board without values
	
	//returns a string version of the board, cards only
	public String getBoard() {
		return	"Bot: " + handToString(botHand) + "\n" +
				"You: " + handToString(playerHand) + "\n"; 
	}
	
	//returns a string version of the board, calculated values built in
	
	//returns string version of board with values
	public String getValueBoard() {
		if(botStanding && playerStanding) 
			return	"Bot: " + handToString(botHand) + "\n" +
					"Bot value: " + handValue(botHand) + "\n" +
					"Bot is standing. \n" +
					"You: " + handToString(playerHand) + "\n" +
					"Your value: " + handValue(playerHand) + "\n" +
					"You are standing. ";
		else if(botStanding)
			return	"Bot: " + handToString(botHand) + "\n" +
					"Bot value: " + handValue(botHand) + "\n" +
					"Bot is standing. \n" +
					"You: " + handToString(playerHand) + "\n" +
					"Your value: " + handValue(playerHand) + "\n";
		else if(playerStanding)
			return	"Bot: " + handToString(botHand) + "\n" +
					"Bot value: " + handValue(botHand) + "\n" +
					"You: " + handToString(playerHand) + "\n" +
					"Your value: " + handValue(playerHand) + "\n" +
					"You are standing.";
		
		return	"Bot: " + handToString(botHand) + "\n" +
				"Bot value: " + handValue(botHand) + "\n" +
				"You: " + handToString(playerHand) + "\n" +
				"Your value: " + handValue(playerHand) + "\n";
	}
	
	//returns the value of the hand

	//returns hand value
	public int handValue(ArrayList<Card> hand) {
		int total = 0;
		
		for(Card c : hand)
			total += c.value;
		
		return total;
	}
	
	//returns if a hand has an ace
	
	public boolean hasAce (ArrayList<Card> hand) {
		for(Card c : hand)
			if(c.displayValue == 1) return true;
		
		return false;
	}
	
	//returns the location of the first ace in the hand
	
	public int aceLoc(ArrayList<Card> hand) {
		for(int i = 0; i < hand.size(); i++)
			if(hand.get(i).displayValue == 1) return i;
		
		return -1;
	}
	
	
	//hit code
	public String hit() {
		String fin = "";
		
		playerHand.add(pull());
		botTurn();
		
		checkGameOver();
		fin += 	getValueBoard() + "\n" +
				checkGameOver();
		
		return fin;
	}
	
	//stand code
	public String stand() {
		String fin = "";
		
		playerStanding = true;
		botTurn();
		
		if(botStanding || checkBust(botHand)) {
			fin += checkGameOver();
		}
		else {
			fin += 	getValueBoard() + "\n" +
					"Bot now playing to completion...\n";
			
			while(!botStanding && !checkBust(botHand)) {
				botTurn();
			}
			
			fin += 	checkGameOver() + "\n";
		}
		return fin;
	}
	
	//bot code
	public void botTurn() {
		
		if(botStanding) return;
		
		if(handValue(botHand) >= 17) {
			botStanding = true;
		}
		
		else botHand.add(pull());
		
	}
	
	//returns outcome of current turn (who won/lost and how)
	public String checkGameOver() {
		String fin = "";
		
		if(checkBust(playerHand) && checkBust(botHand)) fin += "You both went bust. Tie. To play again, enter **" + start + "**";
		else if(checkBust(playerHand)) fin += "You went bust. Bot wins. To play again, enter **" + start + "**";
		else if(checkBust(botHand)) fin += "Bot went bust. You win! To play again, enter **" + start + "**";
		
		else if(handValue(playerHand) == 21 && handValue(botHand) == 21) fin += "You both got 21. Tie To play again, enter **" + start + "**";
		else if(handValue(playerHand) == 21) fin += "You got 21. You win! To play again, enter **" + start + "**";
		else if(handValue(botHand) == 21)fin += "Bot got 21. You lose. To play again, enter **" + start + "**";
		
		else if(playerStanding && botStanding) {
			fin += "You both are standing. ";
			if(handValue(playerHand) > handValue(botHand)) fin += "You were closer to 21. You win! To play again, enter **" + start + "**";
			else if(handValue(playerHand) < handValue(botHand)) fin += "Bot was closer to 21. You lose! To play again, enter **" + start + "**";
			else if(handValue(playerHand) == handValue(botHand)) fin += "You both had the same total. Tie. To play again, enter **" + start + "**";
		}
		
		if(!fin.equals("")) playingBlackjack = false;
		
		return fin;
	}
	
	//check if given hand has gone bust
	public boolean checkBust(ArrayList<Card> hand) {
		//add code
		if(handValue(hand) > 21 && !hasAce(hand)) return true;
		else if(handValue(hand) > 21 && hasAce(hand)) {
			hand.get(aceLoc(hand)).value = 1;
			if(handValue(hand) > 21) return true;
			return false;
		}
		
		return false;
	}
}