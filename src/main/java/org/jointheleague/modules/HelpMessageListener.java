package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;



public class HelpMessageListener extends CustomMessageCreateListener {
	
	private static final String COMMAND = "!help";

	public HelpMessageListener(String channelName) {
		super(channelName);	
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			event.getChannel().sendMessage("Here are the different commands for the Tide Bot: \n"
					+ "!help - gets a list of commands"
					+ "\n !dadon - turns on the dad mode"
					+ "\n !dadoff - turns off the dad mode"
					+ "\n !solvequad - solves quadratic equation"
					+ "\n !addrole - assigns roles to those below you in the role hierarchy"
					+ "\n !coinflip - flips a coin"
					+ "\n !kick - kicks those who can be kicked below you"
					+ "\n !nick - works with the manipulation or alteration of nicknames"
					+ "\n !ping - shows your current ping"
					+ "\n !playrps - starts a game of Rock Paper Scissors against the bot"
					+ "\n !quit - turns off the bot");
		}
	}

}
