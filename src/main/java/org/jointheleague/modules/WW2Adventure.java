package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class WW2Adventure extends CustomMessageCreateListener{
	private static final String COMMAND = "!BeginWW2Journey";
	
	public WW2Adventure(String channelName)  {
		// TODO Auto-generated constructor stub
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String txt = event.getMessageContent();
		txt.trim();
		if(txt.equalsIgnoreCase(COMMAND)) {
			event.getChannel().sendMessage("Hello soldier, you have received a draft to the war!");
		}
	}

}
