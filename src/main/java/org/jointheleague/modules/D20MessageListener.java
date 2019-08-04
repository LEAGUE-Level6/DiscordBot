package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class D20MessageListener extends CustomMessageCreateListener{

	public D20MessageListener(String channelName) {
		super(channelName);
			
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().startsWith("!roll")) {
			String str = event.getMessageContent();
			String s = str.substring(4, str.length());
			Random r = new Random();
			if(s != "")
			{
				System.out.println("You rolled a " +r.nextInt(r.nextInt(Integer.parseInt(s))));
			}
			else
			{
			System.out.println("You rolled a "+r.nextInt(20));	
			}
			
		}
		
	}

}
