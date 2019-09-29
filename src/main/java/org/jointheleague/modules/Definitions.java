package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.javacord.api.event.message.MessageCreateEvent;

public class Definitions extends CustomMessageCreateListener{
	public Definitions(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String a = event.getMessageContent();
		if(a.startsWith("!define ")) {
			String toBeFound = a.substring(8);
			event.getChannel().sendMessage(Definition(toBeFound));
		}
	}
	
	public static String Definition(String s) {
		String d = "Definition could not be found";
		URL dictionarySite;
		try {
			dictionarySite = new URL("https://www.dictionary.com/browse/" + s + "?s=t");
			BufferedReader in = new BufferedReader(new InputStreamReader(dictionarySite.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.length() > 34) {
					if (inputLine.substring(0, 35).equals("  <meta name=\"description\" content=")) {
						d= s + ": " + inputLine.substring(49 + s.length(), inputLine.length()-12);
					}
				}
			}
			in.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "Definition could not be found";
		}
		return d;
	}
}
