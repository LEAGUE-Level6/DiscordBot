package org.jointheleague.discord_bot_example;

import java.util.List;

import org.jointheleague.discord_bot_example.pojos.BotInfo;

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
			List<BotInfo> list = Utilities.loadBotsFromJson();
			for (BotInfo info : list) {
				if (info.getChannel().equals(botName))
					new Bot(info.getToken(), info.getChannel()).connect();
			}
		}
	}
}
