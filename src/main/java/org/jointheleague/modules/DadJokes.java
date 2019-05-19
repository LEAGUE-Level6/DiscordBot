package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class DadJokes extends CustomMessageCreateListener {

	private static final String c1 = "im";
	private static final String c2 = "i'm";
	private static final String c3 = "i am";
	

	public DadJokes(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String a = event.getMessageContent();
		
		String s = event.getMessageContent().toLowerCase();
		if (s.contains(c1) && !event); {
			int x = s.indexOf(c1)+3;
			event.getChannel().sendMessage("Hi "+a.substring(x)+", I'm Dad!");
		}
		if (s.contains(c2)); {
			int x = s.indexOf(c2)+4;
			event.getChannel().sendMessage("Hi "+a.substring(x)+", I'm Dad!");
		}
		if (s.contains(c3)); {
			int x = s.indexOf(c3)+5;
			event.getChannel().sendMessage("Hi "+a.substring(x)+", I'm Dad!");
		}
	}
}
