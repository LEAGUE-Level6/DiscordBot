package org.jointheleague.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.aksingh.owmjapis.api.APIException;

public class PollMessageListener extends CustomMessageCreateListener {
	
	JsonObject channelPolls;
	JsonArray jArr;
	boolean makingPoll = false;
	
	public PollMessageListener(String channelName) {
		super(channelName);
	}
	
	
	//Checks if the there is an entry for the given channel and puts it into a member variable if there is.
	private void getPolls(long id) {
		
		try {
			FileReader reader = new FileReader(new File("src/main/java/org/jointheleague/modules/polls.json"));
			JsonParser parser = new JsonParser();
			JsonArray channelList = (JsonArray) parser.parse(reader);
			jArr = channelList;
			String queryValue = String.valueOf(id);
			channelList.forEach(chnl -> {
				JsonObject channelObj = (JsonObject) chnl;
				if(channelObj.get("id").getAsString().equals(queryValue)) {
					channelPolls = channelObj;
					return;
				}
			});
			
			//If the code ends up here, the channel does not exist in poll information
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void createPoll(MessageCreateEvent event) {
		//Checks if there is an entry in the json for the given channel. If not, it makes one.
		if(channelPolls == null) {
			JsonObject newChannel = new JsonObject();
			newChannel.addProperty("id", String.valueOf(event.getChannel().getId()));
			newChannel.add("cPolls", new JsonArray());
			jArr.add(newChannel);
			channelPolls = newChannel;
		}
		
		//TODO Make Poll Creation
		
	}
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
		//Will make an embed message of all the polls on a channel if there are any;
		if(event.getMessageContent().equals("!polls")) {
			event.getChannel().sendMessage("Preparing...");
			getPolls(event.getChannel().getId());
			if(channelPolls == null) {
				event.getChannel().sendMessage("There are no polls for this channel");
			}
			else {
				event.getChannel().sendMessage("Polls Received.");
				//TODO Display Polls
			}
		}
		
		//Creates a Poll on the channel using the createPoll method
		else if(event.getMessageContent().equals("!createPoll")) {
			getPolls(event.getChannel().getId());
			createPoll(event);
		}
		
	}
	
	
}
