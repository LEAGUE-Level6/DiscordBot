package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Lottery extends CustomMessageCreateListener{
	public static final String COMMAND = "!PowerBall";
	public Lottery(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Use ! + PowerBall to get 5 random lotto numbers, inspired by the powerball Lottery!");
	}
	int randInt = new Random().nextInt(99);
	int randInt1 = new Random().nextInt(99);
	int randInt2 = new Random().nextInt(99);
	int randInt3 = new Random().nextInt(99);
	int randInt4 = new Random().nextInt(99);
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		
		String message = event.getMessageContent();
		if (message.equals(COMMAND)) {
			event.getChannel().sendMessage("Hello, thank you for starting the lotto process! type !GetNumbers to start the process!");
			
		}
		else if (message.equals("!GetNumbers")) {
			event.getChannel().sendMessage(("" + randInt + " ") + ("" +randInt1 + " ") + 
					("" +randInt2 + " ")+ ("" +randInt3 + " ") + ("" +randInt4 + " "));	
			event.getChannel().sendMessage("These are the winning lottory numbers for today!");
		}
	}

}
