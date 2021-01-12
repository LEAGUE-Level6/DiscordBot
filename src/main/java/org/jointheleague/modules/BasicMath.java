package org.jointheleague.modules;

import java.util.Iterator;

import javax.xml.stream.events.Characters;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class BasicMath extends CustomMessageCreateListener {

	private static final String COMMAND = "!doMath"; //it turns out someone else has already implemented a feature that uses !math.
	private String[] arguments;

	public BasicMath(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"This command does basic math. The command syntax is <instruction> <first term> <second term>. "
						+ "The instruction and terms should all be separated from each other using a space character. "
						+ "The operations currently supported are \"multiply,\" \"add,\" \"subtract,\" and \"divide.\"");
	}

	// TODO: Remember to remove the bot token from config.json before commiting to
	// github.
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		//System.out.println("Heard message: " + event.getMessageContent());
		if (event.getMessageContent().contains(COMMAND)) {
			arguments = event.getMessageContent().split(" ");
			// this should result in an array like ["!math","add","1","1"]			
			
			for (int i = 0; i < arguments.length; i++) {
				System.out.println(arguments[i]);
			} System.out.println(arguments.length);
			
			
			try {
				if (arguments[1].equalsIgnoreCase("MULTIPLY") || arguments[1].equalsIgnoreCase("ADD")
						|| arguments[1].equalsIgnoreCase("SUBTRACT") || arguments[1].equalsIgnoreCase("DIVIDE")) {

					event.getChannel().sendMessage("The answer to your math question is " + doBasicMath(arguments[1],
							Double.parseDouble(arguments[2]), Double.parseDouble(arguments[3])));
				} else {
					event.getChannel().sendMessage(
							"The first argument should be an operation. The operations currently supported are \"multiply,\" \"add,\" \"subtract,\" and \"divide.\"");
				}
			} catch (NumberFormatException e) {
				event.getChannel().sendMessage(
						"The two values to be added must be numbers. The command syntax is <instruction> <first term> <second term>.");
			} catch (NullPointerException e) {
				event.getChannel().sendMessage("One of the parameters is a null value.");
			} catch (ArrayIndexOutOfBoundsException e) {
				event.getChannel().sendMessage("You must supply all three parameters. The command syntax is <instruction> <first term> <second term>.");
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
			return term1 - term2;
		case "DIVIDE":
			if (term2 != 0) {
				return term1 / term2;
			}
			break;
		}

		return Double.NEGATIVE_INFINITY;
	}

}
