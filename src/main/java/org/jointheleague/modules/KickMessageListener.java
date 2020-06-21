package org.jointheleague.modules;

import java.time.Instant;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;


public class KickMessageListener extends CustomMessageCreateListener {

	private static final String COMMAND = "!kick";
	private static boolean inPlay = false;
	private static String n = "";
	private User user;
	

	public KickMessageListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(!inPlay) {
			if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
				inPlay = true;
				n = event.getMessageAuthor().getDisplayName();
				event.getChannel().sendMessage("Who would you like to kick? Please add \"user \" before mentioning the user.");
			}
		}
		else if(inPlay) {
			if(event.getMessageAuthor().getDisplayName().equals(n)) {
				inPlay = false;
				if (event.getMessageContent().contains("user ")) {
					Message userMsg = event.getMessage();
					List<User> mentioned = userMsg.getMentionedUsers();
					user = mentioned.get(0);
					CompletableFuture<Void> cf = event.getServer().get().kickUser(user);
					
					if(cf.isCompletedExceptionally())
					event.getChannel().sendMessage("Failed to Kick \"" + user.getName() + "\"");
				
					else 
					event.getChannel().sendMessage("Done Kicking \"" + user.getName() + "\"");
				}
			}
		}
	}
}
