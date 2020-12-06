package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class RandomWords extends CustomMessageCreateListener {
	private static final String COMMAND = "!word";
	String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\org\\jointheleague\\modules\\words.txt";
	File file = new File(filePath);
	String[] words;

	public RandomWords(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"!word - sends a random word \n !word <letter> sends a random word with the first letter matching what was put in the command");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains(COMMAND)) {
			String[] phrase = event.getMessageContent().split(" ");
			if (phrase.length > 2) {
				event.getChannel().sendMessage("Incorrect usage of the command. Correct usages: !word, !word <letter>");
			} else if (phrase.length < 2) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line;
					int i = 0;
					while ((line = br.readLine()) != null) {
						i++;
					}
					br.close();
					words = new String[i];
					br = new BufferedReader(new FileReader(file));
					String line2;
					i = 0;
					while ((line2 = br.readLine()) != null) {
						words[i] = line2;
						i++;
					}
					event.getChannel().sendMessage(words[new Random().nextInt(words.length) - 1]);
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				String letter1 = phrase[1];
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line;
					int i = 0;
					while ((line = br.readLine()) != null) {
						String letter2 = line.substring(0, 1);
						if(letter2.equals(letter1)) {
							i++;
						}
					}
					br.close();
					words = new String[i];
					br = new BufferedReader(new FileReader(file));
					String line2;
					i = 0;
					while ((line2 = br.readLine()) != null) {
						String letter2 = line2.substring(0, 1);
						if (letter2.equals(letter1)) {
							words[i] = line2;
							i++;
						}
					}
					event.getChannel().sendMessage(words[new Random().nextInt(words.length) - 1]);
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
