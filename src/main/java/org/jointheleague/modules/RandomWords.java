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
	BufferedReader br = null;
	String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\org\\jointheleague\\modules\\words";
	File file = new File(filePath);
	ArrayList<String> words = new ArrayList<String>();

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
					br = new BufferedReader(new FileReader(file));
					String line;
					while ((line = br.readLine()) != null) {
						words.add(line);
					}
					event.getChannel().sendMessage(words.get(new Random().nextInt(words.size()-1)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				
			}
		}
	}
}
