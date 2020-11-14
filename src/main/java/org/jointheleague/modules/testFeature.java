package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class testFeature extends CustomMessageCreateListener {
	public static final String COMMAND = "Snake";

	public testFeature(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Does stuff");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().length() >= COMMAND.length() && event.getMessageContent().substring(0, COMMAND.length()).equals(COMMAND)) {
			
			for(int i = 0; i < 20; i++) {
				event.getChannel().sendMessage("Message");
			}
			
			
		}
	

	}

}
