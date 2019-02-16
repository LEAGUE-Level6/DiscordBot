package org.jointheleague.discord_bot_example;

import org.jointheleague.discord_bot_example.pojos.BotInfo;
import org.jointheleague.discord_bot_example.pojos.BotList;

/**
 * 
 * 
 * @author keithgroves
 *
 */
public class Launcher {
	public static void main(String[] args) {
		if (args.length == 0) {
			args = new String[] { "bot" };
		}
		new Launcher().launch(args);
	}

	public void launch(String[] args) {
		for (String botName : args) {
			BotList list = Utilities.loadBotsFromJson();
			for (BotInfo info : list.getBots()) {
				if (info.getChannel().equals(botName))
					new Bot(info.getToken(), info.getChannel()).connect();
			}
		}
	}
}
