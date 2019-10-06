package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class CasinoGameListener extends CustomMessageCreateListener{

	final String BET_ALL_COMMAND = "!bet-it-all";
	final String GET_COINS_COMMAND = "!get-my-coins";
	final String HELP_COMMAND = "!casino help";
	final String BET_COMMAND = "!bet ";
	
	public CasinoGameListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		if (message.startsWith(BET_ALL_COMMAND)) {
			handleBetItAll(event);
		}
		else if () {
			
		}
	}
	
	void handleBetItAll(MessageCreateEvent event)
	{
		
	}

}
