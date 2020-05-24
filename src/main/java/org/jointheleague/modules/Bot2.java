package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class Bot2 extends CustomMessageCreateListener {
	public Bot2(String channelName) {
		super(channelName);
	}

	boolean inGame = false;
	boolean p1 = false;
	long player = 0;

	// BOT'S ID: 691338351025848351
	public void handle(MessageCreateEvent event) {
		if (event.getMessageAuthor().getId() == 691338351025848351L) {
			return;
		} else if (inGame && event.getMessageAuthor().getId() != player) {
			event.getChannel().sendMessage("begone1!1!!!!11!!1oneone1!");
		} else if (!inGame && !p1 && event.getMessageContent().equals("!stix")) {
			player = event.getMessageAuthor().getId();
			p1 = true;
			inGame = true;
			int pl = 1, pr = 1, cl = 1, cr = 1;
			while(true) {
				event.getChannel().sendMessage("You: "+pl+" (left), "+pr+" (right)");
				event.getChannel().sendMessage("AI:    "+cl+" (left), "+cr+" (right)");
				event.getChannel().sendMessage(":open_hands::open_hands::open_hands:");
			}
		} 
	}
}
