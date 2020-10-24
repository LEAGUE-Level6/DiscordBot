package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;


public class WordSearchPuzzleGenerator extends CustomMessageCreateListener{

	public static final String COMMAND = "!generate word search puzzle:";
	public WordSearchPuzzleGenerator(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Creates a 10 x 10 random word search puzzle with the words you want. except the words need to be less than 11 letters long");
		
	}

	@Override
	public void handle(MessageCreateEvent event){
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {
		String response = event.getMessageContent().replace("!generate word search puzzle: ", "");
		System.out.println(response);
		int now = 0;
		for(int i = 0; i < response.length(); i++) {
			if(response.charAt(i)== ',') {
				now++;
			}
		}
		System.out.println(now + " words");
		String[] words = new String[now];
		int o = -1;
		for(int n = 0; n < response.length(); n++) {
			if(response.charAt(n)==',') {
				o++;
				words[o] = response.substring(0, response.indexOf(','));
				//response = response.substring(response.indexOf(',')+1, response.length());
			}
		}
		for(int i = 0; i < words.length; i++) {
			System.out.println(words[i]);
		}
		char[][] puzzle = new char[10][10];
			}
		}
		
	}


