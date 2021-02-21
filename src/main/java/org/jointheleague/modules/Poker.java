package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class Poker extends CustomMessageCreateListener {

	public Poker(String channelName) {
		// TODO Auto-generated constructor stub
		super(channelName);
	}
	private static final String command = "!gamble";
	int balance=50;
	int wager;
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(command)) {
			String content = event.getMessageContent().replaceAll(" ", "").replace("!random","");
			if(content.contains("poker")) {
				content=content.replace("poker", "");
				try {
				wager=Integer.parseInt(content);
				balance-=wager;
				if(balance<0) {
					event.getChannel().sendMessage("You don't have enough money to wager that much.");
				}
				else {
					//send photos of cards
				}
				}
				catch(Exception e){
					event.getChannel().sendMessage("Choose a number");
				}
			}
		}
	}

}
