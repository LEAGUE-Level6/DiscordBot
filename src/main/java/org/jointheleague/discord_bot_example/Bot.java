package org.jointheleague.discord_bot_example;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.jointheleague.modules.CalculatorMessageListener;
import org.jointheleague.modules.ClockMessageListener;
import org.jointheleague.modules.ElmoMessageListener;
import org.jointheleague.modules.PingMessageListener;

public class Bot {
	// FOR FUTURE CODERS:
	// If you get an exception involving websockets, check your config.json file
	// in the src/main/resources folder. It should have your bot's token and channel.
	private String token;
	private String channelName;
	DiscordApi api;

	public Bot(String token, String channelName) {
		this.token = token;
		this.channelName = channelName;
	}

	public void connect() {
		api = new DiscordApiBuilder().setToken(token).login().join();
		System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
		api.getServerTextChannelsByName(channelName).forEach(e -> e.sendMessage("Bot Connected"));
		
		//add Listeners
		api.addMessageCreateListener(new PingMessageListener(channelName));
		api.addMessageCreateListener(new ClockMessageListener(channelName));
		api.addMessageCreateListener(new CalculatorMessageListener(channelName));
		api.addMessageCreateListener(new ElmoMessageListener(channelName));
	}

}


