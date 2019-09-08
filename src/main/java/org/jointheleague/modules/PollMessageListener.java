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
			
			
			/*for(int i = 0; i < parameters.length; i++) {
				System.out.println(parameters[i]);
			}*/
			if(parameters.length >= 3) {
				try {
					
					
					JsonObject newPoll = new JsonObject();
					
					int nextParameter = 0;
					
					String name = parameters[0];
					if(parameters[0].contains("\"")) {
						int otherQuote = -1;
						for(int j = 1; j < parameters.length; j++) {
							if(parameters[j].contains("\"")) {
								otherQuote = j;
								break;
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
						
						finalName = finalName.replaceAll("\"", "");
						
						name = finalName;
						nextParameter = otherQuote+1;
						
					}
					else {nextParameter++;}
					newPoll.addProperty("name", name);
					
					//TODO convert JsonProperty to options JsonArray with name and count
					String options = parameters[nextParameter];
					String[] optionArr = options.split(",");
					if(optionArr.length <= 1) {
						event.getChannel().sendMessage("Must have at least 2 options in a poll");
						return;
					}
					JsonArray optionJsonArr = new JsonArray();
					for(int j = 0; j < optionArr.length; j++) {
						JsonObject tempOption = new JsonObject();
						tempOption.addProperty("optionName", optionArr[j]);
						tempOption.addProperty("optionCount", "0");
						optionJsonArr.add(tempOption);
					}
					newPoll.add("options", optionJsonArr);
					nextParameter++;
					
					String description = parameters[nextParameter];
					if(parameters[nextParameter].contains("\"")) {
						int otherQuote = -1;
						for(int j = nextParameter+1; j < parameters.length; j++) {
							if(parameters[j].contains("\"")) {
								otherQuote = j;
								break;
							}
						}
						if(otherQuote == -1) {
							event.getChannel().sendMessage("There was a problem reading the quotation marks");
							return;
						}
						String finalName = "";
						for(int j = nextParameter; j <= otherQuote; j++) {
							finalName += parameters[j];
							if(j != otherQuote) {
								finalName += " ";
							}
						}
						
						finalName = finalName.replaceAll("\"", "");
						
						description = finalName;
						nextParameter = otherQuote+1;
					}
					
					newPoll.addProperty("description", description);
					
					//TODO convert to JsonArray of participants (empty)
					JsonArray participants = new JsonArray();
					newPoll.add("participants", participants);
					
					/* 	if(optionArr.length > 1) {
						String responseString = "";
						for(int k = 0; k < optionArr.length; k++) {
							responseString += "0";
							if(k != optionArr.length-1) {
								responseString += ",";
							}
						}
						newPoll.addProperty("responses", responseString);
					}
					else {
						event.getChannel().sendMessage("There must be at least two options in the poll");
						return;
					}*/
					
					newPoll.addProperty("owner", String.valueOf(event.getMessageAuthor().asUser().get().getMentionTag()));
					JsonArray allPollsForThisChannel = (JsonArray) channelPolls.get("cPolls");
					allPollsForThisChannel.forEach(pol -> {
						JsonObject polObj = (JsonObject) pol;
						if(polObj.get("name").getAsString().equals(newPoll.get("name").getAsString())) {
							event.getChannel().sendMessage("Name Taken");
							return;
						}
					});
					channelPolls.get("cPolls").getAsJsonArray().add(newPoll);
					
					
					event.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Created a New Poll")
							.setColor(Color.GREEN)
							.addField("Name", name)
							.addField("Description", description)
							.addField("Options", options)
							);
					saveJson();
				}
				catch(IndexOutOfBoundsException e) {
					event.getChannel().sendMessage(new EmbedBuilder().setTitle("Invalid Parameters (IndexOutOfBoundsException)").setDescription("!createPoll \"<Poll Name>\" <Option1,Option2,Option3,...> \"<Description>\" (Only use quotations around the desc and/or name is you want it to be multi-word)").setColor(Color.RED));
				}
				
			}
			else {
				event.getChannel().sendMessage(new EmbedBuilder().setTitle("Invalid Parameters").setDescription("!createPoll \"<Poll Name>\" <Option1,Option2,Option3,...> \"<Description>\" (Only use quotations around the desc and/or name is you want it to be multi-word)").setColor(Color.RED));
			}
		}
		else {
			event.getChannel().sendMessage(new EmbedBuilder().setTitle("Invalid Parameters").setDescription("!createPoll \"<Poll Name>\" <Option1,Option2,Option3,...> \"<Description>\" (Only use quotations around the desc and/or name is you want it to be multi-word)").setColor(Color.RED));
		}
		
	}
	
	private void displayPolls(MessageCreateEvent event) {
		if(channelPolls != null) {
			JsonArray allPC = (JsonArray) channelPolls.get("cPolls");
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Polls for this Channel");
			allPC.forEach(possiblePoll -> {
				JsonObject possObj = (JsonObject) possiblePoll;
				eb.addField(possObj.get("name").getAsString(), possObj.get("description").getAsString());
			});
			eb.setColor(Color.YELLOW);
			event.getChannel().sendMessage(eb);
		}
	}
	
	private void infoForPoll(MessageCreateEvent event) {
		String fullMessage = event.getMessageContent();
		String command = "!pollInfo";
		try {
			String pollName = fullMessage.substring(command.length()).trim().replaceAll("\"", "");
			if(pollName.trim().equals("")) {
				event.getChannel().sendMessage(new EmbedBuilder()
						.setTitle("Invalid Parameters")
						.setDescription("!pollInfo \"<Poll Name>\"")
						.setColor(Color.RED)
						);
				return;
			}
			//event.getChannel().sendMessage(pollName);
			if(channelPolls != null) {
				JsonArray pollInfoArr = channelPolls.get("cPolls").getAsJsonArray();
				for(int j = 0; j < pollInfoArr.size(); j++) {
					JsonObject tempPollInfoArr = pollInfoArr.get(j).getAsJsonObject();
					if(tempPollInfoArr.get("name").getAsString().equals(pollName)) {
						displayInformationPoll(tempPollInfoArr, event);
						return;
					}
				}
				event.getChannel().sendMessage(new EmbedBuilder().setTitle("Poll not Found").setColor(Color.RED));
			}
		}
		catch(IndexOutOfBoundsException e) {
			event.getChannel().sendMessage(new EmbedBuilder()
					.setTitle("Invalid Parameters")
					.setDescription("!pollInfo \"<Poll Name>\"")
					.setColor(Color.RED)
					);
		}
		
		
		
	}
	
	private void displayInformationPoll(JsonObject pollObj, MessageCreateEvent event) {
		System.out.println("test");
		EmbedBuilder embedb = new EmbedBuilder();
		embedb.setTitle("Poll Information");
		embedb.addField("Name", pollObj.get("name").getAsString());
		//TODO requires combining options array into String.
		String optionStr = "";
		JsonArray optionsJs = (JsonArray) pollObj.get("options");
		for(int j = 0; j < optionsJs.size(); j++) {
			JsonObject optObj =  (JsonObject) optionsJs.get(j);
			optionStr += optObj.get("optionName").getAsString();
			if(j != optionsJs.size()-1) {
				optionStr += "\n";
			}
		}
		embedb.addField("Options", optionStr);
		embedb.addField("Description", pollObj.get("description").getAsString());
		embedb.addField("Owner", pollObj.get("owner").getAsString());
		event.getChannel().sendMessage(embedb);
	}
	
	private void removePollParse(MessageCreateEvent event) {
		String fullMessage = event.getMessageContent();
		String command = "!removePoll" ;
		try {
			String pollName = fullMessage.substring(command.length()).trim().replaceAll("\"", "");
			if(pollName.trim().equals("")) {
				event.getChannel().sendMessage(new EmbedBuilder()
						.setTitle("Invalid Parameters")
						.setDescription("!removePoll \"<Poll Name>\"")
						.setColor(Color.RED)
						);
				return;
			}
			//event.getChannel().sendMessage(pollName);
			if(channelPolls != null) {
				
				JsonArray pollInfoArr = channelPolls.get("cPolls").getAsJsonArray();
				for(int j = 0; j < pollInfoArr.size(); j++) {
					JsonObject tempPollInfoArr = pollInfoArr.get(j).getAsJsonObject();
					if(tempPollInfoArr.get("name").getAsString().equals(pollName)) {
						if(tempPollInfoArr.get("owner").getAsString().equals((event.getMessageAuthor().asUser().get().getMentionTag()))) {
							removePoll(event, tempPollInfoArr, j);
						}
						else {
							event.getChannel().sendMessage(new EmbedBuilder().setTitle("Insufficient Permissions").setDescription("You are not the owner of the poll."));
						}
						
						return;
					}
				}
				event.getChannel().sendMessage(new EmbedBuilder().setTitle("Poll not Found").setColor(Color.RED));
				
			}
		}
		catch(IndexOutOfBoundsException e) {
			event.getChannel().sendMessage(new EmbedBuilder()
					.setTitle("Invalid Parameters")
					.setDescription("!removePoll \"<Poll Name>\"")
					.setColor(Color.RED)
					);
		}

	}
	
	private void removePoll(MessageCreateEvent event, JsonObject toRemovePoll, int index) {
		String removedName = toRemovePoll.get("name").getAsString();
		JsonArray cPollsCopy = channelPolls.get("cPolls").getAsJsonArray();
		cPollsCopy.remove(index);
		channelPolls.remove("cPolls");
		channelPolls.add("cPolls", cPollsCopy);
		event.getChannel().sendMessage(new EmbedBuilder().setTitle("Poll Removed")
				.setDescription("The following poll has been removed: " + removedName)
				);
		saveJson();
	}
	
	
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
		if(channelPolls == null) {
			getPolls(event.getChannel().getId());
		}
		
		
		//Will make an embed message of all the polls on a channel if there are any;
		if(event.getMessageContent().equals("!polls")) {
			event.getChannel().sendMessage("Preparing...");
			getPolls(event.getChannel().getId());
			if(channelPolls == null) {
				event.getChannel().sendMessage("There are no polls for this channel");
			}
			else {
				event.getChannel().sendMessage("Polls Received.");
				displayPolls(event);
			}
		}
		
		//Creates a Poll on the channel using the createPoll method
		else if(event.getMessageContent().startsWith("!createPoll")) {
			getPolls(event.getChannel().getId());
			createPoll(event);
		}
		else if(event.getMessageContent().startsWith("!pollInfo")) {
			infoForPoll(event);
		}
		else if (event.getMessageContent().startsWith("!removePoll")) {
			removePollParse(event);
		}
		else if(event.getMessageContent().startsWith("!votePoll")) {
			
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
