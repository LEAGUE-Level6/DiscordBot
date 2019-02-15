package org.jointheleague.discordBotExample;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Bot {
	private String token;
	DiscordApi api;
	
	//Modules: alphabetical order please!
	public Bot(String token) {
		this.token = token;
	}

	public void connect() {
		api = new DiscordApiBuilder().setToken(token).login().join();
		System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
		api.addMessageCreateListener(event -> {
			System.err.println(event.getMessage());
			if (event.getMessageContent().equalsIgnoreCase("!ping")) {
				event.getChannel().sendMessage("Pong!");
			}
		});
	}
}
