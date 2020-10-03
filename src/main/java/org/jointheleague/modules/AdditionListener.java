package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class AdditionListener extends CustomMessageCreateListener{
	private static final String COMMAND = "!add";

	public AdditionListener(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "add two single digit numbers together");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {

			String cmd = event.getMessageContent().replace("!add ", "");

			char a = cmd.charAt(0);
			char b = cmd.charAt(2);
			int n1 = Integer.parseInt(String.valueOf(a));
			int n2 = Integer.parseInt(String.valueOf(b));
			int sum = n1 + n2;
			event.getChannel().sendMessage("the sum is " + sum);

		}
	}
}
