package org.jointheleague.discord_bot_example;

import org.javacord.api.DiscordApi; 
import org.javacord.api.DiscordApiBuilder;
import org.jointheleague.modules.*;


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
		api.addMessageCreateListener(new UH_HUH(channelName));
		api.addMessageCreateListener(new CTAtimes(channelName));
		api.addMessageCreateListener(new BEEP(channelName));
		api.addMessageCreateListener(new RandomNumber(channelName));
		api.addMessageCreateListener(new DadJokes(channelName));
		api.addMessageCreateListener(new ClockMessageListener(channelName));
		api.addMessageCreateListener(new CalculatorMessageListener(channelName));
		api.addMessageCreateListener(new ComicMessageListener(channelName));
		api.addMessageCreateListener(new ElmoMessageListener(channelName));
		api.addMessageCreateListener(new FactMessageListener(channelName));
		api.addMessageCreateListener(new CasinoGameListener(channelName));
		api.addMessageCreateListener(new leetMessageListener(channelName));
		api.addMessageCreateListener(new FlagMessageListener(channelName));
		api.addMessageCreateListener(new Weather(channelName));
		api.addMessageCreateListener(new FashionAdvisor(channelName));
		api.addMessageCreateListener(new NewPollMessageListener(channelName));
		api.addMessageCreateListener(new FDLinks(channelName));
		api.addMessageCreateListener(new JSONadventure(channelName));
	}

}


