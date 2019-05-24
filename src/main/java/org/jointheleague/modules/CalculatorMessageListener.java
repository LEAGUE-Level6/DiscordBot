package org.jointheleague.modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javacord.api.event.message.MessageCreateEvent;

public class CalculatorMessageListener extends CustomMessageCreateListener {

	public CalculatorMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!math")) {
			event.getChannel().sendMessage("I can do basic arithmetic operations with 2 numbers, such as: ");
			event.getChannel().sendMessage("X * Y, X + Y, X - Y, X / Y");
			event.getChannel().sendMessage("What do you want me to calculate?");

			if (processCalc(event.getMessageContent()) != ) {
				
			} else {
				event.getChannel().sendMessage("Dude, that is not a valid arithmetic operation.");
			}
		}
	}
	
	/** Processes a calculation. */
	public Object[] processCalc(String s) {
		if (calc.matches(calcregex)) {
			Pattern calcpattern = Pattern.compile(calcregex, Pattern.MULTILINE);
			Matcher matcher = calcpattern.matcher(calcregex);
			
		} else {
			event.getChannel().sendMessage("Dude, that is not a valid arithmetic operation.");
		}
		return Object[]
	}

}
