package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Bot1Listener extends CustomMessageCreateListener{

	public Bot1Listener(String channelName) {
		super(channelName);
	}
	static int stuff=0;
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		while(true) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.getChannel().sendMessage("!stuff");
		}
	}

}
