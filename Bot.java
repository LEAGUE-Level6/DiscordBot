package org.jointheleague.discord_bot_example;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.jointheleague.modules.CalculatorMessageListener;
import org.jointheleague.modules.CasinoGameListener;
import org.jointheleague.modules.ClockMessageListener;
import org.jointheleague.modules.ComicMessageListener;
import org.jointheleague.modules.DadJokes;
import org.jointheleague.modules.Economy;
import org.jointheleague.modules.ElmoMessageListener;
import org.jointheleague.modules.FactMessageListener;
import org.jointheleague.modules.FashionAdvisor;
import org.jointheleague.modules.FlagMessageListener;
import org.jointheleague.modules.MessageStorage;
import org.jointheleague.modules.MomBot;
import org.jointheleague.modules.NewPollMessageListener;
import org.jointheleague.modules.RandomNumber;
import org.jointheleague.modules.Weather;
import org.jointheleague.modules.leetMessageListener;

public class Bot {

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

		// add Listeners
		api.addMessageCreateListener(new Economy(channelName));
		api.addMessageCreateListener(helpListener);
		api.addMessageCreateListener(new MomBot(channelName));
		api.addMessageCreateListener(new DadJokes(channelName));
		api.addMessageCreateListener(new ClockMessageListener(channelName));
		api.addMessageCreateListener(new CalculatorMessageListener(channelName));
		api.addMessageCreateListener(new ComicMessageListener(channelName));
		api.addMessageCreateListener(new ElmoMessageListener(channelName));
		api.addMessageCreateListener(new FactMessageListener(channelName));
		api.addMessageCreateListener(new CasinoGameListener(channelName));
		api.addMessageCreateListener(new HighLowListener(channelName));
		api.addMessageCreateListener(new PEMDASListener(channelName));
		api.addMessageCreateListener(new Ryland(channelName));
		api.addMessageCreateListener(new RockPaperScissorsListener(channelName));
		api.addMessageCreateListener(new leetMessageListener(channelName));
		api.addMessageCreateListener(new ConnectFour(channelName));
		api.addMessageCreateListener(new FlagMessageListener(channelName));
		api.addMessageCreateListener(new EightBall(channelName));
		api.addMessageCreateListener(new Reddit(channelName));
		api.addMessageCreateListener(new DeepFrier(channelName));
		api.addMessageCreateListener(new PictureOf(channelName));
		api.addMessageCreateListener(new GetPicture(channelName));
		api.addMessageCreateListener(new CuteAnimal(channelName));
		api.addMessageCreateListener(new Weather(channelName));
		api.addMessageCreateListener(new FashionAdvisor(channelName));
		api.addMessageCreateListener(new LatexRender(channelName));
		api.addMessageCreateListener(new MinesweeperListener(channelName));
		api.addMessageCreateListener(new NewPollMessageListener(channelName));
		api.addMessageCreateListener(new Bot1Listener(channelName));
		api.addMessageCreateListener(new QuitMessageListener(channelName));
		api.addMessageCreateListener(new PingMessageListener(channelName));	
		api.addMessageCreateListener(new CoinFlipMessageListener(channelName));
		api.addMessageCreateListener(new PlayRPSMessageListener(channelName));
		api.addMessageCreateListener(new KickMessageListener(channelName));
		api.addMessageCreateListener(new AssignRoleMessageListener(channelName));
		api.addMessageCreateListener(new NicknameListener(channelName));
		api.addMessageCreateListener(new SolveQuadraticListener(channelName));
		api.addMessageCreateListener(new RollDiceMessageListener(channelName));
		api.addMessageCreateListener(new HelpMessageListener(channelName));
		api.addMessageCreateListener(new MorseTranslator(channelName));
		api.addMessageCreateListener(new HangmanListener(channelName));
		api.addMessageCreateListener(new BogoSorterListener(channelName));
		api.addMessageCreateListener(new ComplimentListener(channelName));
		api.addMessageCreateListener(new FEHStatListener(channelName));
		api.addMessageCreateListener(new CrazyEights(channelName));
		api.addMessageCreateListener(new Blackjack(channelName));

	}

}
