package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class RoleBot extends CustomMessageCreateListener {

	private static final String ROLE_PREFIX = "color_";
	private final Map<String ,Role> roles;
	private final Server server;
	RoleBuilder rB;
	
	
	
	public RoleBot(String channelName, Optional<Server> server) {
		super(channelName);
		this.server=server.get();
		rB = new RoleBuilder(this.server);
		roles = new HashMap<>();
		refreshRoles();
	}
	
	public void refreshRoles() {
		for(Role r : server.getRoles()) {
			if(r.getName().startsWith(ROLE_PREFIX)) {
				roles.put(r.getName().replaceAll(ROLE_PREFIX, ""), r);
			}
		}
	}
		
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!role")) {
			if (event.getMessageContent().equals("!role")) {
				event.getChannel().sendMessage("This command can edit your roles: ");
				event.getChannel().sendMessage("use **!role set [Role Name]** to set a role, and **!role remove [Role Name]** to remove a role");				
				event.getChannel().sendMessage("The current editable roles are: ");
				for(Role r : server.getRoles()) {
					if(r.getName().startsWith(ROLE_PREFIX)) {
						event.getChannel().sendMessage(r.getName().replaceAll(ROLE_PREFIX, ""));
					}
				}
			}
			
			if (event.getMessageContent().startsWith("!role set")) {
				String input = event.getMessageContent().replaceAll("!role set ", "");
				addRole(event.getMessageAuthor().asUser(), event.getServer(), roles.get(input));
			}
			if (event.getMessageContent().startsWith("!role remove")) {
				String input = event.getMessageContent().replaceAll("!role remove ", "");
				removeRole(event.getMessageAuthor().asUser(), event.getServer(), roles.get(input));
			}
			if (event.getMessageContent().startsWith("!role create")) {
				if (event.getMessageAuthor().asUser().get().getId()==422983171471179790L) {
					String input = event.getMessageContent().replaceAll("!role create ", "");
					String[] str = input.split(" ");
					try{
						if (str.length == 4) {
							
						if (str[0].matches("^[a-zA-Z]+$")) {
							rB.setName(ROLE_PREFIX+str[0]);
						} else throw new Exception("Invalid color argument, typed " + str[0] +", expected String argument");
						
						rB.setColor(new Color(Integer.parseInt(str[1]), Integer.parseInt(str[2]), Integer.parseInt(str[3])));
						CompletableFuture<Role> cf = rB.create();
						roles.put(str[0], cf.get());
						}
						else throw new Exception("Incorrect number of arguments: expected 4, recieved " + str.length);
					} catch (Exception e) {
						event.getChannel().sendMessage(e.getMessage());
					}
				}
			}
			
		}
	}
	
	private void removeRole(Optional<User> userop, Optional<Server> serverop, Role role) {
		Server server = serverop.get();
		User user = userop.get();
		System.out.println(user);
		System.out.println(server);
		user.removeRole(role);
		System.out.println(role);
	}

	private void addRole(Optional<User> userop, Optional<Server> serverop, Role role) {
		Server server = serverop.get();
		User user = userop.get();
		System.out.println(user);
		System.out.println(server);
		List<Role> roles = user.getRoles(server);
		for (Role r : roles) {
			if(r.getName().startsWith(ROLE_PREFIX)) {
				user.removeRole(r);
			}
		}
		user.addRole(role);
		System.out.println(role);
	}
	
}
