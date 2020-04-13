package org.jointheleague.discord_bot_example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import com.google.api.core.ApiFuture;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author keithgroves
 *
 */
public class Launcher {
	public static void main(String[] args) {
		
		if (args.length == 0) {
			args = new String[] { "default" };
		}
		new Launcher().launch(args);
	}

	public void launch(String[] args) {
		Map<String, BotInfo> map = Utilities.loadBotsFromJson();
		for (String name : args) {
			BotInfo n = map.get(name);
			new Bot(n.getToken(), n.getChannel()).connect();
		}
	}
}
