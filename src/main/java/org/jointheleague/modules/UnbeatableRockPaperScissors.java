package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class UnbeatableRockPaperScissors extends CustomMessageCreateListener{
	private static final String COMMAND = "!";

	public UnbeatableRockPaperScissors(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed(COMMAND, "Unbeatable Rock, Paper, Scissors");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {
			String cmd = event.getMessageContent().replace("!", "");
			if(cmd.equalsIgnoreCase("rock")) {
				event.getChannel().sendMessage("Paper");
			}
			else if(cmd.equalsIgnoreCase("paper")) {
				event.getChannel().sendMessage("Paper Shredder");
			}
			else if(cmd.equalsIgnoreCase("scissors")) {
				event.getChannel().sendMessage("Rock");
			}else {
				event.getChannel().sendMessage("Invalid Command");
			}
		}

	}

}
