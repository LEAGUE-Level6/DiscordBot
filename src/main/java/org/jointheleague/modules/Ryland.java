package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class Ryland extends CustomMessageCreateListener {

	private static final String COMMAND = "!vowelString";

	public Ryland(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace(COMMAND,"");
			
			if(cmd.equals("")) {
				
				
				event.getChannel().sendMessage("Please put a string after the command!");
				
				
			} else {
				String newString = "";
				for (int i = 0; i < cmd.length(); i++) {
					if(cmd.charAt(i)=='a'||cmd.charAt(i)=='e'||cmd.charAt(i)=='i'||cmd.charAt(i)=='o'||cmd.charAt(i)=='u') {
						newString+=cmd.charAt(i);
					}
				}
				event.getChannel().sendMessage("The string with only vowels is: "+ newString);
				
			}
			
		}
	}
	
}