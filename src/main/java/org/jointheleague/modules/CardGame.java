package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class CardGame extends CustomMessageCreateListener {
	
	private Random rand = new Random();
	
	private ArrayList<Card> deck;
	private ArrayList<Card> playerHand = new ArrayList<Card>();
	private ArrayList<Card> botHand = new ArrayList<Card>();
	private ArrayList<Card> pile = new ArrayList<Card>();
	
	private final String start = "!start";
	private final String play = "!play";
	private final String draw = "!draw";
	//add help
	
	public CardGame(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		String message = event.getMessageContent();
		
		if(message.startsWith(start)) {
			event.getChannel().sendMessage("Shuffling decks...");
			
			newDeck();
			deck = shuffle(deck);
			deal();
			
			event.getChannel().sendMessage(getBoard());
			event.getChannel().sendMessage("Your turn.");
		}
		else if(message.startsWith(play)) {
			String cardNumS = message.substring(play.length()+1);
			int cardNum = Integer.parseInt(cardNumS);
			
			if(playCard(cardNum)) {
				
				if(check() == 0) {
					event.getChannel().sendMessage("Bot " + botTurn());
					
					event.getChannel().sendMessage(getBoard());
					event.getChannel().sendMessage("Your turn.");
				}
				else if(check() == 3) {
					event.getChannel().sendMessage("Pile reshuffled");
					
					event.getChannel().sendMessage("Bot " + botTurn());
					
					event.getChannel().sendMessage(getBoard());
					event.getChannel().sendMessage("Your turn.");
				}
				else {
					//say who won
				}
			}
			else{
				event.getChannel().sendMessage("Sorry, invalid card. Try again.");
				event.getChannel().sendMessage(getBoard());
			}
			
			
		}
		else if(message.startsWith(draw)) {
			//add checks
			playerHand.add(pull());
			
			event.getChannel().sendMessage("Bot " + botTurn());
			
			event.getChannel().sendMessage(getBoard());
			event.getChannel().sendMessage("Your turn.");
		}
		//add help
	}

	public void newDeck() {
		deck = new ArrayList<Card>();
		for(int i = 0; i < 52; i++) {
			deck.add(new Card(i%13 +1, i/13 +1));
		}
	}
	
	public ArrayList<Card> shuffle(ArrayList<Card> cards) {
		ArrayList<Card> shuffled = new ArrayList<Card>();
		int size = cards.size();
		
		for(int i = 0; i < size; i++) {
			int index = rand.nextInt(cards.size());
			shuffled.add(cards.remove(index));
		}
		
		return shuffled;
	}
	
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
	
	public Card pull() {
		return deck.remove(0);
	}
	
	public String handToString(ArrayList<Card> hand) {
		String fin = "";
		
		for(Card c : hand) {
			fin += c + ", ";
		}
		
		return fin.substring(0, fin.length()-2);
	}
	
	public String getBoard() {
		return	"Bot: " + botHand.size() + " cards\n" +
				"Top of pile: " + pile.get(pile.size()-1) + "\n" +
				"You: " + handToString(playerHand) + "\n";
	}
	
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
		return played;
	}
	
	public String botTurn() {
		Card topCard = pile.get(pile.size()-1);
		
		for(int i = 0; i < botHand.size(); i++) {
			if(botHand.get(i).suit == topCard.suit || botHand.get(i).value == topCard.suit) {
				pile.add(botHand.remove(i));
				return "played";
			}
		}
		
		botHand.add(pull());
		return "drew";
		
	}
	
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
}
