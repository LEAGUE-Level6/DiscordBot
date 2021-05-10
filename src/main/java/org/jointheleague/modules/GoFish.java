package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class GoFish extends CustomMessageCreateListener{
	//game command
	private static final String COMMAND = "!gofish";
	
	//bot id
	static long botId = 825895501148585984L;
	
	//picking the player
	String playerGuess ="";
	Random r = new Random();
	int randomNum = r.nextInt(11);
	
	//state of the game
	int state = 0;
	
	//decks
	ArrayList<String> playerCards = new ArrayList<String>();
	ArrayList<String> botCards = new ArrayList<String>();
	ArrayList<String> pile = new ArrayList<String>();
	ArrayList<String> deck = new ArrayList<String>();
	
	public GoFish(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Go Fish description goes here");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageAuthor().getId() != botId) {
			System.out.println(state);
			switch(state) {
			//starting the game/deciding who goes first
			case 0:
				pickFirstPlayer(event);
				break;
				
			//shuffling the deck
			case 1:
				deck = shuffleDeck(deck, event);
				break;
				
			//dealing cards to player and bot
			case 2:
				dealCards(event);
				break;
			
			//playing round
			case 3:
				playRound(event);
			
			}
		}
	}
	
	public String pickFirstPlayer(MessageCreateEvent event) {
		String cmd = event.getMessageContent();

		String firstPlayer = null;
		if(cmd.equals(COMMAND)) {
			event.getChannel().sendMessage(randomNum+"Guess my number between 0 and 10 and I'll let you go first");
		}
		else if(state == 0) {
			playerGuess = event.getMessageContent();
			//checkPlayerNumber = false;
			if(Integer.parseInt(playerGuess) == randomNum) {
				event.getChannel().sendMessage("You can go first");
				firstPlayer = "player";
			}
			else {
				event.getChannel().sendMessage("I'll go first");
				firstPlayer = "bot";
			}
			
			state = 1;
			
		}
		
		return firstPlayer;
	}
	
	
	public void createDeck() {
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j ++) {
				deck.add(i +" " + j);
			}
		}
	}
	
	public ArrayList<String> shuffleDeck(ArrayList<String> cards, MessageCreateEvent event) {
		createDeck();
		ArrayList<String> shuffledCards = new ArrayList<String>();
		for(int i = 0; i < deck.size(); i ++) {
			int randomIndex = new Random().nextInt(deck.size());
			shuffledCards.add(deck.get(randomIndex));
		}
		
		state = 2;
		event.getChannel().sendMessage("I shuffled the deck");
		return shuffledCards;
	}
	
	public void dealCards(MessageCreateEvent event) {
		//show the player's cards in list format
		//store in ArrayList
		//save bot's cards in ArrayList
		//rest of deck to be used for go fish
		for(int i = 0; i < 7; i ++) {
			playerCards.add(deck.get(i));
			deck.remove(i);
		}
		for(int i = 0; i < 7; i ++) {
			botCards.add(deck.get(i));
			deck.remove(i);
		}
		
		
		state = 3;
	}
	
	
	public void playRound(MessageCreateEvent event) {
		//user asks for card
			//scenario 1:
				//user receives card and is added to player deck, removed from bot deck
			
			//scenario 2:
				//user does not receive card and bot says "Go Fish"
				//user draws one card from deck, adds to player deck
		//bot takes a turn
			//scenario 1:
				//receives cards from player, add to bot deck, remove from player deck
		
			//scenario 2:
				//does not receive card and player says "Go Fish"
		
		char [] request = event.getMessageContent().toCharArray();
		for(char c : request) {
			if(Character.isDigit(c)) {
				int requestedCardNum = Character.getNumericValue(c);
				if(requestedCardNum >= 2 && requestedCardNum <= 10) {
					searchDeck(botCards, Integer.toString(requestedCardNum));
				}
			}
		}
	}
	
	
	public ArrayList<String> searchDeck(ArrayList<String> deckToSearch, String cardToSearch) {
		ArrayList<String> cardsFound = new ArrayList<String>();
		for(String s : deckToSearch) {
			if(s.equals(cardToSearch)) {
				cardsFound.add(s);
			}
		}
		if(cardsFound.size() > 0) {
		System.out.println(cardsFound.get(0));
		}
		return cardsFound;
	}
	
	
}
