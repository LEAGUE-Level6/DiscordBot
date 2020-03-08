package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class JSONadventure extends CustomMessageCreateListener {

	private static final String START = "!adventure";
	private static final String HELP = "!advhelp";
	Gson gson = new Gson();
    BufferedReader in;

	public JSONadventure(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(START)) {
						try {
							in = new BufferedReader(new FileReader("src/main/java/org/jointheleague/modules/story.json"));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
				        JsonParser parser = new JsonParser();
						JsonObject fileData = (JsonObject) parser.parse(in);
				        event.getChannel().sendMessage("Welcome! " + fileData.get("text").getAsString().replace("\"", ""));
				        event.getChannel().sendMessage("1: " + fileData.get("choicea").getAsString().replace("\"", ""));
				        event.getChannel().sendMessage("2: " + fileData.get("choiceb").getAsString().replace("\"", ""));
				        event.getChannel().sendMessage("3: " + fileData.get("choicec").getAsString().replace("\"", ""));
				}
		else if(event.getMessageContent().equals(HELP)) {
			event.getChannel().sendMessage("Type 1, 2, or 3 to choose!");
		}
	}

}