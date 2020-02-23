package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class NumToBinary extends CustomMessageCreateListener{

	public NumToBinary(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains("!binary")) {
			int num = Integer.parseInt(event.getMessageContent().replaceAll(" ", "").replace("!binary",""));
			System.out.println(num);
			
			/*for (int i = 0; i < array.length; i++) {
				
			}*/
		}
		
	}

}
