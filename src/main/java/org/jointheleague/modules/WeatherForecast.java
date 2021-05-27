package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class WeatherForecast extends CustomMessageCreateListener {

	public final static String COMMAND = "/weather";
	String findGrid = "https://api.weather.gov/points/"; // add [latitude],[longitude] to call
	String latitude = "";
	String longitude = "";

	public WeatherForecast(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String[] str = event.getMessageContent().split(" ");
		str = event.getMessageContent().split(",");
		if (str[0].equals(COMMAND)) {
			if (str[1].equalsIgnoreCase("checkLongLat")) {
				event.getChannel().sendMessage("to find your latitude and longitude, go to: \n latlong.net");
			} else if (str[1].equalsIgnoreCase("checkgrid")) {
				try {
				String get = findGrid + str[2] + "," + str[3];
				BufferedReader br = makeGetRequest(get);
				String response = parseResponse(br);
				System.out.println(response);
				event.getChannel().sendMessage(response);
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else if (str[1].equalsIgnoreCase("checkweather")) {

			}
		}
	}

	private BufferedReader makeGetRequest(String get) throws IOException {
		URL url = new URL(get);
		HttpURLConnection connector = (HttpURLConnection) url.openConnection();
		connector.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(connector.getInputStream()));
		return br;
	}

	private String parseResponse(BufferedReader br) {
		String response = "";
		String line = "";
		try {
			line = br.readLine();
			while (line != null) {
				response += line;
				line = br.readLine();
			}
			br.close();
			response = response.trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}
}
