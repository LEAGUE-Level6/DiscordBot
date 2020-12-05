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
	boolean reach1000 = false;

	// Inspired by the casino minigame from Tales of the Abyss

	public LotteryBall(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().equals("test")) {
			event.getChannel().sendMessage(event.getMessage().getAuthor().getName());
		}
		if (event.getMessageContent().equalsIgnoreCase("!nball")) {
			event.getChannel().sendMessage("Let's play a round of Nephry Ball!");
			event.getChannel().sendMessage("( ´ ▽ ` )ﾉ Here's a list of commands for this game:"
					+ "\n\"!nball new\" will tell you the rules and start a new game."
					+ "\n\"!nball tokens\" will tell you how many tokens you have, and what the high score is."
					+ "\n\"!nball pick\" is how you'll pick numbers to guess. You can also start a new round by typing this instead of \"!nball\" if you don't want to see the rules again."
					+ "\n\"!nball bet\" is how you'll bet on your numbers (using your tokens!). You can bet all of your tokens by typing \"all\" afterwards instead of a number."
					+ "\n\"!nball start\" is when I'll pick my numbers. Typing this also finalizes your guesses and bet amount."
					+ "\n\"!nball reset\" will reset the save file's high score.");
			event.getChannel().sendMessage("I hope you'll have fun! (*･▽･*)");
		} else if (event.getMessageContent().equalsIgnoreCase("!nball new")) {
			loadData(event.getMessage().getAuthor().getName());
			if (tokens == 0) {
				tokens = 5;
				saveDataTest(event.getMessage().getAuthor().getName());
			}
			actualZero = false;
			betAmount = 0;
			numbers = new int[5];
			event.getChannel().sendMessage("Let's play a round of Nephry Ball! ╰( ･ ᗜ ･ )╯");
			event.getChannel().sendMessage("I'll give you 5 tokens to start.");
			event.getChannel().sendMessage("You'll pick five different numbers from 1 to 35 to bet on. Then I'll choose five out of the 35 too!");
			event.getChannel().sendMessage("For each ball that you pick that is also one that I picked, your bet will double, but if you don't pick a ball that I picked, you'll lose your tokens!");
			event.getChannel()
					.sendMessage("If you're ready to play, type \"!nball pick\" and type your choices afterwards!"
							+ "\nPlease separate your numbers with commas, so I can read them. Thanks! (◍•ᴗ•◍)");
			event.getChannel().sendMessage("You can type \"!nball tokens\" at any time to see your token count, or \"!nball reset\" to reset the save file.");
		} else if (event.getMessageContent().equalsIgnoreCase("!nball tokens")) {
			loadData(event.getMessage().getAuthor().getName());
			event.getChannel().sendMessage("( ﾟ▽ﾟ)/ You have " + tokens + " tokens!");
			event.getChannel().sendMessage("Your high score is " + highScore + " tokens.");
		} else if (tokens <= 0 && actualZero) {
			event.getChannel().sendMessage("Oh no! You ran out of tokens.");
			event.getChannel().sendMessage("If you want to play again, just type \"!nball new\" again and the game will reset!");
			actualZero = false;
		} else if (event.getMessageContent().startsWith("!nball pick")) {
			loadData(event.getMessage().getAuthor().getName());
			betAmount = 0;
			numbers = new int[5];
			if (tokens <= 0) {
				event.getChannel().sendMessage("( ´△｀) You don't have any tokens! \nPlease restart the game by typing \"!nball new\".");
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
					if (i > 35 || i < 1) {
						inRange = false;
					}
				}
				if (allDifferent && fiveNums && inRange) {
					numbers = sort(numbers);
					String s = "";
					for (int i = 0; i < 5; i++) {
						s += numbers[i];
						if (i < 4) {
							s += ", ";
						}
					}
					event.getChannel().sendMessage("♪(๑ᴖ◡ᴖ๑)♪ Okay! You've picked these numbers: " + s);
					event.getChannel().sendMessage("If you're ready, type \"!nball bet\" and type the number of tokens you want to bet afterwards.");
				} else {
					event.getChannel().sendMessage("Please choose 5 different numbers under 35! (｡•́︿•̀｡)");
				}
			}
		} else if (event.getMessageContent().startsWith("!nball bet")) {
			if (tokens <= 0 && actualZero) {
				event.getChannel().sendMessage(
						"( ´△｀) You don't have any tokens! \nPlease restart the game by typing \"!nball new\".");
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
				if (numbers == null || (numbers[0] == 0)) {
					event.getChannel().sendMessage("Please pick numbers to bet on first! ( •᷄⌓•᷅ )");
				} else if (betAmount > tokens) {
					event.getChannel().sendMessage("You don't have that many tokens! (●´⌓`●)");
					tokens -= betAmount;
				} else if (betAmount < 1) {
					event.getChannel().sendMessage("Please bet at least 1 token! (oﾟ□ﾟ)o");
					tokens -= betAmount;
				} else {
					tokens -= betAmount;
					event.getChannel().sendMessage("(･ัᗜ･ั)و Okay! You're betting this amount of tokens: " + betAmount);
					event.getChannel().sendMessage("If you're ready, type \"!nball start\" and we'll start the game! ヽ(´∀｀ヽ)");
				}
			}
		} else if (event.getMessageContent().equalsIgnoreCase("!nball start")) {
			if (numbers == null) {
				event.getChannel().sendMessage("Please pick numbers to bet on first! ( •᷄⌓•᷅ )");
			} else if (betAmount == 0) {
				event.getChannel().sendMessage("Please bet some tokens first! (꒪⌓꒪)");
			} else if (tokens <= 0 && actualZero) {
				event.getChannel().sendMessage("( ´△｀) You don't have any tokens! \nPlease restart the game by typing \"!nball new\".");
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
					event.getChannel().sendMessage("ヽ(´□｀。)ﾉ Oh no! Seems like we didn't pick any of the same numbers.");
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
				} else {
					if (tokens >= 1000 && !reach1000) {
						reach1000 = true;
						event.getChannel().sendMessage("Wow, that's a lot of tokens! Have a cookie. :cookie:");
					}
					event.getChannel().sendMessage("If you want to play again, just pick new numbers with \"!nball pick\"!");
					numbers = new int[5];
				}
				saveDataTest(event.getMessage().getAuthor().getName());
			}
		}
	}

	public void loadData(String name) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/LotteryBallSave.txt"));
			String line = "";
			String allData = "";
			while (line != null) {
				line = br.readLine();
				if (line != null) {
					allData += line;
				}
			}
			br.close();
			int nameIndex = allData.lastIndexOf(name);
			if (nameIndex != (-1)) {
				String nameSave = allData.substring(nameIndex + 1);
				String[] saves = nameSave.split("=");
				tokens = Integer.parseInt(saves[1]);
				highScore = Integer.parseInt(saves[2]);
			} else {
				tokens = 5;
				highScore = 5;
			}
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
		} catch (NullPointerException e) {
			tokens = 5;
			saveDataTest(name);
		}

	}

	public void saveDataTest(String name) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/LotteryBallSave.txt"));
			String line = "";
			String allData = "";
			while (line != null) {
				line = br.readLine();
				if (line != null) {
					allData += line;
				}
			}
			br.close();
			FileWriter fw = new FileWriter("src/main/resources/LotteryBallSave.txt");
			int nameIndex = allData.lastIndexOf(name);
			if (nameIndex != (-1)) {
				String nameSave = allData.substring(nameIndex + 1);
				String[] saves = nameSave.split("=");
				if (tokens > Integer.parseInt(saves[2])) {
					fw.write(allData + "\n" + name + "=" + tokens + "=" + tokens + "\n");
				} else {
					fw.write(allData + "\n" + name + "=" + tokens + "=" + Integer.parseInt(saves[2]) + "\n");
				}
			} else {
				fw.write(allData + "\n" + name + "=" + tokens + "=" + tokens + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[] sort(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			int index = i;
			for (int j = (i + 1); j < array.length; j++) {
				if (array[j] < array[index]) {
					index = j;
				}
				int tempHolder = array[index];
				array[index] = array[i];
				array[i] = tempHolder;
			}
		}
		return array;
	}
}
