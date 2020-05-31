package org.jointheleague.discord_bot_example;

import org.javacord.api.DiscordApi; 
import org.javacord.api.DiscordApiBuilder;
import org.jointheleague.modules.Blackjack;
import org.jointheleague.modules.CalculatorMessageListener;
import org.jointheleague.modules.CasinoGameListener;
import org.jointheleague.modules.RandomNumber;
import org.jointheleague.modules.RockPaperScissorsListener;
import org.jointheleague.modules.Weather;
import org.jointheleague.modules.DadJokes;
import org.jointheleague.modules.ClockMessageListener;
import org.jointheleague.modules.ComicMessageListener;
import org.jointheleague.modules.ComplimentListener;
import org.jointheleague.modules.CrazyEights;
import org.jointheleague.modules.ElmoMessageListener;
import org.jointheleague.modules.FEHStatListener;
import org.jointheleague.modules.FactMessageListener;
import org.jointheleague.modules.FlagMessageListener;
import org.jointheleague.modules.MomBot;
import org.jointheleague.modules.NewPollMessageListener;
import org.jointheleague.modules.FashionAdvisor;
import org.jointheleague.modules.leetMessageListener;


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
		api.addMessageCreateListener(new MomBot(channelName));
		api.addMessageCreateListener(new RandomNumber(channelName));
		api.addMessageCreateListener(new DadJokes(channelName));
		api.addMessageCreateListener(new ClockMessageListener(channelName));
		api.addMessageCreateListener(new CalculatorMessageListener(channelName));
		api.addMessageCreateListener(new ComicMessageListener(channelName));
		api.addMessageCreateListener(new ElmoMessageListener(channelName));
		api.addMessageCreateListener(new FactMessageListener(channelName));
		api.addMessageCreateListener(new CasinoGameListener(channelName));
		api.addMessageCreateListener(new RockPaperScissorsListener(channelName));
		api.addMessageCreateListener(new leetMessageListener(channelName));

		api.addMessageCreateListener(new FlagMessageListener(channelName));

		api.addMessageCreateListener(new Weather(channelName));
		api.addMessageCreateListener(new FashionAdvisor(channelName));
		api.addMessageCreateListener(new NewPollMessageListener(channelName));
		api.addMessageCreateListener(new ComplimentListener(channelName));
		api.addMessageCreateListener(new FEHStatListener(channelName));
		api.addMessageCreateListener(new CrazyEights(channelName));
		api.addMessageCreateListener(new Blackjack(channelName));
	}

}


