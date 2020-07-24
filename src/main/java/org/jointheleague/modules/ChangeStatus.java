package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class ChangeStatus extends CustomMessageCreateListener {

	private static final String COMMAND = "!status";

	public ChangeStatus(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!status","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("What do you want to change my status to?");
				
				
			} else {
				
				if(cmd.equalsIgnoreCase("reset")) {
					event.getApi().updateActivity("bot");
				}
				else {
					event.getApi().updateActivity(cmd);
				}
			}
			
		}
	}
	
}
