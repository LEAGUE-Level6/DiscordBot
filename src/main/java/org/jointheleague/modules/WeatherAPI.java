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
import org.jointheleague.pojo.age.Age;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class WeatherAPI extends CustomMessageCreateListener{

	private static final String COMMAND = "!age";
	private final Gson gson = new Gson();

	public WeatherAPI(String channelName) {
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
			}
			else {
				event.getChannel().sendMessage("" + getAge(msg));
			}
		}
	}
	
	public int getAge(String name) {
		String requestURL = "https://api.agify.io?name=" + name;
		try {
			URL url = new URL(requestURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
		JsonReader repoReader = Json.createReader(con.getInputStream());
	    JsonObject userJSON = ((JsonObject) repoReader.read());
		Age age = gson.fromJson(userJSON.toString(), Age.class);
		return age.getAge();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
