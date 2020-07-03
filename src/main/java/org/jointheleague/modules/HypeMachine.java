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
		
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!random","");
			
			if(cmd.contains("add")) {
				String addResponse = cmd.replace("add", "").replace("!HypeMachine", "");
				responses.add(addResponse);
				event.getChannel().sendMessage("Response <"+addResponse+"> appended to hype list!");
			} else {
				Random r = new Random();
				event.getChannel().sendMessage(responses.get(r.nextInt(responses.size())));
				
			}
			
		}
	}
	
}
