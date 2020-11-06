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
		int turns = 0;
		gameStart = false;
		String a = event.getMessageContent();
		ArrayList<Integer> cards = new ArrayList<Integer>();
		ArrayList<Integer> pD = new ArrayList<Integer>();
		ArrayList<Integer> bD = new ArrayList<Integer>();
		for(int i = 1; i <= 10; i++) {
			for(int j = 0; j < 4; j++) {
				cards.add(i);	
			}
		}	
		for(int i = 0; i < cards.size()/2; i++) {
			int index = new Random().nextInt(40-i);
			pD.add(cards.get(index));
			System.out.println("ADDING "+cards.get(index)+" to player deck");
			cards.remove(index);
		}
		for(int i = 0; i < 20; i++) {
			int index = new Random().nextInt(20-i);
			bD.add(cards.get(index));
			cards.remove(index);
		}

		if(a.equals("!War")) {
			gameStart = true;
			event.getChannel().sendMessage("Game of War started. Shuffling the deck.");

			
		}
		if(a.equals("flip")) {
			System.out.println("FLIP RESISTERED");
			event.getChannel().sendMessage("```Your card:   "+pD.get(turns)+"\nBot card:   "+bD.get(turns)+"```");
			if(pD.get(turns)>(pD.get(turns))) {
				event.getChannel().sendMessage("```Your card was greater than the Bot's! You win the round! Points: 1```");
			}
			else {
				event.getChannel().sendMessage("```Your card was less than the Bot's. You lose the round! Points: 0```");
			}
			turns++;
		}
	}
}