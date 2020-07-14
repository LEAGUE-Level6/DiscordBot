package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.Weather.Main;
import org.jointheleague.modules.pojo.Weather.Sys;
import org.jointheleague.modules.pojo.Weather.Weather;
import org.jointheleague.modules.pojo.Weather.WeatherResponse;
import org.jointheleague.modules.pojo.Weather.Wind;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class NewWeather extends CustomMessageCreateListener{
	private static final String COMMAND = "?weather";
	private final Gson gson = new Gson();
	public NewWeather(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {

			//remove the command so we are only left with the search term
			String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");
			
			if (msg.equals("")) {
				event.getChannel().sendMessage("Please put a word after the command");
			} else {
				MessageBuilder definition = getWeatherInfoByLocation(msg);
				definition.send(event.getChannel());
			}
			
		}
		
		
	}
	MessageBuilder getWeatherInfoByLocation(String city) {
		URL url;
		try {
			url = new URL("https://community-open-weather-map.p.rapidapi.com/weather?units=imperial&q=San%20Diego,%20USA");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.addRequestProperty("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com");
			con.addRequestProperty("x-rapidapi-key", "a3be42610emsh2638719585024d5p123003jsn0d9a471a0feb");
			JsonReader repoReader = Json.createReader(con.getInputStream());
		    JsonObject userJSON = ((JsonObject) repoReader.read());
		    con.disconnect();
		    System.out.println(userJSON.toString());
		    WeatherResponse response = gson.fromJson(userJSON.toString(), WeatherResponse.class);
		    Sys sys = response.getSys();
		    Main main =  response.getMain();
		    Wind wind = response.getWind();
		    Weather weather = response.getWeather().get(0);
		    String name = response.getName();
		    String country = sys.getCountry();
		    String conditions = weather.getMain();
		    String description = weather.getDescription();
		    double temp = main.getTemp();
		    double feelsLike = main.getFeelsLike();
		    double high = main.getTempMax();
		    double low = main.getTempMin();
		    int humidity = main.getHumidity();
		    double windSpeed = wind.getSpeed();
		    int windDirection = wind.getDeg();
		    MessageBuilder message = new MessageBuilder()
		    .setEmbed(new EmbedBuilder()
		    		.setTitle("Weather in "+name+", "+country)
		    		.addField("Temperature: "+temp,"Feels like: "+feelsLike+"\n"
		    				+ "Humidity: "+humidity)
		    		.addField("High: "+high, "Low: "+low)
		    		.addField("Current Conditions: "+conditions,"Description of conditions: "+description)
		    		.addField("Wind:", "Speed: "+windSpeed+"MPH \n"
		    				+ "Direction: "+windDirection+" degrees")
		    		.setFooter("Didn't get the city you wanted? Be more specific."));
		    System.out.println(temp);
		    return message;
		    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
}
