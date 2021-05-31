package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

//TODO
/*
 * search playe/bot decks for just numbers
 * change current setup of deck arraylists (suit, deck)
 * 
 * 
 * 
 */


public class GoFish extends CustomMessageCreateListener{
	//game command
	private static final String COMMAND = "!gofish";
	
	//bot id
	static long botId = 825895501148585984L;
	
	//picking the player
	String playerGuess ="";
	Random r = new Random();
	int randomNum = r.nextInt(11);
	String firstPlayer = null;
	
	//state of the game
	int state = 0;
	
	
	//decks
	ArrayList<Integer> playerCards = new ArrayList<Integer>();
	ArrayList<Integer> botCards = new ArrayList<Integer>();
	ArrayList<Integer> pile = new ArrayList<Integer>();
	ArrayList<Integer> deck = new ArrayList<Integer>();
	ArrayList<Integer> cardsFound = new ArrayList<Integer>();
	
	public GoFish(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Go Fish description goes here");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageAuthor().getId() != botId) {
			System.out.println("State: "+state);
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
			
			//playing round user
			case 3:
				userAsksBot(event);
				break;
			
			//playing round bot
			case 4:
				botAsksUser(event);
				break;
			}
				
		}
	}
	
	public void pickFirstPlayer(MessageCreateEvent event) {
		String cmd = event.getMessageContent();

		
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
		
	}
	
	
	public void createDeck() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j ++) {
				deck.add(j);
			}
		}
	}
	
	public ArrayList<Integer> shuffleDeck(ArrayList<Integer> cards, MessageCreateEvent event) {
		createDeck();
		ArrayList<Integer> shuffledCards = new ArrayList<Integer>();
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
			System.out.println(deck.get(i));

			deck.remove(i);
		}
		System.out.println("cards dealt");
		
		state = 3;
	}
	
	
	public void userAsksBot(MessageCreateEvent event) {
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
		
		
		//player asks for card and cards are removed if found in bot deck and added to player
		
		if(firstPlayer.equals("player")) {
			String request = event.getMessageContent();
			//search for number in request message
			for(int i = 0; i < request.length(); i ++){
				if(Character.isDigit(request.charAt(i))) {
					int requestedCard = Integer.parseInt(request.substring(i, i +1));
					if(searchDeck(botCards, requestedCard, playerCards, event)) {
						event.getChannel().sendMessage("Here you go");
					}
				}
			}
		}
		else {
			//move to bot asks user
			state = 4;
		}
		
		
		
	}
	
	public void botAsksUser(MessageCreateEvent event) {
		//bot asks for random card
		int randCard = new Random().nextInt(13);
		event.getChannel().sendMessage("Do you have any " + randCard + "s?");
		
		//search player deck
		if(randCard >= 2 && randCard <= 10) {
			//getting weird error when this is thrown into if statement
			searchDeck(playerCards, randCard, botCards, event);
		}
		
	}
	
	public boolean searchDeck(ArrayList<Integer> deckToSearch, int cardToSearch, ArrayList<Integer> deckToAdd, MessageCreateEvent event) {
		boolean found = false;
		System.out.println(deckToSearch.size());
		for(int i : deckToSearch) {
			
			if(i == cardToSearch) {
				cardsFound.add(i);
			}
		}
		if(cardsFound.size() > 0) {
			for(int i = 0; i < deckToSearch.size(); i ++) {
				for(int j = 0; j < cardsFound.size(); j ++) {
					if(deckToSearch.get(i).equals(cardsFound.get(j))) {
						deckToAdd.add(deckToSearch.get(i));
						deckToSearch.remove(i);
					}
				}
			}
			found = true;
			state = 4;
		}
		cardsFound.clear();
		return found;
	}
	
	
}
