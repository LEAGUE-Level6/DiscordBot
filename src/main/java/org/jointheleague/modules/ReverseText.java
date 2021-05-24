package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class ReverseText extends CustomMessageCreateListener {
	
	private static final String COMMAND = "!reverse";

	public ReverseText(String channelName) {
		super(channelName);
	}
	
	@Override
	public void handle(MessageCreateEvent event) {
		String message = event.getMessageContent().trim();
				
		if(message.contains(COMMAND) && message.length() > COMMAND.length()) {
			message = message.replace("!reverse", "").trim();
			
			String reverseMessage = "";
			for(int i = message.length() - 1; i >= 0; i--) {
				reverseMessage += message.charAt(i);
			}
			event.getChannel().sendMessage(reverseMessage);
		}
	}
}
