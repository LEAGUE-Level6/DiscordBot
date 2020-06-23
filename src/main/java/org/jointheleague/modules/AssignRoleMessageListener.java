package org.jointheleague.modules;

import java.util.List;

import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;


public class AssignRoleMessageListener extends CustomMessageCreateListener{
	Message userMsg;
	Message roleMsg;
	String username;
	User user = null;
	String roleName;
	Role role = null;
	Server server = null;
	int stage = 0;
	private static final String COMMAND = "!addrole";
	
	public AssignRoleMessageListener(String channelName) {
		super(channelName);	
	}

	public void handle(MessageCreateEvent event) {

		if(stage == 0 && event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			server = event.getServer().orElse(null);
			if(server ==  null) {
				System.out.println("no server");
				return;
			}
			
			if(!server.canYouManageRoles()) {
				event.getChannel().sendMessage("Sucks for you! You don't have perms to do this!");
				return;
			}
			
			event.getChannel().sendMessage("Who would you like to receive a role? Please add \"user \" before mentioning the user.");
			stage++;
			
		} 
		
		else if (stage == 1 && event.getMessageContent().contains("user ")){
			
			userMsg = event.getMessage();
			if(userMsg == null) {
				System.out.println("no user message");
				return;
			}
			
			List<User> mentioned = userMsg.getMentionedUsers();
			
			if(mentioned == null || mentioned.isEmpty()) {
				System.out.println("no mentioned users");
				return;
			}
		
		
			if(mentioned.size() > 1) {
				event.getChannel().sendMessage("Give me one user you bot");
				return;	
			}
			
			user = mentioned.get(0);
			stage++;
			event.getChannel().sendMessage("What role would you like to give them? Please add \"give role \" before mentioning the role.");
		
		} 
		
		else if (stage == 2 && event.getMessageContent().contains("give role ")) {

			roleMsg = event.getMessage();
			if(roleMsg == null) {
				System.out.println("no role message");
				return;
			}
			List<Role> roles = roleMsg.getMentionedRoles();
			
			if(roles == null || roles.isEmpty()) {
				System.out.println("no roles");
				return;
			}
			
			role = roles.get(0);
			List<Role> serverRoles = server.getRoles();
			if(!serverRoles.contains(role) || !server.canManageRole(user, role)) {
				event.getChannel().sendMessage("You cannot give that role to that person due to the role hierarchy.");
				return;
			}
			stage = 0;
			
			CompletableFuture<Void> result = server.addRoleToUser(user, role);
			while(!(result.isDone())) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(result.isCompletedExceptionally() || result.isCancelled()) {
				event.getChannel().sendMessage("failed to complete");
			} else {
				event.getChannel().sendMessage("Role Given!");
			}
		}	
	}
}


