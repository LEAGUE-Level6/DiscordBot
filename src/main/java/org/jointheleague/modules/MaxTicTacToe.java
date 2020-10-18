package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class MaxTicTacToe extends CustomMessageCreateListener{

	public MaxTicTacToe(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		boolean playing = false;
		String message = event.getMessageContent();
		String topHalf = "|------|------|------|\r\n" + 
				"|          |         |          |\r\n" + 
				"|------|------|------|";
		String middle = "|          |         |          |";
		String bottomHalf = "|------|------|------|\r\n" + 
				"|          |         |          |\r\n" + 
				"|------|------|------|";
		if(message.contains("!playTicTacToe") && !playing) {
			playing = true;
			event.getChannel().sendMessage(middle);

		}
	}
	
}
