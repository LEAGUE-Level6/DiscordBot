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
	boolean checkPlayerNumber = false;
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
				deck = shuffleDeck(deck);
				break;
				
			//dealing cards to player and bot
			case 2:
				dealCards(event);
				break;
			}
		}
		
		
	}
	
	public String pickFirstPlayer(MessageCreateEvent event) {
		String cmd = event.getMessageContent();

		String firstPlayer = null;
		
		if(checkPlayerNumber) {
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
			state++;
			
		}
		
		if(cmd.equals(COMMAND)) {
			event.getChannel().sendMessage(randomNum+"Guess my number between 0 and 10 and I'll let you go first");
			checkPlayerNumber= true;
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
	
	public ArrayList<String> shuffleDeck(ArrayList<String> cards) {
		createDeck();
		ArrayList<String> shuffledCards = new ArrayList<String>();
		for(int i = 0; i < deck.size(); i ++) {
			int randomIndex = new Random().nextInt(deck.size());
			shuffledCards.add(deck.get(randomIndex));
		}

		state++;
		return shuffledCards;
	}
	
	public void dealCards(MessageCreateEvent event) {
		//show the player's cards in list format
		//store in ArrayList
		//save bot's cards in ArrayList
		//save rest of deck into ArrayList for drawing pile
		for(int i = 0; i < 7; i ++) {
			playerCards.add(deck.get(i));
			deck.remove(i);
		}
		for(int i = 0; i < 7; i ++) {
			botCards.add(deck.get(i));
			deck.remove(i);
		}
		for(String s : playerCards) {
			System.out.println(s);
		}
		
		
	}

}
