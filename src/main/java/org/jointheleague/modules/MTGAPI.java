package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class MTGAPI extends CustomMessageCreateListener{
	private static final String activator = "!get card";
	
	public MTGAPI(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String eventContent = event.getMessageContent();
		if(eventContent.contains(activator)) {
			String temp = new String(eventContent.substring(eventContent.indexOf(activator)+9,eventContent.length()));
			event.getChannel().sendMessage(temp);
			
		}
		
		
	}



}
