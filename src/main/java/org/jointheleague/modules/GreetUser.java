package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class GreetUser extends CustomMessageCreateListener{
	//set command to run feature in chat
	private static final String COMMAND = "!hello";
	
	public GreetUser(String channelName) {
		super(channelName);
		//description for the !help command
		helpEmbed = new HelpEmbed(COMMAND, "Will return a simple greeting from the bot with username");

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		//splits user entered command by spaces
		String[] cmd = event.getMessageContent().split(" ");
		//looks for first word entered
		//check for !hello command
		if(cmd[0].equals(COMMAND)) {
			//if string array has one item, no extra parameters
				//no name will be used in the greeting
			if(cmd.length == 1) {
				//generate Random number so a different message is sent each time
				Random r = new Random();
				int randomNumber = r.nextInt(5);
				//send the different messages
				switch(randomNumber) {
				case 0:
					event.getChannel().sendMessage("Hello! Hope your day is amazing :)");
					break;
				case 1:
					event.getChannel().sendMessage("Hello there......General Kenobi");
					break;
				case 2:
					event.getChannel().sendMessage("Hey! Where's Perry?");
					break;
				case 3:
					event.getChannel().sendMessage("Hey! Where's Perry?");
					break;
				case 4:
					event.getChannel().sendMessage("Hola amigo");
					break;
				}
			}
			//this means the user entered a name to be used in the greeting
			else {
				event.getChannel().sendMessage("Hello, "+cmd[1]);
			}
		}
	}
}
