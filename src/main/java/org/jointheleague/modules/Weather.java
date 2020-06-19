package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.core.OWM.Unit;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.CurrentWeather;


public class Weather extends CustomMessageCreateListener {

	public Weather(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains("!weather")) {
			
			String loc = event.getMessageContent().replaceAll(" ", "").replace("!weather","");
			
			if(loc.equals("")) {
				
                event.getChannel().sendMessage("[WeatherBot] Please specify a location.");
				
				
			} else {
				
				event.getChannel().sendMessage("[WeatherBot] Getting weather data for " + loc + "...");
				
				OWM owm = new OWM("129a81b90112bf5663ab219a65c408e3");
				owm.setUnit(Unit.IMPERIAL);
				CurrentWeather cwd = owm.currentWeatherByCityName(loc);
				
				
				
				event.getChannel().sendMessage("City: " + cwd.getCityName());
				event.getChannel().sendMessage("Temperature: " + cwd.getMainData().getTempMax() + "F");
				
				
			}
			
		}
	}
}
