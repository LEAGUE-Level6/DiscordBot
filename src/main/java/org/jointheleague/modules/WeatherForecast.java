package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class WeatherForecast extends CustomMessageCreateListener {

	public final static String COMMAND = "/weather";
	String findGrid = "https://api.weather.gov/points/"; // add [latitude],[longitude] to call
	String latitude = "";
	String longitude = "";
	String allData = "";

	public WeatherForecast(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed(COMMAND, "There are 3 commands: \n " + COMMAND
				+ " checklonglat: to find out how to find your current longitudinal and latitudinal position(An example is 40, 20) \n "
				+ COMMAND + " weekWeather: to check what the weather will be like this week \n" + COMMAND
				+ " dailyWeather: to check what the weather is at that 12 hour period");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String[] str = event.getMessageContent().split(" ");
		if (str[0].equals(COMMAND)) {
			if (str.length == 1) {
				event.getChannel()
						.sendMessage("you have to add a command, pick one: CheckLongLat, WeekWeather, or DailyWeather");
			}
			if (str[1].equalsIgnoreCase("checklonglat")) {
				event.getChannel().sendMessage(
						"to find your latitude and longitude, go to: \n latlong.net \n and type in your current location. Type in the website your address and it gives you the latitude and longitude of your position. \n Example calls: \n /weather weekweather 10,4 \n /weather dailyweather 3,2  \n NO SPACES BETWEEN THE LATITUDE AND LONGITUDE");
			} else if (str[1].equalsIgnoreCase("weekWeather")) {
				if (str.length == 4 && str[2].contains(",")) {
					str[2] = str[2] + str[3];
				}
				if (str.length >= 3) {
					try {
						String num1;
						String num2;
						if (str[2].contains(",")) {
							String[] str2 = str[2].split(",");
							num1 = str2[0];
							num2 = str2[1];
						} else {
							num1 = str[2];
							num2 = str[3];
						}
						String get = findGrid + num1 + "," + num2;
						BufferedReader br = makeGetRequest(get);
						String response = parseResponse(br);
						WeatherForecastData wfd = getPos(Float.parseFloat(num1), Float.parseFloat(num2));
						get = wfd.properties.forecast;
						WeatherForecastData wfd2 = getForecast(wfd);
						for (int i = 0; i < wfd2.properties.periods.length; i++) {
							allData = "Forecast for: " + (String) wfd2.properties.periods[i].toString() + "\n"
									+ "---|||---" + "\n";
							event.getChannel().sendMessage(allData);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					event.getChannel().sendMessage(
							"make sure it includes your grid which can be found at latlong.net. Example: /weather weekweather 40,20 \n NO SPACES BETWEEN THE LATITUDE AND LONGITUDE");
				}
			} else if (str[1].equalsIgnoreCase("dailyWeather")) {
				if (str.length == 4 && str[2].contains(",")) {
					str[2] = str[2] + str[3];
				}
				if (str.length >= 3) {
					try {
						String num1;
						String num2;
						if (str[2].contains(",")) {
							String[] str2 = str[2].split(",");
							num1 = str2[0];
							num2 = str2[1];
						} else {
							num1 = str[2];
							num2 = str[3];
						}
						String get = findGrid + num1 + "," + num2;
						BufferedReader br = makeGetRequest(get);
						String response = parseResponse(br);
						WeatherForecastData wfd = getPos(Float.parseFloat(num1), Float.parseFloat(num2));
						get = wfd.properties.forecast;
						WeatherForecastData wfd2 = getForecast(wfd);
						allData = "Forecast for: " + (String) wfd2.properties.periods[0].toString();
						event.getChannel().sendMessage(allData);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					event.getChannel().sendMessage(
							"make sure it includes your grid which can be found at latlong.net. Example: /weather dailyweather 10,22  \n NO SPACES BETWEEN THE LATITUDE AND LONGITUDE");
				}
			}
		}
	}

	private WeatherForecastData getPos(float lat, float longi) {
		final Gson gson = new Gson();
		try {
			String get = (String) findGrid + lat + "," + longi;
			BufferedReader br = makeGetRequest(get);
			String response = parseResponse(br);
			WeatherForecastData wfd = gson.fromJson(response, WeatherForecastData.class);
			return wfd;
		} catch (Exception e) {
		}
		return null;
	}

	private WeatherForecastData getForecast(WeatherForecastData wfd) {
		final Gson gson = new Gson();
		try {
			String get = wfd.properties.forecast;
			BufferedReader br = makeGetRequest(get);
			String response = parseResponse(br);
			wfd = gson.fromJson(response, WeatherForecastData.class);
			return wfd;
		} catch (Exception e) {
		}
		return null;
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
