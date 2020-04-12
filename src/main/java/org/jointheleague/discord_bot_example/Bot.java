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
		api.getServerTextChannelsByName(channelName).forEach(e -> e.sendMessage("Hello there! The bot has been connected, and you can now use any features of the bot."));
		api.getServerTextChannelsByName(channelName).forEach(e -> e.sendMessage("Always remember to prefix your commands with an exclamation mark (!)."));
		api.getServerTextChannelsByName(channelName).forEach(e -> e.sendMessage("(!) IMPORTANT: The SBot V1 (Legacy) is being retired. As of March 28th, 2020, all requests will be routed to V2, and V1 history will become read-only. On April 6th, 2020, the SBot V1 (Legacy) will be deleted from Discord and all messages will be deleted. Please save any important requests, and delete any bot commands to avoid looking like a fool when V1 messages disappear on April 6th."));
		
		
		//Add Listeners
		api.addMessageCreateListener(new ProfanityChecker(channelName));
		api.addMessageCreateListener(new ComDatabase(channelName));
		api.addMessageCreateListener(new UInames(channelName));
		api.addMessageCreateListener(new TfLtimes(channelName));
		api.addMessageCreateListener(new ChangeStatus(channelName));
		api.addMessageCreateListener(new LAtimes(channelName));
		api.addMessageCreateListener(new UH_HUH(channelName));
		api.addMessageCreateListener(new Repeat(channelName));
		api.addMessageCreateListener(new CTAtimes(channelName));
		api.addMessageCreateListener(new WeeklyStupidity(channelName));
		api.addMessageCreateListener(new AnyAPI(channelName));
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
		api.addMessageCreateListener(new GithubRepos(channelName));
		api.addMessageCreateListener(new Analogizer(channelName));
		
	}

}


