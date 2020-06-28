package org.jointheleague.modules;

import java.time.Instant;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;


public class NicknameListener extends CustomMessageCreateListener {

	private static final String COMMAND = "!nick";
	private static int stage = 0;
	private static String n = "";
	private User user;
	

	public NicknameListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(stage==0) {
			if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
				stage = 1;
				n = event.getMessageAuthor().getDisplayName();
				event.getChannel().sendMessage("Who would you like to nickname? Please add \"user \" before mentioning the user.");
			}
		}
		else if(stage==1) {
			if(event.getMessageAuthor().getDisplayName().equals(n)) {
				stage = 2;
				if (event.getMessageContent().contains("user ")) {
					Message userMsg = event.getMessage();
					List<User> mentioned = userMsg.getMentionedUsers();
					user = mentioned.get(0);
					event.getChannel().sendMessage("If you would like to simply reset their nickname, just type \"reset\". Otherwise, add \"name \" followed by what you want to nickname them.");
				}
			}
		}
		else if(stage==2) {
			if(event.getMessageAuthor().getDisplayName().equals(n)) {
				stage = 0;
				if (event.getMessageContent().contains("name ")) {
					Message userMsg = event.getMessage();
					CompletableFuture<Void> cf = event.getServer().get().updateNickname(user, userMsg.getContent().substring(5));			
					if(cf.isCompletedExceptionally())
						event.getChannel().sendMessage("Failed to Nickname \"" + user.getName() + "\"");
					else
						event.getChannel().sendMessage("Done Nicknaming \"" + user.getName() + "\"");
				}
				if (event.getMessageContent().contains("reset")) {
					CompletableFuture<Void> cf2 = event.getServer().get().resetNickname(user);	
					if(cf2.isCompletedExceptionally())
						event.getChannel().sendMessage("Failed to Reset \"" + user.getName() + "\"");
					else
						event.getChannel().sendMessage("Done Reseting \"" + user.getName() + "\"");
				}
			}
		}
	}
}
