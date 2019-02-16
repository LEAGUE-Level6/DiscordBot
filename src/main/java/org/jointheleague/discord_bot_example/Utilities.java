package org.jointheleague.discord_bot_example;

import java.io.InputStreamReader;
import java.io.Reader;

import org.jointheleague.discord_bot_example.pojos.BotInfo;
import org.jointheleague.discord_bot_example.pojos.BotList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utilities {
	private Utilities() {
	}
	/**
	 * 
	 * @return list of bots
	 */
	public static BotList loadBotsFromJson() {
		try (Reader reader = new InputStreamReader(
				Utilities.class.getClassLoader().getResourceAsStream("config.json"))) {
			System.out.println("Loading bots");
			Gson gson = new GsonBuilder().create();
			BotList botList = gson.fromJson(reader, BotList.class);
			return botList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Failed to load bots");
		return null;
	}
}
