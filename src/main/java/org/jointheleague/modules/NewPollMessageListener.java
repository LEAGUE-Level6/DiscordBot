package org.jointheleague.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.aksingh.owmjapis.api.APIException;

public class NewPollMessageListener extends CustomMessageCreateListener {

	//Commands
	static final String LIST_COMMAND = "!poll-list";
	static final String CREATE_COMMAND = "!poll-create";
	static final String INFO_COMMAND = "!poll-info";
	static final String REMOVE_COMMAND = "!poll-remove";
	static final String VOTE_COMMAND = "!poll-vote";
	static final String STATUS_COMMAND = "!poll-status";
	static final String HELP_COMMAND = "!poll-help";
	
	//Loaded Json Information
	JsonObject channelPolls;
	JsonArray fileData;
	
	public NewPollMessageListener(String channelName) {
		super(channelName);
	}
	
	//Utility Functions:
	
	static String[] parseParameters(String command, String fullMessage) {
		return null;
	}
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(channelPolls == null) {
			loadPolls(event);
		}
		
		//Separate Command Listeners
		if(event.getMessageContent().startsWith(CREATE_COMMAND)) {
			createPoll(event);
		}
	}
	
	//Gets the JSON Object for the channel into a member variable
	void loadPolls(MessageCreateEvent event) {
		FileReader reader;
		long channelId = event.getChannel().getId();
		try {
			//Get File Contents as JsonArray
			reader = new FileReader(new File("src/main/java/org/jointheleague/modules/polls.json"));
			JsonParser parser = new JsonParser();
			fileData = (JsonArray) parser.parse(reader);
			//Find the JSON Object for the channel
			fileData.forEach(chnl -> {
				JsonObject channelObj = (JsonObject) chnl;
				if(channelObj.get("id").getAsString().equals(String.valueOf(channelId))) {
					channelPolls = channelObj;
					return;
				}
			});
			
			//Create a JSON Object for the channel if it doesn't already exist
			JsonObject newChannel = new JsonObject();
			newChannel.addProperty("id", String.valueOf(event.getChannel().getId()));
			newChannel.add("cPolls", new JsonArray());
			fileData.add(newChannel);
			channelPolls = newChannel;
			saveJson();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void createPoll(MessageCreateEvent event) {
		
	}
	
	//Saves the contents of fileData into the polls.json file for permanent storage
	void saveJson() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("src/main/java/org/jointheleague/modules/polls.json", false));
			pw.print(fileData.toString());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
