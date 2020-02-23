package org.jointheleague.modules;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Hangman extends CustomMessageCreateListener {

	public Hangman(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith("!hangman")) {
		String[] words = new String[4];
		String lines = "";
		words[0] = "computer";
		words[1] = "java";
		words[2] = "laptop";
		words[3] ="programming";
		Random r = new Random();
		int rr = r.nextInt(4);
		for (int i = 0; i < words[rr].length(); i++) {
lines += " - ";
		}
System.out.println(lines);
		event.getChannel().sendMessage(lines);
		event.getChannel().sendMessage("Guess some letters.");
		String s = event.getMessageContent();
		for (int i = 0; i < words[rr].length(); i++) {
			if (s.equals(words[rr].charAt(i))) {
				
			}
			
		}
		}
	}

}
