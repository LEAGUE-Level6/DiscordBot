package org.jointheleague.discordBotExample;

import java.io.InputStreamReader;
import java.io.Reader;

import org.jointheleague.discordBotExample.config.BotInfo;
import org.jointheleague.discordBotExample.config.BotList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utilities {
	public static BotList loadBots() {
		try (Reader reader = new InputStreamReader(
				Utilities.class.getClassLoader().getResourceAsStream("config.json"))) {
			System.out.println("Loading bots");
			Gson gson = new GsonBuilder().create();
			BotList botList = gson.fromJson(reader, BotList.class);
			System.out.println("Success!");
			return botList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Failed to load bots");
		return null;
	}
}
