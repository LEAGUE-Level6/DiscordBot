package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class CountingListener extends CustomMessageCreateListener{
private static final String command= "!";
int num=1;
	public CountingListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String stringNum=""+num;
		if(event.getMessageContent().contains(command)) {
			String content=event.getMessageContent().replaceAll(" ", "").replace(command, "");
			if(content.equals(stringNum)) {
				num+=1;
				event.addReactionsToMessage(":thumbup:");
			}
			else {
				num=1;
				event.getChannel().sendMessage("You broke the chain. The next number is 1.");
			}
		}
	}

}
