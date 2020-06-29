package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
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
				String definition = getWeatherInfoByLocation(msg);
				event.getChannel().sendMessage(definition);
			}
			
		}
		
		
	}
	String getWeatherInfoByLocation(String city) {
		URL url;
		try {
			url = new URL("https://community-open-weather-map.p.rapidapi.com/weather?units=imperial&q=London,US");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.addRequestProperty("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com");
			con.addRequestProperty("x-rapidapi-key", "a3be42610emsh2638719585024d5p123003jsn0d9a471a0feb");
			JsonReader repoReader = Json.createReader(con.getInputStream());
		    JsonObject userJSON = ((JsonObject) repoReader.read());
		    con.disconnect();
		    System.out.println(userJSON.toString());
		    //ApiExampleWrapper apiExampleWrapper = gson.fromJson(userJSON.toString(), ApiExampleWrapper.class);
		    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
