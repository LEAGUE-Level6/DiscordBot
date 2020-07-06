package org.jointheleague.modules;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;

public class NewIdeaChangeName extends CustomMessageCreateListener {
	
	

	private static final String spotifyCmnd = "!test";

	public NewIdeaChangeName(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event){
		
		// TODO Auto-generated method stub
		if (event.getMessageContent().equals(spotifyCmnd)) {
			JOptionPane.showMessageDialog(null, "test");
			
			
		}

	}

	
}
