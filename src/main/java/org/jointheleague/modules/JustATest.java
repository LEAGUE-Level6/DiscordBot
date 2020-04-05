package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class JustATest extends CustomMessageCreateListener{
	public JustATest(String channelName) {
		super(channelName);
	}

	private static final String gameCommand = "!game";

	public void handle(MessageCreateEvent event) {
		int rows = 5;
		int cols = 5;
		int[][] board = new int[rows][cols];
		String matrix = "";
		if (event.getMessageContent().equals(gameCommand)) {
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					board[row][col] = row;
				}
			}
			
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					matrix += board[rows][col] + "	";
				}
				matrix += "\n";
			}
		} 
		
	}
	
}
