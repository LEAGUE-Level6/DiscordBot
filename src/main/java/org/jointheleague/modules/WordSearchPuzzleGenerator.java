package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;


public class WordSearchPuzzleGenerator extends CustomMessageCreateListener{

	public static final String COMMAND = "!generate word search puzzle:";
	public WordSearchPuzzleGenerator(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Creates a 10 x 10 random word search puzzle with the words you want");
		
	}

	@Override
	public void handle(MessageCreateEvent event){
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {
		String response = event.getMessageContent().replace("!generate word search puzzle: ", "");
		System.out.println(response);
		int now = 1;
		for(int i = 0; i < response.length(); i++) {
			if(response.charAt(i)== ',') {
				now++;
			}
		}
		System.out.println(now + " words");
		String[] words = new String[now];
		//int number = 0;
			}
		}
		
	}


