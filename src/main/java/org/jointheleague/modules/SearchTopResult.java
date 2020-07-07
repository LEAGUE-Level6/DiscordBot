package org.jointheleague.modules;

import java.util.HashMap;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class SearchTopResult extends CustomMessageCreateListener {

	public SearchTopResult(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String link = "";
		String[] phrase = event.getMessageContent().toLowerCase().split(" ");
		if (phrase[0] == "!search") {
			if (phrase[1] == "youtube") {
				link = "https://www.youtube.com/results?search_query=";
			} else if (phrase[1] == "google") {
				link = "https://www.google.com/search?q=";
			} else if (phrase[1] == "yahoo") {
				link = "https://search.yahoo.com/search?p=";
			} else if (phrase[1] == "duckduckgo") {
				link = "https://duckduckgo.com/?q=";
			} else if (phrase[1] == "ecosia") {
				link = "https://www.ecosia.org/search?q";
			} else if (phrase[1] == "twitter") {
				link = "https://twitter.com/search?q=sailor%20moon";
			}

			for (int i = 2; i < phrase.length; i++) {
				if (i == phrase.length - 1) {
					link += phrase[i];
					if (link.contains("duckduckgo.com")) {
						link += "&ia=web";
					}
				} else {
					if (phrase[1] == "twitter") {
						link += phrase[i] + "%20";
					} else {
						link += phrase[i] + "+";
					}
				}
			}
		}
	}
}