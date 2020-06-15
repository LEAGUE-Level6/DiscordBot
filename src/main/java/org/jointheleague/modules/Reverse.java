package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Reverse extends CustomMessageCreateListener {

	private static final String COMMAND = "!reverse";
	
	public Reverse(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Call " + COMMAND + " (Message) and it will return the given word or phrase as itself, but backwards");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String message = event.getMessageContent().replace(COMMAND, "");
			String backwards = "";
			
			for (int i = message.length() - 1; i >= 0; i--) {
				backwards += message.charAt(i);
			}
			
			event.getChannel().sendMessage(backwards);
		}
	}
}
