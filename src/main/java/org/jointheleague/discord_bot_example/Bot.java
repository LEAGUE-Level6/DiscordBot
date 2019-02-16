package org.jointheleague.discord_bot_example;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.jointheleague.modules.PingMessageListener;

public class Bot  {
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
	}

}


