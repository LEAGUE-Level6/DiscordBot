package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class CrazyEights extends CustomMessageCreateListener {
	
	//private Random rand = new Random();
	
	private ArrayList<Card> deck;
	private ArrayList<Card> playerHand;
	private ArrayList<Card> botHand;
	private ArrayList<Card> pile;
	
	private final String start = "c8!start";
	private final String play = "!play";
	private final String draw = "!draw";
	private final String help = "c8!help";
	private final String rules = "c8!rules";
	//test codes
	private final String testers = ";cheats";
	private final String give = ";give";
	private final String giveBot = ";giveBot";
	private final String remove = ";remove";
	private final String reveal = ";reveal";
	private final String changeTop = ";changeTop"; 
	private final String swap = ";swap";
	private final String show = ";show";
	private final String clear = ";clear";
	
	public boolean playingCrazyEights = false;
	
	public CrazyEights(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		String message = event.getMessageContent();
		
		ArrayList<Card> examples = new ArrayList<Card>();
		examples.add(new Card(11, 3));
		examples.add(new Card(3, 1));
		examples.add(new Card(4, 2));
		examples.add(new Card(1, 4));
		examples.add(new Card(8, 4));
		
		//start or restart the game
		if(message.startsWith(start)) {
			playingCrazyEights = true;
			event.getChannel().sendMessage("Shuffling decks...");
			
			newDeck();
			deck = shuffle(deck);
			
			playerHand = new ArrayList<Card>();
			botHand = new ArrayList<Card>();
			pile = new ArrayList<Card>();
			
			deal();
			
			event.getChannel().sendMessage("_ _\n" + getBoard() + "\nYour turn.");
		}
		//play a card
		else if(message.startsWith(play) && playingCrazyEights) {
			String remaining = message.substring(play.length()+1);
			
			event.getChannel().sendMessage(play(remaining));
		}
		//draw card
		else if(message.startsWith(draw) && playingCrazyEights) {
			event.getChannel().sendMessage(draw());
		}
		//get help
		else if(message.startsWith(help)) {
			event.getChannel().sendMessage(
					"This is a game of Crazy Eights.\n" +
					"If you need to know the rules, enter the command **" + rules + "**\n" +
					"\n" +
					"To play the game, use these commands:\n" +
					"**" + start + "** to start the game\n" +
					"**" + play + " #** to play the card at position # from your hand\n" +
					"**" + draw + "** to draw a card\n" +
					"Note: When playing an eight, declare the suit after the # with a space (spades, hearts, ect.)." +
					" This will show up after a third slash.\n" +
					"\n" +
					"While playing the game, you will use a game board such as this:\n" +
					"Bot: 3 cards\n" +
					"Top of pile: " + new Card(4, 1) + "\n" +
					"You: " + handToString(examples) + "\n" +
					"\n" +
					"An example play could be **" + play + " 2** or **" + play + " 5 hearts** ending with:\n" +
					"Top of pile: " + new Card(3, 1) + "\n" +
					"or \n" +
					"Top of pile: " + new Card(8, 4, 3) + "\n");
			
		}
		//get rules
		else if(message.startsWith(rules)) {
			event.getChannel().sendMessage(
					"This is a game of Crazy Eights.\n" + 
					"If you need to know how to use the bot to play the game, enter the command **" + help + "**\n" + 
					"\n" + 
					"Each player will be dealt a hand of 5 cards.\n" + 
					"The goal of the game is to play all of your cards.\n" + 
					"In the middle of the game board is the pile.\n" + 
					"You can play any card that matches suit or value of the top card of the pile.\n" +
					"\n" + 
					"8 cards are wild.\n" + 
					"When played, the current player can declare any suit. The next player must play a card that matches only that suit.\n" + 
					"\n" +  
					"The board will look like this:\n" + 
					"Bot: 3 cards\n" +
					"Top of pile: " + new Card(4, 1) + "\n" +
					"You: " + handToString(examples) + "\n" +
					"\n" +
					"In this example, you could play either the 3, 4, or 8.\n" );
		}
		else if(message.startsWith(";") && event.getMessageAuthor().getDisplayName().equals("Certified Crazy Eights Tester")) {
			event.getChannel().sendMessage(cheats(message));
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
	
	//all play logic
	public String play(String message) {
		String fin = "";
		
		//play a normal card (not eight)
		if(message.matches("[0-9]+$")){
			int cardNum = Integer.parseInt(message);
			
			//checks card is playable
			if(playCard(cardNum)) {
				
				//check winner
				if(hasWon(playerHand)) {
					fin += "\n" + "You won!";
					fin += "\n" + "If you want to play again, enter **" + start + "**";
				}
				else {
					fin += "\n" +checkReshuffle();
					
					
					fin += "\n" +"Bot " + botTurn();
					
					if(hasWon(botHand)) {
						fin += "\n" +"Bot won.";
						fin += "\n" +"If you want to play again, enter **" + start + "**";
					}
					else {
						fin += "\n" +checkReshuffle();
						
						fin += "\n" +getBoard();
						fin += "\n" +"Your turn.";
					}
				}
			}
			//error if that card is not playable
			else{
				fin += "\n" +"Sorry, invalid card. Try again.";
				fin += "\n" +getBoard();
			}
		}
		//play an eight
		else {
			String[] wild = message.split(" ");
			
			//error on command
			if(wild.length != 2) {
				fin += "\n" +"Sorry, invalid command. Try again.";
				fin += "\n" +getBoard();
			}
			else {
				//checks they entered a number
				if(wild[0].matches("[0-9]+$")) {
					//checks card is eight
					if(Integer.parseInt(wild[0]) > 0 && Integer.parseInt(wild[0]) <= playerHand.size()) {
						if(playerHand.get(Integer.parseInt(wild[0])-1).value == 8) {
							int cardNum = Integer.parseInt(wild[0]);
							
							int suit = 0;
							if(wild[1].equalsIgnoreCase("Spades")) suit = 1;
							else if(wild[1].equalsIgnoreCase("Clubs")) suit = 2;
							else if(wild[1].equalsIgnoreCase("Hearts")) suit = 3;
							else if(wild[1].equalsIgnoreCase("Diamonds")) suit = 4;
							
							//checks suit is valid 
							if(suit == 0) {
								fin += "\n" +"Sorry, invalid suit. Try again.";
								fin += "\n" +getBoard();
							}
							
							//plays card
							playerHand.get(cardNum-1).declaredSuit = suit;
							pile.add(playerHand.remove(cardNum-1));
							
							fin += "\n" +"You played: " + pile.get(pile.size()-1);
							
							if(hasWon(playerHand)) {
								fin += "\n" +"You won!";
								fin += "\n" +"If you want to play again, enter **" + start + "**";
							}
							else {
								fin += "\n" +checkReshuffle();
	
								fin += "\n" +"Bot " + botTurn();

								if(hasWon(botHand)) {
									fin += "\n" +"Bot won.";
									fin += "\n" +"If you want to play again, enter **" + start + "**";
								}
								else {
									fin += "\n" +checkReshuffle();
									
									fin += "\n" + getBoard();
									fin += "\n" +"Your turn.";
								}
							}
						}
						//error on command
						else {
							fin += "\n" +"Sorry, that's not an eight. Try again.";
							fin += "\n" +getBoard();
						}
					}
					//error on position number
					else {
						fin += "\n" +"Sorry, invalid card. Try again.";
						fin += "\n" +getBoard();
					}
				}
				//error on command
				else {
					fin += "\n" +"Sorry, invalid card. Try again.";
					fin += "\n" +getBoard();
				}
			}
		}
		
		return fin;
	}
	
	//all draw logic
	public String draw() {
		String fin = "";
		
		playerHand.add(pull());

		fin += "\n" + "You drew " + playerHand.get(playerHand.size()-1);
		
		fin += "\n" + checkReshuffle();
		
		fin += "\n" + "Bot " + botTurn();
		
		if(hasWon(botHand)) {
			fin += "\n" + "Bot won.";
			fin += "\n" + "If you want to play again, enter **" + start + "**";
		}
		else {
			fin += "\n" + checkReshuffle();
		
			fin += "\n" + getBoard();
			fin += "\n" + "Your turn.";
		}
		
		return fin;
	}
	
	//deals necessary cards
	public void deal() {
		playerHand.add(pull());
		botHand.add(pull());
		playerHand.add(pull());
		botHand.add(pull());
		playerHand.add(pull());
		botHand.add(pull());
		playerHand.add(pull());
		botHand.add(pull());
		playerHand.add(pull());
		botHand.add(pull());
		
		pile.add(pull());
		while(pile.get(0).value == 8) 
			pile.add(pull());
		
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
	
	//returns string version of board
	public String getBoard() {
		return	"Bot: " + botHand.size() + " cards\n" +
				"Top of pile: " + pile.get(pile.size()-1) + "\n" +
				"You: " + handToString(playerHand) + "\n";
	}
	
	//check if pile needs reshuffling
	public String checkReshuffle() {
		if(deck.size() <= 1) {
			Card topCard = pile.get(pile.size()-1);
			deck = shuffle(pile);
			pile = new ArrayList<Card>();
			pile.add(topCard);
			
			return "Pile reshuffled.";
		}
		
		return "";
	}
	
	//check if given hand has won
	public boolean hasWon(ArrayList<Card> hand) {
		if(hand.size() == 0) {
			playingCrazyEights = false;
			return true;
		}
		return false;
	}
	
	//player plays card, returns true if successfully played
	public boolean playCard(int cardNum) {
		if(cardNum < 1 || cardNum > playerHand.size()) return false;
		
		boolean played = false;
		Card topCard = pile.get(pile.size()-1);
		
		if(topCard.value == 8 && topCard.declaredSuit != 0) {
			if(playerHand.get(cardNum-1).suit == topCard.declaredSuit) {
				pile.add(playerHand.remove(cardNum-1));
				played = true;
			}
		}
		else {
			if(playerHand.get(cardNum-1).suit == topCard.suit) {
				pile.add(playerHand.remove(cardNum-1));
				played = true;
			}
			else if(playerHand.get(cardNum-1).value == topCard.value) {
				pile.add(playerHand.remove(cardNum-1));
				played = true;
			}
		}
		return played;
	}
	
	//logic for bot to play, returns what it did
	public String botTurn() {
		Card topCard = pile.get(pile.size()-1);
		
		//tries to play normal card
		for(int i = 0; i < botHand.size(); i++) {
			if(topCard.value == 8 && topCard.declaredSuit != 0) {
				if(botHand.get(i).suit == topCard.declaredSuit) {
					pile.add(botHand.remove(i));
					return "played";
				}
			}
			else {
				if(botHand.get(i).suit == topCard.suit && botHand.get(i).value != 8) {
					pile.add(botHand.remove(i));
					return "played";
				}
				else if(botHand.get(i).value == topCard.value && botHand.get(i).value != 8) {
					pile.add(botHand.remove(i));
					return "played";
				}
			}
		}
		//tries to play eight
		for(int i = 0; i < botHand.size(); i++) {
			if(botHand.get(i).value == 8) {
				int[] suitCount = new int[5];
				int mostSuit = 0;
				for(Card x : botHand) 
					suitCount[x.suit] ++;
				
				suitCount[botHand.get(i).suit] --;
				
				for(int j = 1; j < 5; j++)
					if(suitCount[j] > suitCount[j-1])
						mostSuit = j;
				
				botHand.get(i).declaredSuit = mostSuit;
				pile.add(botHand.remove(i));
				return "played an 8";
			}
		}
		
		//draws
		botHand.add(pull());
		return "drew";
		
	}
	
	public String cheats(String message) {
		String fin = "";
		
		if(message.startsWith(testers)) {
			fin =
					"Testing commands:\n" +
					"**" + give + "**: give player card\n" +
					"**" + giveBot + "**: give bot card\n" +
					"**" + remove + "**: remove card from bot hand\n" +
					"**" + reveal + "**: show bot hand\n" +
					"**" + changeTop + "**: change top of pile\n" +
					"**" + swap + "**: swap deck and pile\n" +
					"**" + show + "**: show all lists\n" +
					"**" + clear + "**: clear discord";
		}
		//test codes: give card to bot
		else if(message.startsWith(giveBot)) {
			message = message.substring(giveBot.length()+1);
			String[] card = message.split("/");
			int value = Integer.parseInt(card[0]);
			int suit = Integer.parseInt(card[1]);
			botHand.add(new Card(value, suit));
			
			fin = handToString(botHand);
		}
		//test codes: give card
		else if(message.startsWith(give)) {
			message = message.substring(give.length()+1);
			String[] card = message.split("/");
			int value = Integer.parseInt(card[0]);
			int suit = Integer.parseInt(card[1]);
			playerHand.add(new Card(value, suit));
			
			fin = getBoard();
		}
		//test codes: remove card from bot hand
		else if(message.startsWith(remove)) {
			message = message.substring(remove.length()+1);
			botHand.remove(Integer.parseInt(message));
			fin = handToString(botHand);
		}
		//test codes: reveal bot hand
		else if(message.startsWith(reveal)) {
			fin = handToString(botHand);
			fin += "\n" + getBoard();
		}
		//test codes: change top card
		else if(message.startsWith(changeTop)) {
			message = message.substring(changeTop.length()+1);
			String[] card = message.split("/");
			int value = Integer.parseInt(card[0]);
			int suit = Integer.parseInt(card[1]);
			pile.add(new Card(value, suit));
			
			fin = getBoard();
		}
		//test codes: switch pile and deck
		else if(message.startsWith(swap)) {
			ArrayList<Card> swap = pile;
			pile = deck;
			deck = swap;
			
			fin = getBoard();
		}
		//test codes: shows board, pile, and deck
		else if(message.startsWith(show)) {
			fin = "Bot hand: " + handToString(botHand);
			fin += "\n" + getBoard();
			fin += "\n" + "Deck: " + handToString(deck);
			fin += "\n" + "Pile: " + handToString(pile);
		}
		else if(message.startsWith(clear)) {
			fin = "_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n"
					+ "_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n"
					+ "_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n"
					+ "_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n"
					+ "_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n_ _\n_ _";
		}
		
		return fin;
	}
}
