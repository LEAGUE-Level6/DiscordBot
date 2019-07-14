package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class FunnyMessageListener extends CustomMessageCreateListener{	
	ArrayList<String> funny;
	public FunnyMessageListener(String channelName) {
		super(channelName);
		funny.add("its not funny");
		funny.add("https://cdn.discordapp.com/emojis/585915214596276234.gif?v=1");
		funny.add("wheres the funny https://cdn.discordapp.com/emojis/453482352279027732.png?v=1");
		funny.add("https://cdn.discordapp.com/emojis/586571576338546739.png?v=1");
		funny.add("who wants to raid area51");
		funny.add("oh nowwood fired pizza hows pizzzzzzzzzzza gonna job noW!????");
		
	}
	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!funny")) {
			event.getChannel().sendMessage(funny.get(new Random().nextInt(funny.size())));
		}
		
	}

}
