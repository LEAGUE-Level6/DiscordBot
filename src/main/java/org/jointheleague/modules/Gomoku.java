package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Gomoku extends CustomMessageCreateListener {
	
	private static final String COMMAND = "!gomoku";
	
	public Gomoku(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Starts a game of gomoku. !gomokuRules shows the rules. To play, put in a coordinate (e.g. !gm0,0) to place a marker there.");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {
			String command = event.getMessageContent().replaceAll(" ", "").replace("!gomoku", "");
			
			if(command.equals("")) {
				event.getChannel().sendMessage();
			}
		}
	}

}
