package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class Encoder extends CustomMessageCreateListener {

	private final String encode = "!encode";
	private final String decode = "!decode";
	private final String pigLatin = "!pigLatin";
	private final String morse = "!morse";
	private final String shift = "!shift";
	
	public Encoder(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		
		if(message.startsWith(encode)) {
			message = message.substring(encode.length());
			//event.getChannel().sendMessage(message);
			if(message.startsWith(pigLatin)) {
				message = message.substring(pigLatin.length() +1);
				//event.getChannel().sendMessage(message);
				event.getChannel().sendMessage(encodePigLatin(message));
			}
		}
		else if(message.startsWith(decode)) {
			message = message.substring(0, decode.length());
			if(message.startsWith(pigLatin)) {
				message = message.substring(0, pigLatin.length());
			}
		}
		
	}
	
	String encodePigLatin(String message) {
		if(message.indexOf(" ") == -1) message = message.substring(1) + message.charAt(0) + "ay";
		else {
			for(int i = 0; i < message.length();) {
				String word = "";
				if(message.substring(i).indexOf(" ") == -1) word = message;
				else {
					word = message.substring(i).substring(0, message.indexOf(" "));
					i = message.indexOf(" ") + 1;
				}
				
			}
		}
		return message;
	}
	
	
}
