package org.jointheleague.modules;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
			words[3] = "programming";
			Random r = new Random();
			int rr = r.nextInt(4);
			for (int i = 0; i < words[rr].length(); i++) {
				lines += " - ";
			}
			System.out.println(lines);
			event.getChannel().sendMessage(lines);
			event.getChannel().sendMessage("Guess some letters. Use !guess + a letter to guessesd");
			
			String s = event.getMessageContent();
			event.getChannel().sendMessage(s);
			ArrayList<Character> al = new ArrayList<>();
			for (int i = 0; i < words[rr].length(); i++) {
				if (s.equals(words[rr].charAt(i))) {
					for (int j = 0; j < al.size(); j++) {
						if (al.get(j).equals(words[rr].charAt(i))) {
							event.getChannel().sendMessage("That word has been chosen.");
						}

					}

					al.add(words[rr].charAt(i));
					System.out.println("Cprrect letter");
					event.getChannel().sendMessage("Correct letter !");
				}
			
			}
		}
	}

}
