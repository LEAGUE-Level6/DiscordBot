package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class MIme extends CustomMessageCreateListener{
	
	Random r = new Random();
	final static boolean stopAnoyingPeople = false; // if you want to stop set to false
	
	public MIme(String channelName) {
		super(channelName);
	}
	
	@Override
	public void handle(MessageCreateEvent event) {
		if(!event.getMessageAuthor().isYourself() || stopAnoyingPeople) {
			String s = event.getMessageContent();
			for(int i = 0; i < s.length(); i++) {
				int j = r.nextInt(3);
				if(j == 0) {
					s = s.substring(0,i) + s.substring(i,i+1).toUpperCase() + s.substring(i+1,s.length());
				}else if(j == 1) {
					s = s.substring(0,i) + s.substring(i,i+1).toLowerCase() + s.substring(i+1,s.length());
				}else {
					s = s.substring(0,i) + " " + s.substring(i,s.length());
					i ++;
				}
			}
			event.getChannel().sendMessage(s);		
		}
	}
}
