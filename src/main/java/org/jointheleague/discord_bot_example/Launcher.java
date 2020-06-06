package org.jointheleague.discord_bot_example;

/**
 * Launches the bot.
 * @author keithgroves and https://tinystripz.com
 *
 */
public class Launcher {
	public static void main(String[] args) {
		new Launcher().launch(args);
	}

	public void launch(String[] args) {
		// Use Utilities to load the channels and token from a JSON file...
		// ...and put the information in a BotInfo instance
		BotInfo n = Utilities.loadBotsFromJson();
		// Get the channels from the BotInfo class
		String[] channels = n.getChannels();

		// Load all of the bots for every channel
		for (int i = 0; i < channels.length; i++) {
			new Bot(n.getToken(), channels[i]).connect(i == 0);
		}
	}
}
