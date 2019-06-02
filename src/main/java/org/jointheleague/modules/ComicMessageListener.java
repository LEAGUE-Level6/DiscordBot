package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class ComicMessageListener extends CustomMessageCreateListener {

	public ArrayList<String> xURLS; // The URLs of the XKCD comics
	public ArrayList<String> tURLS; // The URLs of the Tiny Stripz comics

	public ComicMessageListener(String channelName) {
		super(channelName);
		xURLS = new ArrayList<String>();

		// Get the XKCD URLs
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(new File("src/main/java/org/jointheleague/modules/BestOfXKCD.txt")));
			while (br.ready()) {
				xURLS.add(br.readLine());
			}

			br.close();
		} catch (Exception e) {
			
		}
		
		// Get the Tiny Stripz URLs
		tURLS = new ArrayList<String>();

		try {
			BufferedReader br = new BufferedReader(
					new FileReader(new File("src/main/java/org/jointheleague/modules/BestOfTinyStripz.txt")));
			while (br.ready()) {
				tURLS.add(br.readLine());
			}

			br.close();
		} catch (Exception e) {
			
		}
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!comic")) {
			if (event.getMessageContent().startsWith("!comic x")) {
				event.getChannel().sendMessage("Getting one of the best XKCDs...");
				event.getChannel().sendMessage(xURLS.get(new Random().nextInt(xURLS.size())));
			} else if (event.getMessageContent().startsWith("!comic t")) {
				event.getChannel().sendMessage("Getting one of the best Tiny Stripz...");
				event.getChannel().sendMessage(tURLS.get(new Random().nextInt(tURLS.size())));
			} else {
				event.getChannel().sendMessage("XKCD or Tiny Stripz?");
				event.getChannel().sendMessage("Type !comic x for XKCD, !comic t for Tiny Stripz");
			}
		}
	}

}
