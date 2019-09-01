package org.jointheleague.modules;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.javacord.api.entity.message.embed.EmbedBuilder;
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
			saveJson();
		}
		
		String fullMessage = event.getMessageContent();
		int commandLength = new String("!createPoll").length();
		if(fullMessage.length() > commandLength) {
			
			
			
			String[] parameters = fullMessage.substring(commandLength).trim().split(" ");
			
			
			for(int i = 0; i < parameters.length; i++) {
				System.out.println(parameters[i]);
			}
			if(parameters.length >= 3) {
				try {
					//TODO add parameters into poll json
					
					
					JsonObject newPoll = new JsonObject();
					
					int nextParameter = 0;
					
					String name = parameters[0];
					if(parameters[0].contains("\"")) {
						int otherQuote = -1;
						for(int j = 1; j < parameters.length; j++) {
							if(parameters[j].contains("\"")) {
								otherQuote = j;
							}
						}
						if(otherQuote == -1) {
							event.getChannel().sendMessage("There was a problem reading the quotation marks");
							return;
						}
						String finalName = "";
						for(int j = 0; j <= otherQuote; j++) {
							finalName += parameters[j];
							if(j != otherQuote) {
								finalName += " ";
							}
						}
						
						finalName = finalName.replaceFirst("\"", "");
						
						name = finalName;
						nextParameter = otherQuote+1;
						
					}
					newPoll.addProperty("name", name);
					
					String options = parameters[nextParameter];
					newPoll.addProperty("options", options);
					nextParameter++;
					
					String description = parameters[nextParameter];
					if(parameters[nextParameter].contains("\"")) {
						int otherQuote = -1;
						for(int j = nextParameter+1; j < parameters.length; j++) {
							if(parameters[j].contains("\"")) {
								otherQuote = j;
							}
						}
						if(otherQuote == -1) {
							event.getChannel().sendMessage("There was a problem reading the quotation marks");
							return;
						}
						String finalName = "";
						for(int j = 0; j <= otherQuote; j++) {
							finalName += parameters[j];
							if(j != otherQuote) {
								finalName += " ";
							}
						}
						
						finalName = finalName.replaceFirst("\"", "");
						
						description = finalName;
						nextParameter = otherQuote+1;
					}
					
					newPoll.addProperty("description", description);
					
					
					event.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Created a New Poll")
							.setColor(Color.GREEN)
							.addField("Name", name)
							.addField("Description", description)
							.addField("Options", options)
							);
				}
				catch(IndexOutOfBoundsException e) {
					event.getChannel().sendMessage(new EmbedBuilder().setTitle("Invalid Parameters (IndexOutOfBoundsException)").setDescription("!createPoll \"<Poll Name>\" <Option1,Option2,Option3,...> \"<Description>\"").setColor(Color.RED));
				}
				
			}
			else {
				event.getChannel().sendMessage(new EmbedBuilder().setTitle("Invalid Parameters").setDescription("!createPoll \"<Poll Name>\" <Option1,Option2,Option3,...> \"<Description>\"").setColor(Color.RED));
			}
		}
		else {
			event.getChannel().sendMessage(new EmbedBuilder().setTitle("Invalid Parameters").setDescription("!createPoll \"<Poll Name>\" <Option1,Option2,Option3,...> \"<Description>\"").setColor(Color.RED));
		}
		
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
		else if(event.getMessageContent().startsWith("!createPoll")) {
			getPolls(event.getChannel().getId());
			createPoll(event);
		}
		
	}
	
	private void saveJson() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("src/main/java/org/jointheleague/modules/polls.json", false));
			pw.print(jArr.toString());
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
