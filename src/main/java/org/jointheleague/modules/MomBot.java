package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class MomBot extends CustomMessageCreateListener {
	
	
	public MomBot(String channelName) {
		super(channelName);
	}
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String m = event.getMessageContent();
		if(m.toLowerCase().contains("mom")) {
			if(m.toLowerCase().contains("can i") && !event.getMessageAuthor().isYourself()){
				event.getChannel().sendMessage("It's \"" + m.toLowerCase().replace("can", "may").replace(" i ", " I ") + "\"");
			}
			if(m.toLowerCase().contains("may i") && !event.getMessageAuthor().isYourself()){
				event.getChannel().sendMessage("No");
			}	
		}
		else if(m.toLowerCase().contains("dad")) {
			if((m.toLowerCase().contains("can i")||m.toLowerCase().contains("may i")) && !event.getMessageAuthor().isYourself()){
				event.getChannel().sendMessage("Go ask your mother");
			}
		}
	}

}
