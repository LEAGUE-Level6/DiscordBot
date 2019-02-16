package org.jointheleague.discordBotExample;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.event.connection.ReconnectEvent;
import org.javacord.api.listener.connection.ReconnectListener;

public class Bot {
	private String token;
	private String channel;
	DiscordApi api;

	// Modules: alphabetical order please!

	public Bot(String token, String channel) {
		this.token = token;
		this.channel = channel;
	}

	public void connect() {
		api = new DiscordApiBuilder().setToken(token).login().join();
		System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
		api.getServerTextChannelsByName(channel).forEach(e->e.sendMessage("Bot Connected"));
		for (ServerTextChannel channel : api.getServerTextChannelsByName(channel)) {
			  channel.sendMessage("Hi");
			}
		api.addMessageCreateListener(event -> {
			System.out.println(event.getMessageAuthor().getName());
			if (event.getServerTextChannel().get().getName().equals(channel)) {
				if (event.getMessageContent().equalsIgnoreCase("!ping")) {
					event.getChannel().sendMessage("Pong!");
				}
			}
		});
	}
}
