package org.jointheleague.modules;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;
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
	
	//Command Syntaxes
	static final String CREATE_SYNTAX = CREATE_COMMAND + " <Poll Name> <Poll Description> <Option 1> <Option 2> (You can have as many options as you want)";
	static final String INFO_SYNTAX = INFO_COMMAND + " <Poll Name>";
	//Loaded Json Information
	JsonObject channelPolls;
	JsonArray fileData;
	
	public NewPollMessageListener(String channelName) {
		super(channelName);
	}
	
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(channelPolls == null) {
			loadPolls(event);
		}
		
		//Maps Commands to functions
		if(event.getMessageContent().startsWith(CREATE_COMMAND)) {
			createPoll(event);
		}
		else if(event.getMessageContent().startsWith(INFO_COMMAND)) {
			infoPoll(event);
		}
		else if(event.getMessageContent().startsWith(LIST_COMMAND)) {
			listPolls(event);
		}
	}
	
	//Utility Functions:
	
	/**
	 * 
	 * Will parse the message into an array of parameters, taking into account multi-word parameters surrounded by quotation marks.
	 * 
	 * @param command The name of the command which the player has called for
	 * @param fullMessage The full message
	 * @return An array of all the parameters.
	 */
	static String[] parseParameters(String command, String fullMessage) {
		
		EmbedBuilder invalidParameters = new EmbedBuilder().setTitle("Invalid Parameters").setDescription("Make sure that you only use quotations when surrounding multi-word parameters.").setColor(Color.RED);
		
		//Splits the parameters by spaces
		String[] rawParams = fullMessage.substring(command.length()).trim().split(" ");
		
		//Since we can't predict how many multi-word parameters there will be, we utilize the ArrayList class
		ArrayList<String> finalParams = new ArrayList<String>();
		int lastQuote = -1;
		for(int i = 0; i < rawParams.length; i++) {
			//Checks if it has a quotation. If it does, that means that it is a multi-word parameter
			if(rawParams[i].contains("\"")) {
				if(lastQuote < 0) {
					lastQuote = i;
				}
				else {
					//This code will be executed if the integer lastQuote is a positive number. In other words, lastQuote represents the first quotation and i represents the second quotation.
					//of a multi-word parameter
					finalParams.add(concatParameters(lastQuote, i, rawParams));
					lastQuote = -1;
				}
			}
			else {
				//This code will simply add the single-word parameter to the array list. If an initial quote is detected, the word may be part
				//of a multi-word parameter, hence the if statement.
				if(lastQuote < 0) {
					finalParams.add(rawParams[i]);
				}
			}
		}
		
		//This means that a beginning quotation was detected but not an end quotation, so the system got confused.
		if(lastQuote >= 0) {
			return null;
		}
		//Converts the array list into an array, ready to return
		return finalParams.toArray(new String[finalParams.size()]);
	}
	
	/**
	 * 
	 * @param beginIndex Index of the word with the first quotation
	 * @param endIndex Index of the word with the second quotation
	 * @param parameters An array of parameters passed by the parseParameters function
	 * @return All words between the beginning and end index concatenated with spaces (inclusive)
	 * @see #parseParameters(String, String)
	 */
	static String concatParameters(int beginIndex, int endIndex, String[] parameters) {
		String toReturn = "";
		for(int i = beginIndex; i <= endIndex; i++) {
			if(parameters[i].contains("\"")) {
				parameters[i] = parameters[i].replaceAll("\"", "");
			}
			toReturn += parameters[i];
			if(i != endIndex) {
				toReturn += " ";
			}
		}
		
		return toReturn;
	}
	/**
	 * Saves the JsonArray member variable "fileData" into the permanent file storage. Should be called after every edit operation.
	 */
	void saveJson() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("src/main/java/org/jointheleague/modules/polls.json", false));
			pw.print(fileData.toString());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param query The name to query for in the poll list
	 * @return JsonObject representing the poll. The function returns null if the poll can't be found.
	 */
	JsonObject getPollFromName(String query) {
		
		JsonArray allPolls = channelPolls.get("cPolls").getAsJsonArray();
		for(int i = 0; i < allPolls.size(); i++) {
			JsonObject currentIteration = allPolls.get(i).getAsJsonObject();
			if(currentIteration.get("name").getAsString().equals(query)) {
				return currentIteration;
			}
		}
		
		return null;
	}
	
	
	//Command Functions:
	
	/**
	 * Loads the file data from the permanent file storage. Usually called the first time the data is requested.
	 * 
	 * @param event MessageCreateEvent from Javacord Listener
	 */
	void loadPolls(MessageCreateEvent event) {
		FileReader reader;
		long channelId = event.getChannel().getId();
		try {
			//Get File Contents as JsonArray
			reader = new FileReader(new File("src/main/java/org/jointheleague/modules/polls.json"));
			JsonParser parser = new JsonParser();
			fileData = (JsonArray) parser.parse(reader);
			
			//Find the JSON Object for the channel
			for(int j = 0; j < fileData.size(); j++) {
				JsonObject channelObj = fileData.get(j).getAsJsonObject();
				if(channelObj.get("id").getAsString().equals(String.valueOf(channelId))) {
					channelPolls = channelObj;
					return;
				}
			}
			
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
	
	/**
	 * Packs the given parameters into a Json Object representing a new poll. The command goes through multiple checks, including valid parameters
	 * and ensuring the name isn't taken. Once the new poll has been added into the file data, it will save the data into permanent storage before displaying
	 * the poll information via the information command.
	 * 
	 * @param event MessageCreateEvent given by Javacord Listener
	 * @see #displayPoll(String)
	 */
	void createPoll(MessageCreateEvent event) {
		
		//Error Messages for the Command
		EmbedBuilder invalidParameters = new EmbedBuilder().setTitle("Invalid Parameters").setDescription(CREATE_SYNTAX).setColor(Color.RED);
		EmbedBuilder nameTaken = new EmbedBuilder().setTitle("Poll Name Taken").setDescription("Someone already has a poll with that name").setColor(Color.RED);
		
		String[] commandParameters = parseParameters(CREATE_COMMAND, event.getMessageContent());
		
		//This if statement checks if the parseParameters function had an issue. If so, it should have already sent an error message so we just return.
		if(commandParameters == null) {
			return;
		}
		//There should be a minimum of four parameters since every poll must have a title, description, and at least 2 options
		if(commandParameters.length < 4) {
			event.getChannel().sendMessage(invalidParameters);
			return;
		}
		
		//Beginning of Json Object Construction:
		JsonObject newPoll = new JsonObject();
		
		//Adds in name and description properties
		String name = commandParameters[0];
		String description = commandParameters[1];
		newPoll.addProperty("name", name);
		newPoll.addProperty("description", description);
		//Adds in property of who is the owner so the program knows who will be able to perform administrative actions on the poll
		newPoll.addProperty("owner", event.getMessageAuthor().asUser().get().getMentionTag());
		
		//Loops through each option parameter and adds a new JsonObject for it, adding it into the options JsonArray
		JsonArray optionArr = new JsonArray();
		for(int i = 2; i < commandParameters.length; i++) {
			JsonObject newOption = new JsonObject();
			newOption.addProperty("optionName", commandParameters[i]);
			newOption.addProperty("optionCount", "0");
			optionArr.add(newOption);
		}
		newPoll.add("options", optionArr);
		
		//The element will be used when tracking which participants have already voted.
		newPoll.add("participants", new JsonArray());
		
		//Last minute check to make sure that the name for the poll is not already taken
		if(getPollFromName(name) != null) {
			event.getChannel().sendMessage(nameTaken);
			return;
		}
		
		//Adds the poll into the file data and saves the file data
		channelPolls.get("cPolls").getAsJsonArray().add(newPoll);
		saveJson();
		//Displays the poll using the built-in info command so the creator can double check he entered in the parameters correctly
		displayPoll(event, name);
	}
	
	/**
	 * The function is called in response to the information command. The function checks the parameter given by the user and,
	 * if all is according to syntax, will send over the singular parameter to {@link #displayPoll(MessageCreateEvent, String)} to be displayed.
	 * @param event
	 */
	void infoPoll(MessageCreateEvent event) {
		
		//Error Messages
		EmbedBuilder invalidParameters = new EmbedBuilder().setTitle("Invalid Parameters").setDescription(INFO_SYNTAX).setColor(Color.RED);
		
		//Makes sure there is only one parameter
		String[] commandParameters = parseParameters(INFO_COMMAND, event.getMessageContent());
		if(commandParameters.length != 1) {
			event.getChannel().sendMessage(invalidParameters);
			return;
		}
		
		//Calls the displayPoll function to display the poll
		displayPoll(event, commandParameters[0]);
	}
	
	/**
	 * Displays a Discord Embed of the given poll. Gets the JsonObject from {@link #getPollFromName(String)}. Often used
	 * alongside the {@link #infoPoll(MessageCreateEvent)} function.
	 * 
	 * @param event MessageCreateEvent from Javacord Listener
	 * @param pollName Name of the Poll to Display
	 */
	void displayPoll(MessageCreateEvent event, String pollName) {
		EmbedBuilder pollNotFound = new EmbedBuilder().setTitle("Poll Not Found").setDescription("Double check the poll you requested exists").setColor(Color.RED);
		
		//Gets the poll from getPollFromName utility function and then makes sure it exists
		JsonObject pollToDisplay = getPollFromName(pollName);
		if(pollToDisplay == null) {
			event.getChannel().sendMessage(pollNotFound);
			return;
		}
		
		//Adds fields for name and description
		EmbedBuilder toDisplay = new EmbedBuilder().setTitle("Poll Information").setColor(Color.YELLOW);
		toDisplay.addField("Name", pollToDisplay.get("name").getAsString());
		toDisplay.addField("Description", pollToDisplay.get("description").getAsString());
		
		//Creates a display string for all the options, each separated by a new line
		JsonArray optionArr = pollToDisplay.get("options").getAsJsonArray();
		String displayStringForOptions = "";
		for(int j = 0; j < optionArr.size(); j++) {
			displayStringForOptions += optionArr.get(j).getAsJsonObject().get("optionName").getAsString();
			displayStringForOptions += "\n";
		}
		toDisplay.addField("Options", displayStringForOptions);
		
		
		//Adds in the owner field
		toDisplay.addField("Owner", pollToDisplay.get("owner").getAsString());
		
		//Sends the Embed with all the Information
		event.getChannel().sendMessage(toDisplay);
	}
	
	/**
	 * Creates an embed with the names and descriptions of every poll on the channel.
	 * 
	 * @param event MessageCreateEvent from Javacord Listener
	 */
	void listPolls(MessageCreateEvent event) {
		//Creates Embed
		EmbedBuilder toDisplay = new EmbedBuilder().setTitle("All Polls for this Channel").setColor(Color.YELLOW);
		
		//Iterates through each poll and adds a field in embed with name and description
		JsonArray allPolls = channelPolls.get("cPolls").getAsJsonArray();
		for(int i = 0; i < allPolls.size(); i++) {
			JsonObject iteration = allPolls.get(i).getAsJsonObject();
			toDisplay.addField(iteration.get("name").getAsString(), iteration.get("description").getAsString());
		}
		
		//Displays Embed
		event.getChannel().sendMessage(toDisplay);
	}
	
	

}
