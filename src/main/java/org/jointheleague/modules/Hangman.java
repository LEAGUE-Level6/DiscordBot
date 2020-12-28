package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Hangman extends CustomMessageCreateListener{
	
	private static final String STARTGAME = "&hangman";
	private static final String GUESSCHAR = "&guess";
	static boolean gameInProgress = false;
	static String word = "";
	static String remaining = "";
	static String guessed = "";
	static String display = "";
	static int lives = 6;
	
	public Hangman(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
		if(event.getMessageContent().contains(STARTGAME)) {
			String mode = event.getMessageContent().split(" ")[1];
			//Start Game
			if(gameInProgress) {
				event.getChannel().sendMessage("There is already a game in progress!");
			}else {
				reset();
				gameInProgress = true;
				Random r = new Random();
				BufferedReader brCount = null;
				BufferedReader br = null;
				System.out.println(mode);
				if(mode.equalsIgnoreCase("easy")) {
					try {
						brCount = new BufferedReader(new FileReader("/Users/THEHANS/Desktop/DiscordBot/src/main/java/org/jointheleague/modules/hangmanEasy.txt"));
						br = new BufferedReader(new FileReader("/Users/THEHANS/Desktop/DiscordBot/src/main/java/org/jointheleague/modules/hangmanEasy.txt"));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(mode.equalsIgnoreCase("medium")) {
					try {
						brCount = new BufferedReader(new FileReader("/Users/THEHANS/Desktop/DiscordBot/src/main/java/org/jointheleague/modules/hangmanMedium.txt"));
						br = new BufferedReader(new FileReader("/Users/THEHANS/Desktop/DiscordBot/src/main/java/org/jointheleague/modules/hangmanMedium.txt"));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(mode.equalsIgnoreCase("hard")) {
					try {
						brCount = new BufferedReader(new FileReader("/Users/THEHANS/Desktop/DiscordBot/src/main/java/org/jointheleague/modules/hangmanHard.txt"));
						br = new BufferedReader(new FileReader("/Users/THEHANS/Desktop/DiscordBot/src/main/java/org/jointheleague/modules/hangmanHard.txt"));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				int lineNumber = r.nextInt((int) brCount.lines().count()) + 1;
				System.out.println(lineNumber);
				try {
					String line = "";
					for(int i = 0; i < lineNumber; i++) {
						line = br.readLine();
					}
					word = line;
					remaining = word;
					System.out.println(remaining);
					br.close();
					event.getChannel().sendMessage("Game started!");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//Add Character
		if(event.getMessageContent().contains("&add")) {
			String add = event.getMessageContent().split(" ")[1];
			FileWriter fw = null;
			if(add.length()<3) {
				event.getChannel().sendMessage("Word is too short!");
			}else if(add.length()>10) {
				event.getChannel().sendMessage("Word is too long!");
			}else if(add.length() > 3 && add.length() < 6) {
				try {
					fw = new FileWriter("/discord_bot_example/src/main/java/org/jointheleague/modules/hangmanEasy.txt", true);
					fw.write("\n" + add);
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				event.getChannel().sendMessage("Word " + add + " added.");
			}else if(add.length() > 5 && add.length() < 8) {
				try {
					fw = new FileWriter("/discord_bot_example/src/main/java/org/jointheleague/modules/hangmanMedium.txt", true);
					fw.write("\n" + add);
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				event.getChannel().sendMessage("Word " + add + " added.");
			}else {
				try {
					fw = new FileWriter("/discord_bot_example/src/main/java/org/jointheleague/modules/hangmanHard.txt", true);
					fw.write("\n" + add);
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				event.getChannel().sendMessage("Word " + add + " added.");
			}
		}
		
		//Guess
		if(event.getMessageContent().contains(GUESSCHAR) && gameInProgress) {
			char guess = event.getMessageContent().charAt(event.getMessageContent().length()-1);
			if(guessed.contains(guess + "")) {
				event.getChannel().sendMessage("You already guessed this character!");
			}else {
				guessed += guess;
				if(remaining.contains(guess + "")) {
					remaining = remove(remaining, guess);
				}else {
					lives--;
				}
				displayHangman(event, lives);
				display = "";
				for(int i = 0; i < word.length(); i++) {
					if(guessed.contains(word.charAt(i) + "")) {
						display += word.charAt(i);
					}else {
						display += "_";
					}
					display += " ";
				}
				new MessageBuilder().appendCode("java", display).send(event.getChannel());
				event.getChannel().sendMessage("Guessed characters: " + guessed);
				event.getChannel().sendMessage("Lives: " + lives);
				if(lives == 0) {
					event.getChannel().sendMessage("You lost! The word was " + word + ".");
					reset();
				}
				if(remaining.length() == 0 && gameInProgress) {
					event.getChannel().sendMessage("You won with " + lives + " lives left.");
					reset();
				}
			}
		}
		
		if(event.getMessageContent().contains("&giveup") && gameInProgress) {
			event.getChannel().sendMessage("You've given up! The word was " + word + ".");
			reset();
		}
	}
	
	public static String remove(String s, char c) {
		String str = "";
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != c) {
				str += s.charAt(i);
			}
		}
		return str;
	}
	
	public static void displayHangman(MessageCreateEvent event, int lives) {
		String s = "";
		if(lives == 6) {
			s = "-------–––––––––––––––\n" + 
					"        |                             |\n" + 
					"        |                            O\n" + 
					"        |                         -–|–-\n" + 
					"        |                          / \\\n" + 
					"        |\n" + 
					"–––––––––";
		}else if(lives == 5) {
			s = "-------–––––––––––––––\n" + 
					"        |                             |\n" + 
					"        |                            O\n" + 
					"        |                         -–|–-\n" + 
					"        |                             \\\n" + 
					"        |\n" + 
					"–––––––––";
		}else if(lives == 4) {
			s = "-------–––––––––––––––\n" + 
					"        |                             |\n" + 
					"        |                            O\n" + 
					"        |                         -–|–-\n" + 
					"        |\n" + 
					"        |\n" + 
					"–––––––––";
		}else if(lives == 3) {
			s = "-------–––––––––––––––\n" + 
					"        |                             |\n" + 
					"        |                            O\n" + 
					"        |                             |–-\n" + 
					"        |\n" + 
					"        |\n" + 
					"–––––––––";
		}else if(lives == 2) {
			s = "-------–––––––––––––––\n" + 
					"        |                             |\n" + 
					"        |                            O\n" + 
					"        |                             |\n" + 
					"        |\n" + 
					"        |\n" + 
					"–––––––––";
		}else if(lives == 1) {
			s = "-------–––––––––––––––\n" + 
					"        |                             |\n" + 
					"        |                            O\n" + 
					"        |\n" + 
					"        |\n" + 
					"        |\n" + 
					"–––––––––";
		}else {
			s = "-------–––––––––––––––\n" + 
					"        |                             |\n" + 
					"        |\n" + 
					"        |\n" + 
					"        |\n" + 
					"        |\n" + 
					"–––––––––";
		}
		event.getChannel().sendMessage(s);
	}
	
	public static void reset() {
		gameInProgress = false;
		word = "";
		remaining = "";
		guessed = "";
		display = "";
		lives = 6;
	}
}
