package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.server.role.RoleDeleteListener;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
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
		TextChannel channel = event.getChannel();
		String input = event.getMessageContent();
		User user = event.getMessageAuthor().asUser().get();
		Server server = event.getServer().get();
		StringBuilder stringBuilder = new StringBuilder();
		if (input.startsWith("!role")) {
			if (input.equals("!role")) {
				stringBuilder.append("This command can edit your roles: \n");
				stringBuilder.append("use **!role set [Role Name]** to set a role, and **!role remove [Role Name]** to remove a role\n");				
				stringBuilder.append("The current editable roles are: \n");
				stringBuilder.append("```\n");
				for(Role r : server.getRoles()) {
					if(r.getName().startsWith(ROLE_PREFIX)) {
						stringBuilder.append(r.getName().replaceAll(ROLE_PREFIX, "")+"\n");
					}
				}
				stringBuilder.append("```");
				channel.sendMessage(stringBuilder.toString());
			}
			
			if (input.startsWith("!role set")) {
				String argument = input.replaceAll("!role set ", "");
				roleCheck(channel, argument, ("You have been given the " + argument + " role."));
				addRole(user, server, roles.get(argument));
			}
			if (input.startsWith("!role remove")) {
				String argument = input.replaceAll("!role remove ", "");
				roleCheck(channel, argument, ("You have removed the " + argument + " role."));
				removeRole(user, server, roles.get(argument));
			}
			if (input.startsWith("!role create")) {
				if (user.getId()==422983171471179790L) {
					String argument = input.replaceAll("!role create ", "");
					String[] str = argument.split(" ");
					if (!roles.containsValue(roles.get(str[0]))) {
						try{
							if (str.length == 4) {
								
								if (str[0].matches("^[a-zA-Z_]+$")) {
									rB.setName(ROLE_PREFIX+str[0]);
								} else throw new Exception("Invalid color argument, typed " + str[0] +", expected String argument");
						
								rB.setColor(new Color(Integer.parseInt(str[1]), Integer.parseInt(str[2]), Integer.parseInt(str[3])));
								CompletableFuture<Role> cf = rB.create();
								roles.put(str[0], cf.get());
								channel.sendMessage("You have created a role with the name " + str[0] + ", and the rgb values of " + 
										str[1] + " " + str[2] + " " + str[3] + " ");
							}
						else throw new Exception("Incorrect number of arguments: expected 4, recieved " + str.length);
						} catch (Exception e) {
							channel.sendMessage(e.getMessage());
						}
					}
					else {channel.sendMessage("That role already exists!");}
				}
				else {channel.sendMessage("You don't have the authority to use this command :/");}
			}
			if (input.startsWith("!role delete")) {
				if (user.getId()==422983171471179790L) {
					String argument = input.replaceAll("!role delete ", "");
					roleCheck(channel, argument, ("You have deleted the " + argument + " role."));
					roles.get(argument).delete();
					roles.remove(argument);
				}
				else {channel.sendMessage("You don't have the authority to use this command :/");}
			}
		}
	}
	
	private void removeRole(User user, Server server, Role role) {
		System.out.println(user);
		System.out.println(server);
		user.removeRole(role);
		System.out.println(role);
	}
	
	private void roleCheck(TextChannel channel, String argument, String message) {
		if (roles.containsValue(roles.get(argument))) {
			channel.sendMessage(message);
		}
		else channel.sendMessage("That role doesn't exist!");
	}

	private void addRole(User user, Server server, Role role) {
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
