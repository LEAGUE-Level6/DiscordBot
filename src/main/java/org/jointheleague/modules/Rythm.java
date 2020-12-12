package org.jointheleague.modules;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Rythm extends CustomMessageCreateListener{
	
	ServerVoiceChannel channel;
	private static final String COMMAND = "!play";

	public Rythm(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {
		channel = event.getMessageAuthor().getConnectedVoiceChannel().get();
		channel.connect().thenAccept(audioConnection -> {
		    // Do stuff
		}).exceptionally(e -> {
		    // Failed to connect to voice channel (no permissions?)
		    e.printStackTrace();
		    return null;
		});
		}
	}

}
