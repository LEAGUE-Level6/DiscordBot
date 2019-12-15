package org.jointheleague.discord_bot_example;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.CustomMessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class EightBall extends CustomMessageCreateListener {

	public EightBall(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!8ball")) {
			int choice = (int) (Math.random()*4);
			if (choice ==0) {
				event.getChannel().sendMessage("Yes");
			} else if (choice==1) {
				event.getChannel().sendMessage("No");
			} else if (choice==2) {
				event.getChannel().sendMessage("Most definitely");
			} else {
				event.getChannel().sendMessage("Nah.");
			}
		}
	}

}
