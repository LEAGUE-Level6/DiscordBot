package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Bingo extends CustomMessageCreateListener{

	private static final String BINGO = "!bingo";
	int bingoCard[][] = new int[5][5];
	ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
	ArrayList<Integer> randomNumArr = new ArrayList<Integer>();
	int random;
	
	public Bingo(String channelName) {
		super(channelName);
		
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		
		if(message.equalsIgnoreCase("!bingo")) {
			event.getChannel().sendMessage("this is a test");
			
			while(randomNumbers.size() < 60) {
				random = (int)(Math.random()*100 + 1);
				if (!randomNumbers.contains(random)) {
					randomNumbers.add(random);
				} 
			}
			System.out.println(randomNumbers);
		}
		
		for (int i = 0; i < bingoCard.length; i++) {
			for (int j = 0; j < bingoCard[i].length; j++) {
				bingoCard[i][j] = randomNumbers.get(random);
			}
		}
		
		}
		
	}


