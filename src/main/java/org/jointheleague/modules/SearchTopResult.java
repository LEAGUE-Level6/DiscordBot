package org.jointheleague.modules;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class SearchTopResult extends CustomMessageCreateListener {

	public SearchTopResult(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String searchLink = "";
		String[] phrase = event.getMessageContent().toLowerCase().split(" ");

		if (phrase[0].equals("!search")) {
			if (phrase[1].equals("youtube")) {
				searchLink = "https://www.youtube.com/results?search_query=";
			} else if (phrase[1].equals("google")) {
				searchLink = "https://www.google.com/search?q=";
			} else if (phrase[1].equals("yahoo")) {
				searchLink = "https://search.yahoo.com/search?p=";
			} else if (phrase[1].equals("duckduckgo")) {
				searchLink = "https://duckduckgo.com/?q=";
			} else if (phrase[1].equals("ecosia")) {
				searchLink = "https://www.ecosia.org/search?q";
			} else if (phrase[1].equals("twitter")) {
				searchLink = "https://twitter.com/search?q=";
			}

			for (int i = 2; i < phrase.length; i++) {
				if (i == phrase.length - 1) {
					searchLink += phrase[i];
					if (searchLink.contains("duckduckgo.com")) {
						searchLink += "&ia=web";
					}
				} else {
					if (phrase[1].equals("twitter")) {
						searchLink += phrase[i] + "%20";
					} else {
						searchLink += phrase[i] + "+";
					}
				}
			}

			URL oracle;
			String siteContents = "";
			try {
				oracle = new URL(searchLink);
				BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
				String inputLine = in.readLine();
				while (inputLine != null) {
					siteContents += inputLine;
					inputLine = in.readLine();
				}

				in.close();
				event.getChannel().sendMessage(siteContents);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String link = "";
			if (phrase[1].equals("youtube")) {
				for (int i = 0; i < siteContents.length() - 9; i++) {
					if (siteContents.substring(i, i + 9).equals("\"videoId\"")) {
						link += "https://www.youtube.com/watch?v=" + siteContents.substring(i + 11, i + 22);
						break;
					}
				}
			} 

			event.getChannel().sendMessage("Top result: " + link);
		}
	}
}
