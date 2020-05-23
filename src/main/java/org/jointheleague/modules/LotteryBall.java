package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class LotteryBall extends CustomMessageCreateListener {
	public LotteryBall(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains("!lotteryball")) {
			event.getChannel().sendMessage("Hi");
		}
	}
}
