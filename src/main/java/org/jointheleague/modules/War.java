package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class War extends CustomMessageCreateListener {
	private static final String COMMAND = "!fortune";
	boolean gameStart = false;
	String month;
	String object;
	ArrayList<Integer> cards;
	ArrayList<Integer> pD;
	ArrayList<Integer> bD;
	int turns;
	int pPoints;
	int bPoints;
	
	 public War (String channelName) {
		super(channelName);
		
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
			turns = 0;
			pPoints = 0;
			bPoints = 0;
			
			cards = new ArrayList<Integer>();
			for(int i = 1; i <= 13; i++) {
				for(int j = 0; j < 4; j++) {
					cards.add(i);	
				}
			}	
			int half = cards.size()/2;
			pD = new ArrayList<Integer>();
			bD = new ArrayList<Integer>();
			for(int i = 0; i < half; i++) {
				int index = new Random().nextInt(cards.size());
				pD.add(cards.get(index));
				System.out.println("ADDING "+cards.get(index)+" to player deck");
				cards.remove(index);
			}
			for(int i = 0; i < half; i++) {
				int index = new Random().nextInt(cards.size());
				bD.add(cards.get(index));
				cards.remove(index);
			}
			
		}
		if(a.equals("flip")) {
			for(int i = 0; i < pD.size(); i++) {
				System.out.println("PLAYER CARD 1: "+pD.get(i));
			}
			System.out.println();
			System.out.println("FLIP RESISTERED");
			event.getChannel().sendMessage("```Your card:   "+pD.get(turns)+"\nBot card:   "+bD.get(turns)+"```");
			if(pD.get(turns)>(bD.get(turns))) {
				pPoints++;
				event.getChannel().sendMessage("```Your card was greater than the Bot's! You win the round! Points: "+pPoints+"\nBot Points: "+bPoints+"```");
			}
			
			else if(pD.get(turns)<(bD.get(turns))){
				bPoints++;
				event.getChannel().sendMessage("```Your card was less than the Bot's. You lose the round! Points: "+pPoints+"\nBot Points: "+bPoints+"```");
			}
			else if (pD.get(turns)==(bD.get(turns))){
				event.getChannel().sendMessage("```Your cards are the same. This means War!```");
				event.getChannel().sendMessage("🂠🂠🂠");
			}
			turns++;
		}
	}
}