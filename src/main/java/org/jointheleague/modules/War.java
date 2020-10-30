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
		
		helpEmbed = new HelpEmbed(COMMAND, "Use ! + sign to recieve a prediction about your life!");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageAuthor().getName().equals("Ella's Bot")) {
		return;
		}
		gameStart = true;
		String a = event.getMessageContent();
		ArrayList<Integer> cards = new ArrayList<Integer>();
		ArrayList<Integer> pD = new ArrayList<Integer>();
		ArrayList<Integer> bD = new ArrayList<Integer>();
		for(int i = 1; i <= 10; i++) {
			for(int j = 0; j < 4; j++) {
				cards.add(i);
				
		}
		}
		for(int i = 0; i < 20; i++) {
			int index = new Random().nextInt(20);
			pD.add(cards.get(index));
			cards.remove(index);
		}
		for(int i = 0; i < 20; i++) {
			int index = new Random().nextInt(20);
			bD.add(cards.get(index));
			cards.remove(index);
		}
		if(a.equals("!War")) {
			int turns = 0;
			event.getChannel().sendMessage("Game of War started. Shuffling the deck.");

			
		}
		if(a.equals("flip") && gameStart) {
			event.getChannel().sendMessage("```Your card:   "+pD.get(1)+"\nBot card:   "+bD.get(3)+"```");
		}
	}
}