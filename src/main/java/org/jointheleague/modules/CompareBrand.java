package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class CompareBrand extends CustomMessageCreateListener {

	private static final String COMMAND = "/compare";
	
	public CompareBrand(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Type /compare budget followed by the maximum to search within a budget, same with horse power and cost. To do multiple, do /compare all");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().toLowerCase().startsWith(COMMAND));
		event.getChannel().sendMessage("hi");
	}

}
// TODO: Find 2021 car brand models and write down a few's  seats, and horsepower, cost and put into a text file

