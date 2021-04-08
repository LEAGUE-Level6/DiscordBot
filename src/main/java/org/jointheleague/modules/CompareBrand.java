package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class CompareBrand extends CustomMessageCreateListener {

	
	
	public CompareBrand(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		
	}

}
// TODO: Find 2021 car brand models and write down a few's mpg, cost, and horsepower