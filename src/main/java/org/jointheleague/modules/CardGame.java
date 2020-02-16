package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class CardGame extends CustomMessageCreateListener {
	
	private Random rand = new Random();
	
	private ArrayList<Card> deck;
	private ArrayList<Card> playerHand;
	private ArrayList<Card> botHand;
	private ArrayList<Card> pile;
	
	private final String start = "!start";
	private final String play = "!play";
	private final String draw = "!draw";
	private final String help = "!help";
	private final String rules = "!rules";
	//test codes
	private final String give = ";give";
	private final String giveBot = ";giveBot";
	private final String remove = ";remove";
	private final String reveal = ";reveal";
	private final String changeTop = ";changeTop"; //add code to this
	
	public CardGame(String channelName) {
		super(channelName);
	}

	//finish testing code
	
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
			event.getChannel().sendMessage("Shuffling decks...");
			
			newDeck();
			deck = shuffle(deck);
			
			playerHand = new ArrayList<Card>();
			botHand = new ArrayList<Card>();
			pile = new ArrayList<Card>();
			
			deal();
			
			event.getChannel().sendMessage(getBoard());
			event.getChannel().sendMessage("Your turn.");
		}
		//play a card
		else if(message.startsWith(play)) {
			String remaining = message.substring(play.length()+1);
			
			//play a normal card (not eight)
			if(remaining.matches("[0-9]+$")){
				int cardNum = Integer.parseInt(remaining);
				
				//checks card is playable
				if(playCard(cardNum)) {
					
					//checks winners or pile reshuffling
					int check = check();
					if(check == 0) {
						event.getChannel().sendMessage("Bot " + botTurn());
						
						event.getChannel().sendMessage(getBoard());
						event.getChannel().sendMessage("Your turn.");
					}
					else if(check == 3) {
						event.getChannel().sendMessage("Pile reshuffled");
						
						event.getChannel().sendMessage("Bot " + botTurn());
						
						event.getChannel().sendMessage(getBoard());
						event.getChannel().sendMessage("Your turn.");
					}
					else {
						if(check == 1)
							event.getChannel().sendMessage("You won!");
						else if(check == 2)
							event.getChannel().sendMessage("Bot won.");
						
						event.getChannel().sendMessage("If you want to play again, enter " + start);
					}
				}
				//error if that card is not playable
				else{
					event.getChannel().sendMessage("Sorry, invalid card. Try again.");
					event.getChannel().sendMessage(getBoard());
				}
			}
			//play an eight
			else {
				String[] wild = remaining.split(" ");
				
				//error on command
				if(wild.length != 2) {
					event.getChannel().sendMessage("Sorry, invalid command. Try again.");
					event.getChannel().sendMessage(getBoard());
				}
				else {
					//checks they entered a number
					if(wild[0].matches("[0-9]+$")) {
						//checks card is eight
						if(playerHand.get(Integer.parseInt(wild[0])-1).value == 8) {
							int cardNum = Integer.parseInt(wild[0]);
							
							int suit = 0;
							if(wild[1].equalsIgnoreCase("Spades")) suit = 1;
							else if(wild[1].equalsIgnoreCase("Clubs")) suit = 2;
							else if(wild[1].equalsIgnoreCase("Hearts")) suit = 3;
							else if(wild[1].equalsIgnoreCase("Diamonds")) suit = 4;
							
							//checks suit is valid 
							if(suit == 0) {
								event.getChannel().sendMessage("Sorry, invalid suit. Try again.");
								event.getChannel().sendMessage(getBoard());
							}
							
							//plays card
							playerHand.get(cardNum-1).declaredSuit = suit;
							pile.add(playerHand.remove(cardNum-1));

							event.getChannel().sendMessage("Bot " + botTurn());
							
							event.getChannel().sendMessage(getBoard());
							event.getChannel().sendMessage("Your turn.");
						}
						//error on command
						else {
							event.getChannel().sendMessage("Sorry, that's not an eight. Try again.");
							event.getChannel().sendMessage(getBoard());
						}
					}
					//error on command
					else {
						event.getChannel().sendMessage("Sorry, invalid card. Try again.");
						event.getChannel().sendMessage(getBoard());
					}
				}
			}
		}
		//draw card
		else if(message.startsWith(draw)) {
			//add checks
			playerHand.add(pull());
			
			event.getChannel().sendMessage("Bot " + botTurn());
			
			event.getChannel().sendMessage(getBoard());
			event.getChannel().sendMessage("Your turn.");
		}
		//get help
		else if(message.startsWith(help)) {
			
			event.getChannel().sendMessage(
					"This is a game of Crazy Eights.\n" +
					"If you need to know the rules, enter the command **" + rules + "**");
			event.getChannel().sendMessage("_ _");
			event.getChannel().sendMessage(
					"To play the game, use these commands:\n" +
					"**" + start + "** to start the game\n" +
					"**" + play + " #** to play the card at position # from your hand\n" +
					"**" + draw + "** to draw a card");
			event.getChannel().sendMessage("Note: When playing an eight, declare the suit after the # with a space (spades, hearts, ect.)." +
					 " This will show up after a third slash.");
			event.getChannel().sendMessage("_ _");
			event.getChannel().sendMessage("While playing the game, you will use a game board such as this:\n" +
					"Bot: 3 cards\n" +
					"Top of pile: " + new Card(4,1) + "\n" +
					"You: " + handToString(examples) + "\n");
			
		}
		//get rules
		else if(message.startsWith(rules)) {
			//add rules
		}
		//test codes: give card to bot
		else if(message.startsWith(giveBot)) {
			message = message.substring(giveBot.length()+1);
			String[] card = message.split("/");
			int value = Integer.parseInt(card[0]);
			int suit = Integer.parseInt(card[1]);
			botHand.add(new Card(value, suit));
			
			event.getChannel().sendMessage(handToString(botHand));
		}
		//test codes: give card
		else if(message.startsWith(give)) {
			message = message.substring(give.length()+1);
			String[] card = message.split("/");
			int value = Integer.parseInt(card[0]);
			int suit = Integer.parseInt(card[1]);
			playerHand.add(new Card(value, suit));
			
			event.getChannel().sendMessage(getBoard());
		}
		//test codes: remove card from bot hand
		else if(message.startsWith(remove)) {
			message = message.substring(remove.length()+1);
			botHand.remove(Integer.parseInt(message));
			event.getChannel().sendMessage(handToString(botHand));
		}
		//test codes: reveal bot hand
		else if(message.startsWith(reveal)) {
			event.getChannel().sendMessage(handToString(botHand));
			event.getChannel().sendMessage(getBoard());
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
		playerHand.add(pull());
		botHand.add(pull());
		playerHand.add(pull());
		botHand.add(pull());
		playerHand.add(pull());
		botHand.add(pull());
		
		pile.add(pull());
	}
	
	//takes top card of deck
	
	public Card pull() {
		return deck.remove(0);
	}
	
	//returns string version of hand
	public String handToString(ArrayList<Card> hand) {
		String fin = "";
		
		for(Card c : hand) {
			fin += c + ", ";
		}
		
		return fin.substring(0, fin.length()-2);
	}
	
	//returns string version of board
	
	public String getBoard() {
		return	"Bot: " + botHand.size() + " cards\n" +
				"Top of pile: " + pile.get(pile.size()-1) + "\n" +
				"You: " + handToString(playerHand) + "\n";
	}
	
	//checks for winners or reshuffling
	public int check() {
		if(playerHand.size() == 0) {
			return 1;
		}
		if(botHand.size() == 0) {
			return 2;
		}
		if(deck.size() == 0) {
			deck = shuffle(pile);
			return 3;
		}
		
		return 0;
	}
	
	//player plays card, returns true if successfully played
	
	public boolean playCard(int cardNum) {
		boolean played = false;
		Card topCard = pile.get(pile.size()-1);
		
		if(playerHand.get(cardNum-1).suit == topCard.suit) {
			pile.add(playerHand.remove(cardNum-1));
			played = true;
		}
		else if(playerHand.get(cardNum-1).value == topCard.value) {
			pile.add(playerHand.remove(cardNum-1));
			played = true;
		}
		else if(playerHand.get(cardNum-1).suit == topCard.declaredSuit) {
			pile.add(playerHand.remove(cardNum-1));
			played = true;
		}
		return played;
	}
	
	//logic for bot to play, returns what it did
	public String botTurn() {
		Card topCard = pile.get(pile.size()-1);
		
		//tries to play normal card
		for(int i = 0; i < botHand.size(); i++) {
			if(botHand.get(i).suit == topCard.suit || botHand.get(i).suit == topCard.declaredSuit) {
				pile.add(botHand.remove(i));
				return "played";
			}
			else if(botHand.get(i).value == topCard.value && botHand.get(i).value != 8) {
				pile.add(botHand.remove(i));
				return "played";
			}
		}
		//tries to play eight
		for(int i = 0; i < botHand.size(); i++) {
			if(botHand.get(i).value == 8) {
				int[] suitCount = new int[5];
				int mostSuit = 0;
				for(Card x : botHand) 
					suitCount[x.suit] ++;
				
				for(int j = 1; j < 5; j++)
					if(suitCount[j] > suitCount[j-1])
						mostSuit = j;
				
				botHand.get(i).declaredSuit = mostSuit;
				pile.add(botHand.remove(i));
				return "played 8";
			}
		}
		
		//draws
		botHand.add(pull());
		return "drew";
		
	}
}
