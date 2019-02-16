package org.jointheleague.discord_bot_example;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Utilities {
	private Utilities() {
	}

	/**
	 * 
	 * @return list of bots
	 */
	public static Map<String, BotInfo> loadBotsFromJson() {
		try (Reader reader = new InputStreamReader(
				Utilities.class.getClassLoader().getResourceAsStream("config.json"))) {
			System.out.println("Loading bots");
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(reader, new TypeToken<Map<String, BotInfo>>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Failed to load bots");
		return null;
	}
}
