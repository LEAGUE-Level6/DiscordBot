package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class HypeMachine extends CustomMessageCreateListener {

	private static final String COMMAND = "!HypeMachine";
	ArrayList<String> responses = new ArrayList<String>();
	
	
	public HypeMachine(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Hypes you up. You say something, then it replies with a happy response! If you want to add a new response just type add after HypeMachine and then the response.");
		responses.add("You got this!");
		responses.add("You're Awesome!");
		responses.add("Way to go!");
		responses.add("I'm rooting for you!");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String contents = event.getMessageContent();
		String[] cmds = contents.split(" ");
		if (contents.contains(COMMAND)) {
			if(cmds.length > 1 && cmds[1].equals("add")) {
				if(cmds.length > 2) {
					int messageIndex = contents.indexOf("add") + 3;
					String addResponse = contents.substring(messageIndex);
					responses.add(addResponse);
					event.getChannel().sendMessage("Response <"+addResponse+"> appended to hype list!");
				}else {
					event.getChannel().sendMessage("You must add a statement after 'add' to complete the command");
				}
			} else {
				Random r = new Random();
				event.getChannel().sendMessage(responses.get(r.nextInt(responses.size())));
				
			}
			
		}
	}
	
}
