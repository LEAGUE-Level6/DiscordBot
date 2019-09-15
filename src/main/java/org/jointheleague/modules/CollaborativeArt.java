package org.jointheleague.modules;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class CollaborativeArt extends CustomMessageCreateListener{
	
	private static final String COMMAND3 = "!storystart";
	private static final String COMMAND2 = "!storyreset";
	private static final String COMMAND1 = "!storyedit";
	private String body = "";
	private String name = "";

	public CollaborativeArt(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND1)) {
			String s2 = event.getMessageContent().substring( event.getMessageContent().indexOf('!')+10);
			body = body + s2;
			event.getChannel().sendMessage(name + " So Far: \n" + body);
		}
		if (event.getMessageContent().contains(COMMAND2)) {
			body = "";
			name = "";
			event.getChannel().sendMessage("Everything has been reset!");
		}
		if (event.getMessageContent().contains(COMMAND3)) {
			name = event.getMessageContent().substring( event.getMessageContent().indexOf('!')+12);
			event.getChannel().sendMessage(name + " So Far: \n" + body);
		}
	}



}
