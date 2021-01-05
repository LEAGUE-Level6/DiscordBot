package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class RandomNumber extends CustomMessageCreateListener {

	private static final String COMMAND = "!random";
	
	public RandomNumber(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Allows you to get a random number.  You can also specify a range of values (e.g. !random 50-100)"); 
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!random","");
			
			if(cmd.equals("")) {
				
				Random r = new Random();
				event.getChannel().sendMessage("Your random number is " + r.nextInt(1000));
				
				
			} else {
				
				String rlow = cmd.substring(0, cmd.indexOf('-'));
				String rhigh = cmd.replace(rlow + '-', "");
				
				event.getChannel().sendMessage("Your random number between " + rlow + " and " + rhigh + " is " + ThreadLocalRandom.current().nextInt(Integer.parseInt(rlow), Integer.parseInt(rhigh)));
				
			}
			
		}
	}
	
}
