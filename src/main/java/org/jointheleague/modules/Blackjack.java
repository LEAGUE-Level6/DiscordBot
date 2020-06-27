package org.jointheleague.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Blackjack extends CustomMessageCreateListener {
	
	public Blackjack(String channelName) {
		super(channelName);
	}

	private ArrayList<Card> deck;
	private ArrayList<Card> playerHand;
	private ArrayList<Card> botHand;
	
	public static final String start = "b!start";
	private final String hit = "!hit";
	private final String stand = "!stand";
	private final String getBoard = "!getboard";
	private final String end = "!end";
	private final String again = "!again";
	private final String help = "b!help";
	private final String rules = "b!rules";
	
	public boolean playingBlackjackEmbed = false;
	private boolean justEnded = false;
	private boolean playerStanding = false;
	private boolean botStanding = false;
	
	private EmbedBuilder board = new EmbedBuilder();
	
	private Message botMessage;

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		String message = event.getMessageContent();
		clearBoard();
		
		if(justEnded) {
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
		else if(message.startsWith(help)) {
			
			EmbedBuilder help = new EmbedBuilder();
			
			help.setColor(new Color(44, 118, 225));
			help.setTitle("Command Help");
			help.setDescription(
					"This is a modified version of Blackjack.\n" +
					"If you need to know the rules, enter the command **" + rules + "**\n");
			help.addField("To play the game, use these commands:", 
							"**" + start + "** - start the game\n" +
							"**" + hit + " ** - hit\n" +
							"**" + stand + "** - stand\n" +
							"**" + getBoard + "** - get the current board if it got lost/deleted\n"+
							"**" + end + "** - end the current game\n" +
							"**" + again + "** - used only directly after the last game ends, it allows you to play a new game in quick succession");
			
			event.getChannel().sendMessage(help);
		}
		//get rules
		else if(message.startsWith(rules)) {
			EmbedBuilder rules = new EmbedBuilder();
			
			rules.setColor(new Color(44, 118, 225));
			rules.setTitle("Rules");
			rules.setDescription(
					"This is modified version of Blackjack.\n" +
					"If you need to know how to use the bot to play the game, enter the command **" + help + "**\n");
			rules.addField("How to play:",
					"Each player is dealt 2 cards face up. \n" +
					"The goal is to have a hand as close to 21 as possible. \n" +
					"On your turn, you have the option to hit or stand. Hit means you get a new card, stand means you don't. Once you stand, you can draw no more cards. \n" +
					"If you are originally dealt an ace and 10/face card, you have a natural and automatically win. \n" +
					"An ace dealt at the start is counted as an 11 (unless you go over 21) and aces dealt throughout the game are counted as 1.\n" +
					"If you or the bot goes over 21, it is called going bust and that player loses.");
			rules.addField("Modifications:", 
					"If you have played blackjack before, this version does not have a dealer. Some rules have also been simplified.");
			
			event.getChannel().sendMessage(rules);
		}
		if(playingBlackjackEmbed) {
			if(message.equalsIgnoreCase(hit)) {
				hit();
				
				event.getChannel().deleteMessages(botMessage);
				try{
					botMessage = event.getChannel().sendMessage(board).get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				} 
			}
			else if(message.equalsIgnoreCase(stand)) {
				stand();
				
				event.getChannel().deleteMessages(botMessage);
				try{
					botMessage = event.getChannel().sendMessage(board).get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				} 
			}
			else if(message.equalsIgnoreCase(end)) {
				playingBlackjackEmbed = false;
				board.setTitle("GAME TERMINATED");
				
				event.getChannel().deleteMessages(botMessage);
				try{
					botMessage = event.getChannel().sendMessage(board).get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				} 
			}
			else if(message.equalsIgnoreCase(getBoard)) {
				setValueBoard();
				event.getChannel().sendMessage(board);
			}
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
		playingBlackjackEmbed = true;
		
		newDeck();
		deck = shuffle(deck);
		playerHand = new ArrayList<Card>();
		botHand = new ArrayList<Card>();
		
		playerStanding = false;
		botStanding = false;
		
		playerHand.add(pull());
		botHand.add(pull());
		playerHand.add(pull());
		botHand.add(pull());
		
		board = new EmbedBuilder();
		board.setColor(Color.RED/*new Color(112, 28, 232)*/);
		
		if(handValue(playerHand) == 11 && hasAce(playerHand) && handValue(botHand) == 11 && hasAce(botHand)) {
			board.setTitle("You tied.");
			board.setDescription("Both you and the bot have naturals. To play again, enter **" + again + "**");
			setBoard();
			
			playingBlackjackEmbed = false;
			justEnded = true;
		}
		else if(handValue(playerHand) == 11 && hasAce(playerHand)) {
			board.setTitle("You won!");
			board.setDescription("You have a natural! You win! To play again, enter **" + again + "**");
			setBoard();
			
			playingBlackjackEmbed = false;
			justEnded = true;
		}
		else if(handValue(botHand) == 11 && hasAce(botHand)) { 
			board.setTitle("You lose.");
			board.setDescription("The bot has a natural. You lose. To play again, enter **" + again + "**");
			setBoard();
			
			playingBlackjackEmbed = false;
			justEnded = true;
		}
		else {
			if(hasAce(playerHand)) {
				playerHand.get(aceLoc(playerHand)).value = 11;
			}
			if(hasAce(botHand)) {
				botHand.get(aceLoc(botHand)).value = 11;
			}
			
			setValueBoard();
		}
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
	
	public void setBoard() {
		board.addField("Bot: ", handToString(botHand));
		board.addField("You:", handToString(playerHand));
	}
	
	public void setValueBoard() {
		if(botStanding && playerStanding) {
			board.addField("Bot:", 
					handToString(botHand) + "\n" +
					"Value: " + handValue(botHand) + "\n" +
					"Bot is standing.");
			board.addField("You:",
					handToString(playerHand) + "\n" +
					"Value: " + handValue(playerHand) + "\n" +
					"You are standing. ");
		}
		else if(botStanding) {
			board.addField("Bot:", 
					handToString(botHand) + "\n" +
					"Value: " + handValue(botHand) + "\n" +
					"Bot is standing.");
			board.addField("You:",
					handToString(playerHand) + "\n" +
					"Value: " + handValue(playerHand));
		}
		else if(playerStanding) {
			board.addField("Bot:", 
					handToString(botHand) + "\n" +
					"Value: " + handValue(botHand));
			board.addField("You:",
					handToString(playerHand) + "\n" +
					"Value: " + handValue(playerHand) + "\n" +
					"You are standing. ");
		}
		else {
			board.addField("Bot:", 
					handToString(botHand) + "\n" +
					"Value: " + handValue(botHand));
			board.addField("You:",
					handToString(playerHand) + "\n" +
					"Value: " + handValue(playerHand));
		}
	}
	
	public void clearBoard() {
		board.removeAllFields();
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
	public void hit() {
		playerHand.add(pull());
		botTurn();
		
		checkGameOver();
		//setValueBoard();
	}
	
	//stand code
	public void stand() {
		
		playerStanding = true;
		botTurn();
		
		if(botStanding || checkBust(botHand)) {
			checkGameOver();
		}
		else {
			//getValueBoard();
			
			while(!botStanding && !checkBust(botHand)) {
				botTurn();
			}
			
			checkGameOver();
		}
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
	public void checkGameOver() {
		
		if(checkBust(playerHand) && checkBust(botHand)) {
			board.setTitle("You tied.");
			board.setDescription("You both went bust. To play again, enter **" + again + "**");
			playingBlackjackEmbed = false;
			justEnded = true;
		}
		else if(checkBust(playerHand)) {
			board.setTitle("You lose.");
			board.setDescription("You went bust. To play again, enter **" + again + "**");
			playingBlackjackEmbed = false;
			justEnded = true;
		}
		else if(checkBust(botHand)) {
			board.setTitle("You win!");
			board.setDescription("Bot went bust. To play again, enter **" + again + "**");
			playingBlackjackEmbed = false;
			justEnded = true;
		}
		
		
		else if(handValue(playerHand) == 21 && handValue(botHand) == 21) {
			board.setTitle("You tied.");
			board.setDescription("You both got 21. To play again, enter **" + again + "**");
			playingBlackjackEmbed = false;
			justEnded = true;
		}
		else if(handValue(botHand) == 21) {
			board.setTitle("You lose.");
			board.setDescription("Bot got 21. To play again, enter **" + again + "**");
			playingBlackjackEmbed = false;
			justEnded = true;
		}
		else if(handValue(playerHand) == 21) {
			board.setTitle("You win!");
			board.setDescription("You got 21! To play again, enter **" + again + "**");
			playingBlackjackEmbed = false;
			justEnded = true;
		}
		
		
		else if(playerStanding && botStanding) {
			if(handValue(playerHand) > handValue(botHand)) {
				board.setTitle("You win!");
				board.setDescription("You were both standing. Since you were closer to 21, you win! To play again, enter **" + again + "**");
				playingBlackjackEmbed = false;
				justEnded = true;
			}
			else if(handValue(playerHand) < handValue(botHand)) {
				board.setTitle("You lose.");
				board.setDescription("You were both standing. Since the bot was closer to 21, it won. To play again, enter **" + again + "**");
				playingBlackjackEmbed = false;
				justEnded = true;
			}
			else if(handValue(playerHand) == handValue(botHand)) {
				board.setTitle("You tied.");
				board.setDescription("You were both standing. Since you both had the same total, neither won. To play again, enter **" + again + "**");
				playingBlackjackEmbed = false;
				justEnded = true;
			}
		}
		//else {
			setValueBoard();
		//}
		
		//setValueBoard();
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