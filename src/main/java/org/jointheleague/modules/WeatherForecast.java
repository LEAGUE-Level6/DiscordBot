package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class WeatherForecast extends CustomMessageCreateListener{

	public final static String COMMAND = "/weather";
	
	public WeatherForecast(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String[] str = event.getMessageContent().split(" ");
		if(str[0].equals(COMMAND)) {
			if(str[1].equals("checkLongLat")) {
				event.getChannel().sendMessage("to find your latitude and longitude, go to: \n latlong.net");
			}
		}
	}

}
