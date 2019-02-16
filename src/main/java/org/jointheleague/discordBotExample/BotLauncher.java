package org.jointheleague.discordBotExample;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.jointheleague.discordBotExample.config.BotInfo;
import org.jointheleague.discordBotExample.config.BotList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * BotLauncher can launch one or more bots if needed.
 * 
 * @author keithgroves
 *
 */
public class BotLauncher {
	public static void main(String[] args) {
		new BotLauncher().launch();
	}

	public void launch() {
		BotList p = Utilities.loadBots();
		for (BotInfo b : p.getBots()) {
			new Bot(b.getToken(), b.getChannel()).connect();
		}
	}
}
