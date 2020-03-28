package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class AdvancedHangmanListener extends CustomMessageCreateListener {
	String helpCommand = "!help";
	String guessCommand = "!guess";
	String uncoverCommand = "!uncover";
	String startCommand = "!start";
	public AdvancedHangmanListener(String channelName) {
		super(channelName);
		
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().startsWith(startCommand)) {
		int pointTotal = 10;
		event.getChannel().sendMessage("Welcome to Advanced Hangman! Here is how you play: \n 1) You will be presented with a random cliche phrase and given the same amount of boxes as the letters in each word.");
		event.getChannel().sendMessage("2) Here are your commands: \n '!start'-starts a new game \n '!help'-shows all commands for reference \n '!uncover-use to uncover a letter; enter guesses in format (Word#,Letter#) \n '!guess'-use to make a guess");
		}
	}

}
