package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class HeadlineListener extends CustomMessageCreateListener{
	boolean hasGuessed;
	int r = new Random().nextInt(2);
	public HeadlineListener(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed("Welcome to: Real vs Fake; NEWS HEADLINE EDITION!", "Here is how it works: \n You will be presented with a headline; it can be real or fake \n Type '!guess REAL' if you think it's real and '!guess FAKE' if you think it's fake \n Commands: \n '!starthl' = start game \n '!guess REAL' or '!guess FAKE' = for guessing real or fake");
	}
	
	@Override
	public void handle(MessageCreateEvent event) {
	if(event.getMessageContent().toLowerCase().startsWith("!starthl")) {
		hasGuessed=false;
		event.getChannel().sendMessage("Here is your headline:");
		int headlinenum = new Random().nextInt(32);
		String headline = "";
		if(r == 0) {
			try {
				BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/jointheleague/modules/falseheadlines"));
				for(int i = 0; i<headlinenum;i++) {
					headline = br.readLine();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		event.getChannel().sendMessage(headline);
		}else {
			try {
				BufferedReader br2 = new BufferedReader(new FileReader("src/main/java/org/jointheleague/modules/trueheadlines"));
				for(int i = 0; i<headlinenum;i++) {
					headline = br2.readLine();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		event.getChannel().sendMessage(headline);
		}
	}
	if(event.getMessageContent().toLowerCase().startsWith("!guess")&& hasGuessed == false) {
		String guess = event.getMessageContent().substring(7,event.getMessageContent().length());
		if(!guess.toLowerCase().equals("real") && !guess.toLowerCase().equals("fake")) {
			event.getChannel().sendMessage("ENTER GUESS IN FORMAT '!guess REAL' OR '!guess FAKE' (capitlization not essential)");
		}else {
			if(guess.toLowerCase().contentEquals("real")) {
				if(r == 1) {
					event.getChannel().sendMessage("You got it!");
					hasGuessed = true;
				}
				if(r == 0) {
					event.getChannel().sendMessage("Wrong lol");
					hasGuessed=true;
				}
			}else {
				if(r == 1) {
					event.getChannel().sendMessage("Wrong lol");
					hasGuessed=true;
				}
				if(r == 0) {
					event.getChannel().sendMessage("You got it!");
					hasGuessed=true;
				}
			}
		}
	}
	}
}
