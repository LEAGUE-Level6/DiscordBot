package org.jointheleague.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class CrazyEights extends CustomMessageCreateListener {
	
	public CrazyEights(String channelName) {
		super(channelName);
	}

	//private Random rand = new Random();
	public static final String genHelp = "!help";
	
	private ArrayList<Card> deck;
	private ArrayList<Card> playerHand;
	private ArrayList<Card> botHand;
	private ArrayList<Card> pile;
	
	public static final String start = "ce!start";
	private final String play = "!play";
	private final String draw = "!draw";
	private final String getBoard = "!getboard";
	private final String end = "!end";
	private final String again = "!again";
	private final String help = "ce!help";
	private final String rules = "ce!rules";
	private final String give = ";give";
	
	private Message botMessage;
	
	public boolean playingCrazyEightsEmbed = false;
	private boolean justEnded = false;
	
	private EmbedBuilder board = new EmbedBuilder();
	
	ArrayList<Card> examples = new ArrayList<Card>();

	@Override
	public void handle(MessageCreateEvent event) throws APIException  {

		String message = event.getMessageContent();
		clearBoard();
		
		if(justEnded) {
			//allows quick play again
			if(message.equalsIgnoreCase(again)) {
				deal();
				
				event.getChannel().deleteMessages(botMessage);
				try{
					botMessage = event.getChannel().sendMessage(board).get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			else if (!event.getMessage().equals(botMessage)) {
				justEnded = false;
			}
		}
		
		//start or restart the game
		if(message.equalsIgnoreCase(start)) {
			
			EmbedBuilder shuffling = new EmbedBuilder();
			shuffling.setTitle("Shuffling decks...");
			shuffling.setDescription("Use **" + help + "** for command help or **" + rules + "** for rules help.");
			shuffling.setColor(new Color(44, 118, 225));
			event.getChannel().sendMessage(shuffling);
			
			deal();
			
			try{
				botMessage = event.getChannel().sendMessage(board).get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			} 
		}
		//get help
		else if(message.equalsIgnoreCase(help)) {
			EmbedBuilder help = new EmbedBuilder();
			
			help.setColor(new Color(44, 118, 225));
			help.setTitle("Command Help");
			help.setDescription(
					"This is a game of Crazy Eights.\n" +
					"If you need to know the rules, enter the command **" + rules + "**\n");
			help.addField("To play the game, use these commands:", 
							"**" + start + "** - start the game\n" +
							"**" + play + " # suit** - play the card # suit from your hand (*2 Diamonds or J Spades*)\n" +
							"**" + draw + "** - draw a card\n" +
							"**" + getBoard + "** - get the current board if it got lost/deleted\n"+
							"**" + end + "** - end the current game\n" +
							"**" + again + "** - used only directly after the last game ends, it allows you to play a new game in quick succession");
			help.addField("Additional notes: ", " - When playing an eight, declare the new suit after its actual suit with a space (*e.g. 8 hearts clubs*)." +
					" When played, it will only display the suit.\n" +
					" - When playing a normal card, you can use either a single letter abreviation (A, Q, ect.) or spell out the whole card (ace, queen, ect.)");
			
			event.getChannel().sendMessage(help);
		}
		//get rules
		else if(message.equalsIgnoreCase(rules)) {
			EmbedBuilder rules = new EmbedBuilder();
			
			rules.setColor(new Color(44, 118, 225));
			rules.setTitle("Rules");
			rules.setDescription(
					"This is a game of Crazy Eights.\n" +
					"If you need to know how to use the bot to play the game, enter the command **" + help + "**\n");
			rules.addField("How to play:",
					"Each player will be dealt a hand of 5 cards. The goal of the game is to play all of your cards. "
					+ "In the middle of the game board is the pile. You can play any card that matches suit or value of the top card of the pile.");
			rules.addField("Eights:", 
					"8 cards are wild.\n" + 
					"When played, the current player can declare any suit. The next player must play a card that matches only that suit.\n");
			
			event.getChannel().sendMessage(rules);
		}

		
		
		if(playingCrazyEightsEmbed) {
			
			//play a card
			if(message.toLowerCase().startsWith(play)) {
				String remaining = message.substring(play.length()+1);
				
				play(remaining);
				
				event.getChannel().deleteMessages(botMessage);
				try {
					botMessage = event.getChannel().sendMessage(board).get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				} 
			}
			//draw card
			else if(message.equalsIgnoreCase(draw)) {
				draw();
				
				event.getChannel().deleteMessages(botMessage);
				try{
					botMessage = event.getChannel().sendMessage(board).get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				} 
			}
			//end the game
			else if(message.equalsIgnoreCase(end)) {
				playingCrazyEightsEmbed = false;
				board.setTitle("GAME TERMINATED");
				
				event.getChannel().deleteMessages(botMessage);
				try{
					botMessage = event.getChannel().sendMessage(board).get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				} 
			}
			//get the board again
			else if(message.equalsIgnoreCase(getBoard)) {
				setBoard();
				event.getChannel().sendMessage(board);
			}
			//give a card (test code)
			else if(message.startsWith(give)) {
				String[] card = message.split(" ");
				playerHand.add((new Card(Integer.parseInt(card[1]), Integer.parseInt(card[2]))));
				setBoard();
				event.getChannel().sendMessage(board);
			}
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
	
	public void play(String message) {
		
		String[] rawCard = message.split(" ");
		Card card;
		
		String valueString = rawCard[0];
		String suitString = rawCard[1];
		
		int value = 0;
		int suit = 0;
		
		if(valueString.matches("[0-9]+$")) {
			int v = Integer.parseInt(valueString);
			
			if(v >= 2 && v <= 10)
				value = v;
		}
		else {
			switch(valueString.toUpperCase()) {
			case "A":
				value = 1;
				break;
			case "ACE":
				value = 1;
				break;
			case "J":
				value = 11;
				break;
			case "JACK":
				value = 11;
				break;
			case "Q":
				value = 12;
				break;
			case "QUEEN":
				value = 12;
				break;
			case "K":
				value = 13;
				break;
			case "KING":
				value = 13;
				break;
			default:
				board.setTitle("Sorry, invalid value. Try again.");
				setBoard();
				return;
			}
		}
		
		switch(suitString.toLowerCase()) {
		case "spades":
			suit = 1;
			break;
		case "clubs":
			suit = 2;
			break;
		case "hearts":
			suit = 3;
			break;
		case "diamonds":
			suit = 4;
			break;
		default:
			board.setTitle("Sorry, invalid suit. Try again.");
			setBoard();
			return;
		}
		
		if(rawCard.length == 2) {
			if(value == 8) {
				board.setTitle("You forgot to declare a suit. Try again.");
				setBoard();
				return;
			}
			card = new Card(value, suit);
			if(playCard(card)) {
				//check winner
				if(hasWon(playerHand)) {
					board.setTitle("You won!");
					board.setDescription("If you want to play again, enter **" + again + "**");
				}
				else {
					board.setTitle("You played: " + pile.get(pile.size()-1));
					
					if(checkReshuffle())
						board.addField("_ _", "Pile reshuffled.");
					
					board.setDescription("Bot " + botTurn());
					
					if(hasWon(botHand)) {
						clearBoard();
						board.setTitle("Bot won.");
						board.setDescription("If you want to play again, enter **" + again + "**");
					}
					else {
						if(checkReshuffle())
							board.addField("_ _", "Pile reshuffled.");
						
						setBoard();
						
						board.addField("_ _", "Your turn.");
					}
				}
			}
			//error if that card is not playable
			else{
				board.setTitle("Sorry, invalid card. Try again.");
				setBoard();
			}
		}
		else if(rawCard.length == 3) {
			if(value != 8) {
				board.setTitle("Sorry, you can only declare suits on eights. Try again.");
				setBoard();
				return;
			}
			
			String dSuitString = rawCard[2];
			int dSuit = 0;
			
			switch(dSuitString.toLowerCase()) {
			case "spades":
				dSuit = 1;
				break;
			case "clubs":
				dSuit = 2;
				break;
			case "hearts":
				dSuit = 3;
				break;
			case "diamonds":
				dSuit = 4;
				break;
			default:
				board.setTitle("Sorry, invalid suit declaration. Try again.");
				setBoard();
				return;
			}
			
			card = new Card(value, suit, dSuit); 
			
			if(playCard(card)) {
				//check winner
				if(hasWon(playerHand)) {
					board.setTitle("You won!");
					board.setDescription("If you want to play again, enter **" + again + "**");
				}
				else {
					board.setTitle("You played: " + pile.get(pile.size()-1));
					
					if(checkReshuffle())
						board.addField("_ _", "Pile reshuffled.");
					
					board.setDescription("Bot " + botTurn());

					if(hasWon(botHand)) {
						clearBoard();
						board.setTitle("Bot won.");
						board.setDescription("If you want to play again, enter **" + again + "**");
					}
					else {
						if(checkReshuffle())
							board.addField("_ _", "Pile reshuffled.");
						
						setBoard();
						
						board.addField("_ _", "Your turn.");
					}
				}
			}
			else {
				board.setTitle("Sorry, invalid card. Try again.");
			}
		}
		else {
			board.setTitle("Sorry, invalid command. Try again.");
			setBoard();
		}
		
	}
	
	//all draw logic
	public void draw() {
		
		playerHand.add(pull());

		board.setTitle("You drew " + playerHand.get(playerHand.size()-1));
		
		if(checkReshuffle())
			board.addField("_ _", "Pile reshuffled.");
		
		board.setDescription("Bot " + botTurn());
		//board.addField("_ _", );
		
		if(hasWon(botHand)) {
			clearBoard();
			board.setTitle("Bot won.");
			board.setDescription("If you want to play again, enter **" + again + "**");
		}
		else {
			if(checkReshuffle())
				board.addField("_ _", "Pile reshuffled.");
			
			setBoard();
			
			board.addField("_ _", "Your turn.");
		}
	}
	
	//deals necessary cards
	public void deal() {
		playingCrazyEightsEmbed = true;
			
		newDeck();
		deck = shuffle(deck);
		
		playerHand = new ArrayList<Card>();
		botHand = new ArrayList<Card>();
		pile = new ArrayList<Card>();
		
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
		while(pile.get(0).displayValue == 8) 
			pile.add(pull());
			
		board = new EmbedBuilder();
		board.setColor(Color.RED);
		
		setBoard();
		board.addField("_ _", "Your turn.");
		
	}
	
	//takes top card of deck
	public Card pull() {
		checkReshuffle();
		return deck.remove(0);
		
	}
	
	//returns string version of hand
	public String handToString(ArrayList<Card> hand) {
		String fin = "|";
		
		for(Card c : hand) {
			fin += "   " + c + "   |";
		}
		
		return fin;
	}
	
	public void setBoard() {
		
		board.addField("Bot:", botHand.size() + " cards");
		board.addField("Top of pile:", pile.get(pile.size()-1)+"");
		board.addField("You:", handToString(playerHand));
	}
	
	public void clearBoard() {
		board.removeAllFields();
		board.setTitle("");
		board.setDescription("");
	}
	
	//check if pile needs reshuffling
	public boolean checkReshuffle() {
		if(deck.size() <= 1) {
			Card topCard = pile.get(pile.size()-1);
			deck = shuffle(pile);
			pile = new ArrayList<Card>();
			pile.add(topCard);
			
			return true;
		}
		
		return false;
	}
	
	//check if given hand has won
	public boolean hasWon(ArrayList<Card> hand) {
		if(hand.size() == 0) {
			playingCrazyEightsEmbed = false;
			justEnded = true;
			return true;
		}
		return false;
	}
	
	public boolean playCard(Card card) {
		
		boolean inHand = false;
		int index = 0;
		Card topCard = pile.get(pile.size()-1);
		
		for(int i = 0; i < playerHand.size(); i++) {
			if(playerHand.get(i).displayValue == card.displayValue && playerHand.get(i).suit == card.suit) {
				index = i;
				inHand = true;
				playerHand.get(i).declaredSuit = card.declaredSuit;
				break;
			}
		}
		if(!inHand) {
			//System.out.println("not in hand");
			return false;
		}
			
		if(topCard.displayValue == 8 && topCard.declaredSuit != 0) {
			if(card.suit == topCard.declaredSuit) {
				pile.add(playerHand.remove(index));
				return true;
			}
			else if(card.displayValue == 8 && card.declaredSuit != 0) {
				pile.add(playerHand.remove(index));
				return true;
			}
		}
		else if(card.displayValue == 8 && card.declaredSuit != 0) {
			pile.add(playerHand.remove(index));
			return true;
		}
		else {
			if(card.suit == topCard.suit) {
				pile.add(playerHand.remove(index));
				return true;
			}
			else if(card.displayValue == topCard.displayValue) {
				pile.add(playerHand.remove(index));
				return true;
			}
		}
		
		return false;
	}
	
	//logic for bot to play, returns what it did
	public String botTurn() {
		Card topCard = pile.get(pile.size()-1);
		
		//tries to play normal card
		for(int i = 0; i < botHand.size(); i++) {
			if(topCard.displayValue == 8 && topCard.declaredSuit != 0) {
				if(botHand.get(i).suit == topCard.declaredSuit) {
					pile.add(botHand.remove(i));
					return "played";
				}
			}
			else {
				if(botHand.get(i).suit == topCard.suit && botHand.get(i).displayValue != 8) {
					pile.add(botHand.remove(i));
					return "played";
				}
				else if(botHand.get(i).displayValue == topCard.displayValue && botHand.get(i).displayValue != 8) {
					pile.add(botHand.remove(i));
					return "played";
				}
			}
		}
		//tries to play eight
		for(int i = 0; i < botHand.size(); i++) {
			if(botHand.get(i).displayValue == 8) {
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


}
