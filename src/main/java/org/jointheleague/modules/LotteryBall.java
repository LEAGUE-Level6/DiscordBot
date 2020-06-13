package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import net.aksingh.owmjapis.api.APIException;

public class LotteryBall extends CustomMessageCreateListener {

	int tokens;
	int highScore;
	int[] numbers;
	int betAmount = 0;
	Random rand;
	int[] tokenNumbers;
	boolean actualZero;

	public LotteryBall(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().equalsIgnoreCase("!nball")) {
			loadData();
			if (tokens == 0) {
				tokens = 5;
				saveData();
			}
			actualZero = false;
			betAmount = 0;
			event.getChannel().sendMessage("Let's play a round of Nephry Ball! ╰( ･ ᗜ ･ )╯");
			event.getChannel().sendMessage("If you haven't played before, I'll give you 5 tokens to start.");
			event.getChannel().sendMessage(
					"You'll pick five different numbers from 1 to 35 to bet on. Then I'll choose five out of the 35 too!");
			event.getChannel().sendMessage(
					"For each ball that you pick that is also one that I picked, your bet will double, but if you don't pick a ball that I picked, you'll lose your tokens!");
			event.getChannel()
					.sendMessage("If you're ready to play, type \"!nball pick\" and type your choices afterwards!"
							+ "\nPlease separate your numbers with commas, so I can read them. Thanks! (◍•ᴗ•◍)");
			event.getChannel().sendMessage("You can type \"!nball tokens\" at any time to see your token count.");
		} else if (event.getMessageContent().equalsIgnoreCase("!nball tokens")) {
			loadData();
			event.getChannel().sendMessage("( ﾟ▽ﾟ)/ You have " + tokens + " tokens!");
			event.getChannel().sendMessage("Seems like the high score is " + highScore + " tokens.");
		} else if (event.getMessageContent().equalsIgnoreCase("!nball reset")) { 
			event.getChannel().sendMessage("Ok, resetting save...");
			reset();
			event.getChannel().sendMessage("All clear!");
		} else if (tokens <= 0 && actualZero) {
			event.getChannel().sendMessage("Oh no! You ran out of tokens.");
			event.getChannel()
					.sendMessage("If you want to play again, just type \"!nball\" again and the game will reset!");
			actualZero = false;
		} else if (event.getMessageContent().startsWith("!nball pick")) {
			loadData();
			betAmount = 0;
			if (tokens <= 0) {
				event.getChannel().sendMessage(
						"( ´△｀) You don't have any tokens! \nPlease restart the game by typing \"!nball\".");
			} else {
				String numbersRaw = event.getMessageContent().substring(12);
				numbersRaw = numbersRaw.trim();
				String[] numPicks = numbersRaw.split(",");
				numbers = new int[numPicks.length];
				for (int i = 0; i < numbers.length; i++) {
					numPicks[i] = numPicks[i].trim();
					numbers[i] = Integer.parseInt(numPicks[i]);
				}
				boolean allDifferent = true;
				boolean fiveNums = true;
				boolean inRange = true;
				// five numbers?
				if (numbers.length != 5) {
					fiveNums = false;
				}
				// all different?
				for (int i = 0; i < numbers.length; i++) {
					for (int j = 0; j < numbers.length; j++) {
						if (!(i == j)) {
							if (numbers[i] == numbers[j]) {
								allDifferent = false;
							}
						}
					}
				}
				// 35 or below?
				for (int i : numbers) {
					if (i > 35) {
						inRange = false;
					}
				}
				if (allDifferent && fiveNums && inRange) {
					String s = "";
					for (int i = 0; i < 5; i++) {
						s += numbers[i];
						if (i < 4) {
							s += ", ";
						}
					}
					event.getChannel().sendMessage("♪(๑ᴖ◡ᴖ๑)♪ Okay! You've picked these numbers: " + s);
					event.getChannel().sendMessage(
							"If you're ready, type \"!nball bet\" and type the number of tokens you want to bet afterwards.");
				} else {
					event.getChannel().sendMessage("Please choose 5 different numbers under 35! (｡•́︿•̀｡)");
				}
			}
		} else if (event.getMessageContent().startsWith("!nball bet")) {
			if (tokens <= 0 && actualZero) {
				event.getChannel().sendMessage(
						"( ´△｀) You don't have any tokens! \nPlease restart the game by typing \"!nball\".");
			} else {
				actualZero = false;
				tokens += betAmount;
				String betRaw = event.getMessageContent().substring(10);
				betRaw = betRaw.trim();
				if (betRaw.contains("all")) {
					betAmount = tokens;
				} else {
					betAmount = Integer.parseInt(betRaw);
				}
				if (numbers == null) {
					event.getChannel().sendMessage("Please pick numbers to bet on first! ( •᷄⌓•᷅ )");
				} else if (betAmount > tokens) {
					event.getChannel().sendMessage("You don't have that many tokens! (●´⌓`●)");
				} else if (betAmount < 1) {
					event.getChannel().sendMessage("Please bet at least 1 token! (oﾟ□ﾟ)o");
				} else {
					tokens -= betAmount;
					event.getChannel().sendMessage("(･ัᗜ･ั)و Okay! You're betting this amount of tokens: " + betAmount);
					event.getChannel()
							.sendMessage("If you're ready, type \"!nball start\" and we'll start the game! ヽ(´∀｀ヽ)");
				}

			}
		} else if (event.getMessageContent().equalsIgnoreCase("!nball start")) {
			if (numbers == null) {
				event.getChannel().sendMessage("Please pick numbers to bet on first! ( •᷄⌓•᷅ )");
			} else if (betAmount == 0) {
				event.getChannel().sendMessage("Please bet some tokens first! (꒪⌓꒪)");
			} else if (tokens <= 0 && actualZero) {
				event.getChannel().sendMessage(
						"( ´△｀) You don't have any tokens! \nPlease restart the game by typing \"!nball\".");
			} else {
				rand = new Random();
				tokenNumbers = new int[5];
				event.getChannel().sendMessage("Let's start! I'll pick numbers now...");
				for (int i = 0; i < 5;) {
					int pick = 0;
					boolean contains = false;
					while (pick == 0) {
						pick = rand.nextInt(36);
					}
					for (int token : tokenNumbers) {
						if (token == pick) {
							contains = true;
						}
					}
					if (!contains) {
						tokenNumbers[i] = pick;
						i++;
					}
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
				for (int compPick : tokenNumbers) {
					for (int playerPick : numbers) {
						if (compPick == playerPick) {
							matching++;
						}
					}
				}
				if (matching == 0) {
					event.getChannel()
							.sendMessage("ヽ(´□｀。)ﾉ Oh no! Seems like we didn't pick any of the same numbers.");
				} else {
					if (matching == 5) {
						event.getChannel().sendMessage("（＊〇□〇）Whoa! We picked the same exact numbers!");
					} else {
						event.getChannel().sendMessage("(۶•̀ᴗ•́)۶ Cool! We have " + matching + " number(s) in common!");
					}
					tokens += betAmount * (2 * matching);
				}
				event.getChannel().sendMessage("You have " + tokens + " token(s) now!");
				if (tokens == 0) {
					actualZero = true;
				} else if (tokens >= 1000) {
					event.getChannel().sendMessage("Wow, that's a lot of tokens! Have a virtual cookie.");
				} else {
					event.getChannel()
							.sendMessage("If you want to play again, just pick new numbers with \"!nball pick\"!");
				}
				saveData();
			}
		}
	}

	public void loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/LotteryBallSave.txt"));
			String saveData = br.readLine();
			String saveData2 = br.readLine();
			int saveDataNum = Integer.parseInt(saveData);
			int highScoreNum = Integer.parseInt(saveData2);
			tokens = saveDataNum;
			highScore = highScoreNum;
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			tokens = 5;
		} catch (StringIndexOutOfBoundsException e) {
			tokens = 5;
		}

	}

	public void saveData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/LotteryBallSave.txt"));
			String saveData = br.readLine();
			String saveData2 = br.readLine();
			int highScoreNum = Integer.parseInt(saveData2);
			br.close();
			FileWriter fw = new FileWriter("src/main/resources/LotteryBallSave.txt");
			if (tokens > highScoreNum) {
				fw.write(tokens + "\n" + tokens);
			} else {
				fw.write(tokens + "\n" + highScoreNum);
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reset() {
		FileWriter fw;
		try {
			fw = new FileWriter("src/main/resources/LotteryBallSave.txt");
			fw.write(tokens + "\n5");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
