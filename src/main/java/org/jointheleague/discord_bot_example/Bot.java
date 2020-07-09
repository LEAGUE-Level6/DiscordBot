package org.jointheleague.discord_bot_example;

import org.javacord.api.DiscordApi;

import org.javacord.api.DiscordApiBuilder;

/**
 * Launches all of the listeners for one channel.
 * @author keithgroves and https://tinystripz.com
 *
 */
import org.jointheleague.modules.*;



public class Bot  {
	
	// The string to show the custom :vomiting_robot: emoji
	public static String emoji = "<:vomiting_robot:642414033290657803>";

	private String token;
	private String channelName;
	DiscordApi api;
	_HelpListener helpListener;

	public Bot(String token, String channelName) {
		this.token = token;
		this.channelName = channelName;
		helpListener = new _HelpListener(channelName);
	}

	public void connect(boolean printInvite) {
		
		api = new DiscordApiBuilder().setToken(token).login().join();

		// Print the URL to invite the bot
		if (printInvite) {
			System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
		}

		api.getServerTextChannelsByName(channelName).forEach(e -> e.sendMessage("Bot Connected"));
		
		//add Listeners

		HeadlineListener head = new HeadlineListener(channelName);
		api.addMessageCreateListener(head);
		helpListener.addHelpEmbed(head.getHelpEmbed());

		RandomNumber randomNumber = new RandomNumber(channelName);
		api.addMessageCreateListener(randomNumber);
		helpListener.addHelpEmbed(randomNumber.getHelpEmbed());
		
		SetProfilePic setPFP = new SetProfilePic(channelName);
		api.addMessageCreateListener(setPFP);
		helpListener.addHelpEmbed(setPFP.getHelpEmbed());

		ToGif toGif = new ToGif(channelName);
		api.addMessageCreateListener(toGif);
		helpListener.addHelpEmbed(toGif.getHelpEmbed());
		
		RandomCase randomCase = new RandomCase(channelName);
		api.addMessageCreateListener(randomCase);
		helpListener.addHelpEmbed(randomCase.getHelpEmbed());
		
		_ApiExampleListener apiExampleListener = new _ApiExampleListener(channelName);
		api.addMessageCreateListener(apiExampleListener);
		helpListener.addHelpEmbed(apiExampleListener.getHelpEmbed());
		
		FindNBAPlayer nbaPlayerListener = new FindNBAPlayer(channelName);
		api.addMessageCreateListener(nbaPlayerListener);
		helpListener.addHelpEmbed(nbaPlayerListener.getHelpEmbed());
		
		//old way to add listeners 
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
		api.addMessageCreateListener(new Bot1Listener(channelName));
		api.addMessageCreateListener(new PingMessageListener(channelName));	
		api.addMessageCreateListener(new CoinFlipMessageListener(channelName));
		api.addMessageCreateListener(new PlayRPSMessageListener(channelName));
		api.addMessageCreateListener(new KickMessageListener(channelName));
		api.addMessageCreateListener(new AssignRoleMessageListener(channelName));
		api.addMessageCreateListener(new NicknameListener(channelName));
		api.addMessageCreateListener(new SolveQuadraticListener(channelName));
		api.addMessageCreateListener(new RollDiceMessageListener(channelName));
		api.addMessageCreateListener(new MorseTranslator(channelName));
		api.addMessageCreateListener(new HangmanListener(channelName));
		api.addMessageCreateListener(new BogoSorterListener(channelName));
		api.addMessageCreateListener(new ComplimentListener(channelName));
		api.addMessageCreateListener(new CrazyEights(channelName));
		api.addMessageCreateListener(new Blackjack(channelName));
		api.addMessageCreateListener(new RandomCase(channelName));
		api.addMessageCreateListener(new GetTime(channelName));
		api.addMessageCreateListener(new ScreenCapture(channelName));
		api.addMessageCreateListener(new MorseCodeTranslator(channelName));
		api.addMessageCreateListener(new SearchTopResult(channelName));


	}
}
