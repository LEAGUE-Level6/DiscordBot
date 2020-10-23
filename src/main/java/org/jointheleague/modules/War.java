package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class War extends CustomMessageCreateListener {
	private static final String COMMAND = "!fortune";
	private boolean gameStart = false;
	String month;
	String object;
	
	 public War (String channelName) {
		super(channelName);
		ArrayList<Integer> pD = new ArrayList<Integer>();
		ArrayList<Integer> bD = new ArrayList<Integer>();
		helpEmbed = new HelpEmbed(COMMAND, "Use ! + sign to recieve a prediction about your life!");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageAuthor().getName().equals("Ella's Bot")) {
		return;
		}
		
		String a = event.getMessageContent();
		if(a.equals("!War")) {
			gameStart = true;
			event.getChannel().sendMessage("Game of War started. Shuffling the deck.");
		
		}
	}
}