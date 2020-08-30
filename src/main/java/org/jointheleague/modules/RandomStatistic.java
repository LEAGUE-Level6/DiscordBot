package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class RandomStatistic extends CustomMessageCreateListener{
	public RandomStatistic(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Spews a random statistic. The more you know!");
	}

	private static final String COMMAND = "!statistic";
	
	public void handle(MessageCreateEvent event) {
		String[] facts = new String[5];
		facts[0] = "The global rate for washing hands after using the toilet is under 20 percent";
		facts[1] = "More than 36 million U.S. adults cannot read above a third grade level. That's more than 10 percent of people.";
		facts[2] = "There are nearly 12,000 annual injuries related to TVs falling in the U.S.";
		facts[3] = "Only 3 percent of adults over 65 use Snapchat. That still seems too high...";
		facts[4] = "One in five American high schoolers are smoking e-cigarettes. I hope you aren't one of them.";

		if (event.getMessageContent().contains(COMMAND)) {
			
			
			event.getChannel().sendMessage(facts[new Random().nextInt(5)]);
		}
	}
}
