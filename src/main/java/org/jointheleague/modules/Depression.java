package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class Depression extends CustomMessageCreateListener {

	private static final String COMMAND = "!depression";
	
	public Depression(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Allows you to hear depressing messages");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!depression","");
			
			if(cmd.equals("")) {
				
				Random r = new Random();
				int num = r.nextInt(3);
				
				System.out.println(num);
				if(num==0) {
					event.getChannel().sendMessage("You don't have friends");
				}
				if(num==1) {
					event.getChannel().sendMessage("You are failing all your classes");
				}
				if(num==2) {
					event.getChannel().sendMessage("You don't have dreams");
				}
				if(num==3) {
					event.getChannel().sendMessage("You suck");
				}
				
				
			}
			
		}
	}
	
}
