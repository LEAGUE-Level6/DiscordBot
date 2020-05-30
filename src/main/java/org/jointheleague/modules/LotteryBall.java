package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import net.aksingh.owmjapis.api.APIException;

public class LotteryBall extends CustomMessageCreateListener {
	
	int tokens = 5;
	int[] numbers;
	int betAmount;
	Random rand;
	int[] tokenNumbers;
	
	public LotteryBall(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().equalsIgnoreCase("!lotteryball")) {
			event.getChannel().sendMessage("Let's play a round of Nephry Ball! ╰( ･ ᗜ ･ )╯");
			event.getChannel().sendMessage("To start, I'll give you 5 tokens.");
			event.getChannel().sendMessage("You'll pick five different numbers from 1 to 30 to bet on. Then I'll choose five out of the 30 too!");
			event.getChannel().sendMessage("For each ball that you pick that is also one that I picked, your bet will double!");
			event.getChannel().sendMessage("But if you don't pick one that I picked, you'll lose your tokens!");
			event.getChannel().sendMessage("If you're ready to play, type \"!lotteryball pick\" and type your choices afterwards!"
					+ "\nPlease separate your numbers with commas, so I can read them. Thanks! (◍•ᴗ•◍)");
		} else if (event.getMessageContent().equalsIgnoreCase("!lotteryball tokens")){
			event.getChannel().sendMessage("( ﾟ▽ﾟ)/ You have " + tokens + " tokens!");
		} else if (event.getMessageContent().contains("!lotteryball pick")) {
			String numbersRaw = event.getMessageContent().substring(18);
			String[] numPicks = numbersRaw.split(",");
			numbers = new int[numPicks.length];
			for (int i = 0; i < numbers.length; i++) {
				numPicks[i] = numPicks[i].trim();
				numbers[i] = Integer.parseInt(numPicks[i]);
			}
			boolean allDifferent = true;
			boolean fiveNums = true;
			boolean inRange = true;
			//five numbers?
			if (numbers.length != 5) {
				fiveNums = false;
			}
			//all different?
			for (int i = 0; i < numbers.length; i++) {
				for (int j = 0; j < numbers.length; j++) {
					if (!(i == j)) {
						if (numbers[i] == numbers[j]) {
							allDifferent = false;
						}
					}
				}
			}
			//30 or below?
			for (int i: numbers) {
				if (i > 30) {
					inRange = false;
				}
			}
			if (allDifferent && fiveNums && inRange){
				String s = "";
				for (int i = 0; i < 5; i++) {
					s += numbers[i];
					if (i < 4) {
						s += ", ";
					}
				}
				event.getChannel().sendMessage("♪(๑ᴖ◡ᴖ๑)♪ Okay! You've picked these numbers: " + s);
				event.getChannel().sendMessage("If you're ready, type \"!lotteryball bet\" and type the amount of tokens you want to bet afterwards."
						+ "\nPlease just type a number afterwards!");
			} else {
				event.getChannel().sendMessage("Please choose 5 different numbers under 30! (｡•́︿•̀｡)");
			}
		} else if (event.getMessageContent().contains("!lotteryball bet") && !event.getMessageAuthor().isYourself()) {
			String betRaw = event.getMessageContent().substring(17);
			betAmount = Integer.parseInt(betRaw);
			if (numbers == null) {
				event.getChannel().sendMessage("Please pick numbers to bet on first! ( •᷄⌓•᷅ )");
			} else if (betAmount > tokens) {
				event.getChannel().sendMessage("You don't have that many tokens! (●´⌓`●)");
			} else if (betAmount < 1){
				event.getChannel().sendMessage("Please bet at least 1 token! (oﾟ□ﾟ)o");
			} else {
				tokens -= betAmount;
				event.getChannel().sendMessage("(･ัᗜ･ั)و Okay! You're betting this amount of tokens: " + betAmount);
				event.getChannel().sendMessage("If you're ready, type \"!lotteryball start\" and we'll start the game! ヽ(´∀｀ヽ)");
			}
		} else if (event.getMessageContent().equalsIgnoreCase("!lotteryball start")) {
			if (numbers == null) {
				event.getChannel().sendMessage("Please pick numbers to bet on first! ( •᷄⌓•᷅ )");
			} else if (betAmount == 0) {
				event.getChannel().sendMessage("Please bet some tokens first! (꒪⌓꒪)");
			} else {
				rand = new Random();
				tokenNumbers = new int[5];
				event.getChannel().sendMessage("Let's start! I'll pick numbers now...");
				for (int i = 0; i < 5; i++) {
					int pick = 0;
					while (pick == 0) {
						pick = rand.nextInt(31);
					}
					tokenNumbers[i] = pick;
				}
				String s = "";
				for (int i = 0; i < 5; i++) {
					s += tokenNumbers[i];
					if (i < 4) {
						s += ", ";
					}
				}
				event.getChannel().sendMessage("Here are the numbers I picked: " + s);
				int matching = 0;
				for (int compPick: tokenNumbers) {
					for (int playerPick: numbers) {
						if (compPick == playerPick) {
							matching++;
						}
					}
				}
				if (matching == 0) {
					event.getChannel().sendMessage("ヽ(´□｀。)ﾉ Oh no! Seems like we didn't pick any of the same numbers.");
					tokens -= betAmount;
				} else {
					if (matching == 5) {
						event.getChannel().sendMessage("（＊〇□〇）Whoa! We picked the same exact numbers!");
					} else {
						event.getChannel().sendMessage("(۶•̀ᴗ•́)۶ Cool! We picked " + matching + " of the same numbers!");
					}
					tokens += betAmount * (2*matching);
				}
				event.getChannel().sendMessage("You have " + tokens + " tokens now!");
				event.getChannel().sendMessage("If you want to try again, just pick new numbers with \"!lotteryball pick\"!");
			}
		}
	}
}
