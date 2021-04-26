package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class GoFish extends CustomMessageCreateListener{
	//game command
	private static final String COMMAND = "!gofish";
	
	//picking the player
	boolean checkPlayerNumber = false;
	String playerGuess ="";
	Random r = new Random();
	int randomNum = r.nextInt(11);
	
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
		pickFirstPlayer(event);
		
	}
	
	public String pickFirstPlayer(MessageCreateEvent event) {
		String cmd = event.getMessageContent();

		String firstPlayer = null;
		
		if(cmd.equals(COMMAND)) {
			event.getChannel().sendMessage(randomNum+"Guess my number between 0 and 10 and I'll let you go first");
			checkPlayerNumber= true;
		}
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
		}
		return firstPlayer;
	}
	
	
	public void dealCards(MessageCreateEvent event) {
		//show the player's cards in list format
		//store in ArrayList
		//save bot's cards in ArrayList
		//save rest of deck into ArrayList for drawing pile
		for(int i = 0; i < 7; i ++) {
			
		}
		
	}

}
