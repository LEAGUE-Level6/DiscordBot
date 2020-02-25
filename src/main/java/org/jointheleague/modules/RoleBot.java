package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class RoleBot extends CustomMessageCreateListener {

	private static final String ROLE_PREFIX = "color_";
	private final Map<String ,Role> roles;
	
	public RoleBot(String channelName, Optional<Server> server) {
		super(channelName);
		roles = new HashMap<>();
		for(Role r : server.get().getRoles()) {
			if(r.getName().startsWith(ROLE_PREFIX)) {
				roles.put(r.getName().replaceAll(ROLE_PREFIX, ""), r);
			}
		}
	}

	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!role")) {
			if (event.getMessageContent().equals("!role")) {
				event.getChannel().sendMessage("This command can edit your roles: ");
				event.getChannel().sendMessage("use **!role add [Role Name]** to add a role, and **!role remove [Role Name]** to remove a role");				
				event.getChannel().sendMessage("The current editable roles are: ");
				event.getChannel().sendMessage("```\nred\nyellow\nblue```");
			}
			
			String input = event.getMessageContent().replaceAll("!role add ", "");
			roles.get(input);
			
		}
	}
	
	private void addRole(User user, Optional<Server> optional, String role) {
		Server server = optional.get();
		System.out.println(user);
		System.out.println(server);
		user.addRole(server.getRolesByName(role).get(0));
		System.out.println(server.getRolesByName(role));
	}
	
}
