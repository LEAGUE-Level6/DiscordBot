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
		
		ToDoList list = new ToDoList(channelName);
		api.addMessageCreateListener(list);
		helpListener.addHelpEmbed(list.getHelpEmbed());

		HeadlineListener head = new HeadlineListener(channelName);
		api.addMessageCreateListener(head);
		helpListener.addHelpEmbed(head.getHelpEmbed());

		RandomNumber randomNumber = new RandomNumber(channelName); //replace with feature class later
		api.addMessageCreateListener(randomNumber);
		helpListener.addHelpEmbed(randomNumber.getHelpEmbed());
		
		HypeMachine hypeMachine = new HypeMachine(channelName);
		api.addMessageCreateListener(hypeMachine);
		helpListener.addHelpEmbed(hypeMachine.getHelpEmbed());
		
		TextStyler textStyler = new TextStyler(channelName);
		api.addMessageCreateListener(textStyler);
		helpListener.addHelpEmbed(textStyler.getHelpEmbed());
		
		Tomagachi tomagachi = new Tomagachi(channelName);
		api.addMessageCreateListener(tomagachi);
		helpListener.addHelpEmbed(tomagachi.getHelpEmbed());
		
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
		
		NewWeather newWeather = new NewWeather(channelName);
		api.addMessageCreateListener(newWeather);
		helpListener.addHelpEmbed(newWeather.getHelpEmbed());
		
		CoinFlip cp = new CoinFlip(channelName);
		api.addMessageCreateListener(cp);
	
    RandomString randomString = new RandomString(channelName);
		api.addMessageCreateListener(randomString);
		helpListener.addHelpEmbed(randomString.getHelpEmbed());
		
		BitcoinListener bc = new BitcoinListener(channelName);
		api.addMessageCreateListener(bc);
		helpListener.addHelpEmbed(bc.getHelpEmbed());
		
		
	//	Greeter g = new Greeter(channelName);
//		api.addMessageCreateListener(g);
	//	helpListener.addHelpEmbed(g.getHelpEmbed());
		
//		ListMakerMessageListener LM = new ListMakerMessageListener(channelName);
//		api.addMessageCreateListener(LM);
//		helpListener.addHelpEmbed(LM.getHelpEmbed());
		
		Dice d = new Dice(channelName);
		api.addMessageCreateListener(d);
		helpListener.addHelpEmbed(d.getHelpEmbed());

	//	UnbeatableRockPaperScissors rps = new UnbeatableRockPaperScissors(channelName);
//		api.addMessageCreateListener(rps);
//		helpListener.addHelpEmbed(rps.getHelpEmbed());
		
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

	}
}
