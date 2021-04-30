package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Dice extends CustomMessageCreateListener {
	final String dice_command = "!diceformat";
	final String roll_command = "!roll";
	public Dice(String channelName) {
		// TODO Auto-generated constructor stub
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().equals(dice_command)) {
			event.getChannel().sendMessage("format: !roll # #-sided");
		}
		if(event.getMessageContent().startsWith(roll_command)) {
			String variables = event.getMessageContent().substring(6);
			HandleRoll(event, variables);
		}
	}

	private void HandleRoll(MessageCreateEvent event, String variables) {
		// TODO Auto-generated method stub
		String[] test = variables.split(" ");
		int numDice = Integer.parseInt(test[0]);
		String[] digits = test[1].split("-");
		int sides = Integer.parseInt(digits[0]); 
		Random ran = new Random();
		for (int i = 0; i < numDice; i++) {
			int values = ran.nextInt(sides);
			event.getChannel().sendMessage("Dice "+(i+1)+": "+(values+1));
			
		}
		
	}

}
