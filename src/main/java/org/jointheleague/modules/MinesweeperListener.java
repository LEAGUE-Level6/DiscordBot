package org.jointheleague.modules;

import java.util.HashMap;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class MinesweeperListener extends CustomMessageCreateListener {
	
	//Stores each game with a String of a user mention id so the code knows which game is being played based on the user
	HashMap<String, MinesweeperGame> games = new HashMap<String, MinesweeperGame>();
	
	public MinesweeperListener(String channelName) {
		super(channelName);
	}

	void parsePositions() {
		
	}
	
	void parseParameters() {
		
	}
	
	void displayGrid() {
		
	}
	
	void getEmojiForCell() {
		
	}
	
	void interactCommand() {
		
	}
	
	void flagCommand() {
		
	}
	
	void createCommand() {
		
	}
	
	void quitCommand() {
		
	}
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
	}

}
