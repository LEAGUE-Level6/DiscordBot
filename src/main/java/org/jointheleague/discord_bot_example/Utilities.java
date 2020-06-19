package org.jointheleague.discord_bot_example;

import java.io.*;
import java.util.Arrays;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * This class contains the `loadBotsfromJson()` method, which parses the JSON file
 * and returns a BotInfo class.
 * @author keithgroves and https://tinystripz.com
 *
 */
public class Utilities {
	private Utilities() {

	}

	/**
	 * Parses the JSON file and returns a BotInfo class.
	 * @return BotInfo
	 */
	public static BotInfo loadBotsFromJson() {
		JSONParser parser = new JSONParser();
		try {
			// Import and parse JSON file
			FileReader fr = new FileReader(new File("src/main/resources/config.json"));
			JSONObject json = (JSONObject) parser.parse(fr);

			System.out.println(json);
			// Get the array of channels and API token
			JSONArray channels = (JSONArray) json.get("channels");
			String token = (String) json.get("token");

			System.out.println(channels + " " + token);
			// Create a BotInfo and set the channels
			BotInfo info = new BotInfo();
			info.setChannels(Arrays.copyOf(channels.toArray(), channels.size(), String[].class));
			info.setToken(token);

			return info;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
}
