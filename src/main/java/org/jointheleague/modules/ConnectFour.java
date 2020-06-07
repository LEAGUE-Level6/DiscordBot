package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class ConnectFour extends CustomMessageCreateListener {

	private static final String COMMAND = "!Connect4";

	public ConnectFour(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		int numPlayers = 2;
		boolean gameInProgress = false;
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace(COMMAND,"");
			
			if(cmd.equals("")) {
				
				
				event.getChannel().sendMessage("Please put a string after the command!");
				
				
			} else {
				if(cmd.equals("Start") && gameInProgress == false) {
				event.getChannel().sendMessage("Game has started. Type '!vowelString join' to join. Maximum of "+ numPlayers + " allowed");
				}
				int joinedPlayers = 0;
				if(gameInProgress == true && joinedPlayers<numPlayers && cmd.equals ("join")){
					
				}
			}
			
		}
	}
	
}