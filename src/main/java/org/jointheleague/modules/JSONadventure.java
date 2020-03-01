package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class JSONadventure extends CustomMessageCreateListener {

	private static final String START = "!adventure";
	private static final String HELP = "!advhelp";
	String suffix = "SHORT";
	Gson gson = new Gson();
    BufferedReader in;

	public JSONadventure(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(START)) {
						try {
							in = new BufferedReader(new FileReader("adventure.json"));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
				        JsonParser parser = new JsonParser();
						JsonObject fileData = (JsonObject) parser.parse(in);
				        event.getChannel().sendMessage(fileData.get("startprompt").toString().replace("\"", ""));
				}
		else if(event.getMessageContent().equals(HELP)) {
			event.getChannel().sendMessage("The SBot's Dynamic Links feature lets you shorten links. Usage is !‎dynamiclink [url]. Currently no option is available to change the suffix or domain.");
		}
	}

}