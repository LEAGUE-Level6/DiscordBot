package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class SimpleChat implements MessageCreateListener {

	private static final String BEGIN = "hello hmmm";
	private static final String END = "bye hmmm";
	private boolean conversing = false;

	public SimpleChat(String channelName) {
		super();
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		if(conversing) {
			if(event.getMessageContent().equalsIgnoreCase(END)) {
				conversing = false;
				event.getChannel().sendMessage("Okay, goodbye " + event.getMessageAuthor().getDisplayName());
			}
			else if(!event.getMessageAuthor().getDisplayName().equals("hmmm")){
				Random r = new Random();
				int messageNum = r.nextInt(5);
				switch(messageNum) {
				case 0:
					event.getChannel().sendMessage("Wow that sounds interesting.");
					break;
				case 1:
					event.getChannel().sendMessage("Cool.");
					break;
				case 2:
					event.getChannel().sendMessage("Fun.");
					break;
				case 3:
					event.getChannel().sendMessage("Hmmm...");
					break;
				case 4:
					event.getChannel().sendMessage("Ha ha.");
					break;
				default:
					event.getChannel().sendMessage("Something went wrong.");
					break;
				}
			}
		}
		else if(event.getMessageContent().equalsIgnoreCase(BEGIN)) {
			conversing = true;
			event.getChannel().sendMessage("Hello there " + event.getMessageAuthor().getDisplayName());
		}
	}
	
}
