package org.jointheleague.modules;

import java.util.List;

import java.util.Random;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;


public class KillMessageListener extends CustomMessageCreateListener {
	int stage = 0;
	Message userMsg;
	User user;
	Random r;
	private static final String COMMAND = "!kill";
	
	
	public KillMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(stage == 0 && event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			event.getChannel().sendMessage("Who would you like to kill? Please do \"user \" before you mention the user.");
			stage++;
		}
		
		else if (stage == 1 && event.getMessageContent().contains("user ")) {
			userMsg = event.getMessage();
			if(userMsg == null) {
				//System.out.println("no user message");
				return;
			}
			
			List<User> mentioned = userMsg.getMentionedUsers();
			
			if(mentioned == null || mentioned.isEmpty()) {
				//System.out.println("no mentioned users");
				return;
			}
		
		
			if(mentioned.size() > 1) {
				event.getChannel().sendMessage("Give me one user you bot");
				return;	
			}
			
			user = mentioned.get(0);
			stage++;
			
			r = new Random();
			int messageId = r.nextInt(5);
			
			
		}
	}

}
