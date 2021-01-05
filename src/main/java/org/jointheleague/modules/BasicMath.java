package org.jointheleague.modules;

import javax.xml.stream.events.Characters;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class BasicMath extends CustomMessageCreateListener {

	private static final String COMMAND = "!math";
	private String[] arguments;

	public BasicMath(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"This command does basic math. The command syntax is <instruction> <first term> <second term>. "
						+ "The instruction and terms should all be separated from each other using a space character. "
						+ "The operations currently supported are \"multiply\", \"add\", \"subtract\", and \"divide\"");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains(COMMAND)) {
			arguments = event.getMessageContent().split("" + Characters.SPACE);
			// this should result in an array like ["!math","add","1","1"]
			if (arguments[1].equalsIgnoreCase("MULTIPLY") || arguments[1].equalsIgnoreCase("ADD")
					|| arguments[1].equalsIgnoreCase("SUBTRACT") || arguments[1].equalsIgnoreCase("DIVIDE")) {
				try {
					event.getChannel().sendMessage("The answer to your math question is " + doBasicMath(arguments[1],
							Double.parseDouble(arguments[2]), Double.parseDouble(arguments[3])));
				} catch (NumberFormatException e) {
					
				} catch (NullPointerException e) {

				} catch (ArrayIndexOutOfBoundsException e) {

				}
			} else {

			}
		}
	}

	private double doBasicMath(String operator, double term1, double term2) {
		switch (operator.toUpperCase()) {
		case "MULTIPLY":
			return term1 * term2;
		case "ADD":
			return term1 + term2;
		case "SUBTRACT":
			return term1 + term2;
		case "DIVIDE":
			if (term2 != 0) {
				return term1 + term2;
			}
			break;
		}

		return Double.NEGATIVE_INFINITY;
	}

}
