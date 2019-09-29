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
		int commandLength = new String("!poll-create").length();
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
					event.getChannel().sendMessage(new EmbedBuilder().setTitle("Invalid Parameters (IndexOutOfBoundsException)").setDescription("!poll-create \"<Poll Name>\" <Option1,Option2,Option3,...> \"<Description>\" (Only use quotations around the desc and/or name is you want it to be multi-word)").setColor(Color.RED));
				}
				
			}
			else {
				event.getChannel().sendMessage(new EmbedBuilder().setTitle("Invalid Parameters").setDescription("!poll-create \"<Poll Name>\" <Option1,Option2,Option3,...> \"<Description>\" (Only use quotations around the desc and/or name is you want it to be multi-word)").setColor(Color.RED));
			}
		}
		else {
			event.getChannel().sendMessage(new EmbedBuilder().setTitle("Invalid Parameters").setDescription("!poll-create \"<Poll Name>\" <Option1,Option2,Option3,...> \"<Description>\" (Only use quotations around the desc and/or name is you want it to be multi-word)").setColor(Color.RED));
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
		String command = "!poll-info";
		try {
			String pollName = fullMessage.substring(command.length()).trim().replaceAll("\"", "");
			if(pollName.trim().equals("")) {
				event.getChannel().sendMessage(new EmbedBuilder()
						.setTitle("Invalid Parameters")
						.setDescription("!poll-info \"<Poll Name>\"")
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
					.setDescription("!poll-info \"<Poll Name>\"")
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
		String command = "!poll-remove" ;
		try {
			String pollName = fullMessage.substring(command.length()).trim().replaceAll("\"", "");
			if(pollName.trim().equals("")) {
				event.getChannel().sendMessage(new EmbedBuilder()
						.setTitle("Invalid Parameters")
						.setDescription("!poll-remove \"<Poll Name>\" (Use quotations if the poll name is multi-word)")
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
					.setDescription("!poll-remove \"<Poll Name>\" (Use quotations if the poll name is multi-word)")
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
	
	
	private void doVote(MessageCreateEvent event) {
		String command = "!poll-vote ";
		EmbedBuilder formatCorrection = new EmbedBuilder()
				.setTitle("Invalid Parameters")
				.setDescription("!poll-vote \"<Poll Name>\" <option> (Only use quotations around the Poll Name if it is multi-word)");
		try {
			String parameters = event.getMessageContent().substring(command.length());
			String[] params = parameters.split(" ");
			String nameP = null;
			int currentIndex = 0;
			if(params[0].contains("\"")) {
				for(int k = 1; k < params.length; k++) {
					if(params[k].contains("\"")) {
						currentIndex = k+1;
						nameP = "";
						for(int l = 0; l <= k; l++) {
							nameP += params[l];
							if(l != k) {
								nameP += " ";
							}
						}
						break;
					}
				}
			}
			else {
				nameP = params[0];
				currentIndex++;
			}
			
			if(nameP == null) {
				event.getChannel().sendMessage("There was a problem getting the name of the poll.");
				return;
			}
			
			nameP = nameP.replaceAll("\"", "");
			
			JsonArray allCPolls = channelPolls.get("cPolls").getAsJsonArray();
			for(int j = 0; j < allCPolls.size(); j++) {
				System.out.println(allCPolls.get(j).getAsJsonObject().get("name").getAsString());
				System.out.println(nameP);
				if(allCPolls.get(j).getAsJsonObject().get("name").getAsString().equals(nameP)) {
					//Poll exists
					JsonArray participants = allCPolls.get(j).getAsJsonObject().get("participants").getAsJsonArray();
					for(int r = 0; r < participants.size(); r++) {
						JsonObject partPointer = participants.get(r).getAsJsonObject();
						if(partPointer.get("userTag").getAsString().equals(event.getMessageAuthor().asUser().get().getMentionTag())) {
							event.getChannel().sendMessage("You already voted.");
							return;
						}
					}
					//User has not voted
					String chosenOption = params[currentIndex];
					JsonArray allOptions = allCPolls.get(j).getAsJsonObject().get("options").getAsJsonArray();
					for(int p = 0; p < allOptions.size(); p++) {
						JsonObject optPointer = allOptions.get(p).getAsJsonObject();
						if(optPointer.get("optionName").getAsString().equals(chosenOption)) {
							String optCount = optPointer.get("optionCount").getAsString();
							int countInt = Integer.valueOf(optCount);
							countInt++;
							optPointer.remove("optionCount");
							optPointer.addProperty("optionCount", String.valueOf(countInt));
							
							JsonObject newParticipant = new JsonObject();
							newParticipant.addProperty("userTag", event.getMessageAuthor().asUser().get().getMentionTag());
							newParticipant.addProperty("userOption", chosenOption);
							participants.add(newParticipant);
							event.getChannel().sendMessage(new EmbedBuilder().setTitle("Vote Submitted")
									.setDescription("Your vote for the following poll has been submitted: "+nameP)
									.setColor(Color.GREEN));
							//TODO DM poll status to user
							event.getMessage().delete();
							saveJson();
							event.getChannel().sendMessage("You voted! You can check the status of the poll by using the command '!poll-status'");
							return;
						}
					}
					event.getChannel().sendMessage("Invalid Option Name");
					return;
				}
			}
			event.getChannel().sendMessage("Invalid Poll");
		}
		catch(IndexOutOfBoundsException e) {
			event.getChannel().sendMessage(formatCorrection);
		}
		catch(NumberFormatException e) {
			event.getChannel().sendMessage("There was an internal error");
		}
		
	}
	
	private void getPollStatus(MessageCreateEvent event) {
		String command = "!poll-status ";
		String pollName = "";
		try {
			pollName = event.getMessageContent().substring(command.length());
		}
		catch(IndexOutOfBoundsException e) {
			event.getChannel().sendMessage(new EmbedBuilder()
					.setTitle("Invalid Parameters")
					.setDescription("!poll-status \"<Poll Name>\"(Only use quotations is the poll name is multi-word)")
					.setColor(Color.RED));
			return;
		}
		
		pollName = pollName.replaceAll("\"", "");
		JsonArray cPollio = channelPolls.get("cPolls").getAsJsonArray();
		for(int j = 0; j < cPollio.size(); j++) {
			JsonObject pollPointer = cPollio.get(j).getAsJsonObject();
			if(pollPointer.get("name").getAsString().equals(pollName)) {
				//Found Poll
				EmbedBuilder toDirect = new EmbedBuilder().setTitle("Poll Status for: "+pollName).setColor(Color.ORANGE);
				JsonArray allPossOpt = pollPointer.get("options").getAsJsonArray();
				
				
				for(int k = 0; k < allPossOpt.size(); k++) {
					JsonObject optPointer = allPossOpt.get(k).getAsJsonObject();
					toDirect.addField(optPointer.get("optionName").getAsString(), optPointer.get("optionCount").getAsString());
				}
				if(true) {
					JsonArray allParts = pollPointer.get("participants").getAsJsonArray();
					for(int q = 0; q < allParts.size(); q++) {
						JsonObject currentPart = allParts.get(q).getAsJsonObject();
						if(currentPart.get("userTag").getAsString().equals(event.getMessageAuthor().asUser().get().getMentionTag())) {
							event.getMessageAuthor().asUser().get().sendMessage(toDirect);
							event.getChannel().sendMessage("Status Information sent.");
							return;
						}
					}
					event.getChannel().sendMessage("You didn't vote on this poll yet.");
				}
				
				return;
			}
		}
		event.getChannel().sendMessage("Invalid Poll");
	}
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
		if(channelPolls == null) {
			getPolls(event.getChannel().getId());
		}
		
		//Will make an embed message of all the polls on a channel if there are any;
		if(event.getMessageContent().equals("!poll-list")) {
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
		else if(event.getMessageContent().startsWith("!poll-create")) {
			getPolls(event.getChannel().getId());
			createPoll(event);
		}
		else if(event.getMessageContent().startsWith("!poll-info")) {
			infoForPoll(event);
		}
		else if (event.getMessageContent().startsWith("!poll-remove")) {
			removePollParse(event);
		}
		else if(event.getMessageContent().startsWith("!poll-vote")) {
			doVote(event);
		}
		else if(event.getMessageContent().startsWith("!poll-status")) {
			getPollStatus(event);
		}
		else if(event.getMessageContent().startsWith("!poll-help")) {
			EmbedBuilder helpStuff = new EmbedBuilder().setTitle("Poll Commands").setColor(Color.BLUE);
			helpStuff.setDescription("Note: For multi-word parameters, surround the parameter in quotations. Poll options cannot be multiword");
			helpStuff.addField("!poll-list","Lists all the Polls");
			helpStuff.addField("!poll-create <Poll Name> <option1,option2,option3> <Poll Description>", "Create a Poll");
			helpStuff.addField("!poll-remove <Poll Name>", "Remove one of your Polls");
			helpStuff.addField("!poll-info <Poll Name>", "Get basic information about the poll (Name, Description, Option, Owner)");
			helpStuff.addField("!poll-vote <Poll Name> <Option Name>", "Vote on the specified Poll");
			helpStuff.addField("!poll-status <Poll Name>", "Gets the Status of the Poll");
			helpStuff.addField("!poll-help", "Gives you information about Poll Commands");
			event.getChannel().sendMessage(helpStuff);
		}
		
	}
	
	private void saveJson() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("src/main/java/org/jointheleague/modules/polls.json", false));
			pw.print(jArr.toString());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
