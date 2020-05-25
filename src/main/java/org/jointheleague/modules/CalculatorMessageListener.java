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
			if (event.getMessageContent().equals("!math")) {
				event.getChannel().sendMessage("I can do basic arithmetic operations with 2 numbers, such as: ");
				event.getChannel().sendMessage("X * Y, X + Y, X - Y, X / Y");
				event.getChannel().sendMessage("To calculate, type !math <math operation>");
			} else {
				String calc = event.getMessageContent().substring(6);
				processCalc(calc, event);
			}
		}
	}

	/** Processes a calculation. */
	public void processCalc(String s, MessageCreateEvent event) {
		String calcregex = "(\\d+) {0,1}(\\+|-|\\*|\\/) {0,1}(\\d+)";

		if (s.matches(calcregex)) {
			Pattern calcpattern = Pattern.compile(calcregex, Pattern.MULTILINE);
			Matcher matcher = calcpattern.matcher(s);

			int dig1 = Integer.parseInt(matcher.replaceAll("$1"));
			String oper = matcher.replaceAll("$2");
			int dig2 = Integer.parseInt(matcher.replaceAll("$3"));

			try {
				switch (oper) {
				case "+":
					event.getChannel().sendMessage(s + " = " + (dig1 + dig2));
					break;
				case "-":
					event.getChannel().sendMessage(s + " = " + (dig1 - dig2));
					break;
				case "*":
					event.getChannel().sendMessage(s + " = " + (dig1 * dig2));
					break;
				case "/":
					event.getChannel().sendMessage(s + " = " + (dig1 / dig2));
					break;
				}
			} catch (ArithmeticException e) {
				event.getChannel().sendMessage("OMG HOMIE MY HEAD IS EXPLODING FROM TRYING TO SOLVE DAT MATH");
			}
		} else {
			event.getChannel().sendMessage("Dude, that is not a valid arithmetic operation.");
		}
	}

}
