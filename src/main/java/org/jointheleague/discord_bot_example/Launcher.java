package org.jointheleague.discord_bot_example;

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
