package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class TicTacToe extends CustomMessageCreateListener{
	private static final String COMMAND = "!TicTacToe";
	final String TopLeft = "!TopLeft";
	final String TopMiddle = "!TopMiddle";
	final String TopRight = "!TopRight";
	final String MiddleLeft = "!MiddleLeft";
	final String MiddleMiddle = "!MiddleMiddle";
	final String MiddleRight = "!MiddleRight";
	final String LeftLeft = "!LeftLeft";
	final String LeftMiddle = "LeftMiddle";
	final String LeftRight = "LeftRight";
	
	ArrayList<String>commands = new ArrayList<String>();

	public TicTacToe(String channelName) {
		super(channelName);
		
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		commands.add(TopLeft);
		commands.add(TopMiddle);
		commands.add(TopRight);
		commands.add(MiddleLeft);
		commands.add(MiddleMiddle);
		commands.add(MiddleRight);
		commands.add(LeftLeft);
		commands.add(LeftMiddle);
		commands.add(LeftRight);
		
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith(COMMAND)) {
			for (int i = 0; i < 9; i++) {
				if(i%2 == 0) {
				event.getChannel().sendMessage("Choose where you want to place the X");
				String location = event.getMessageContent();
				for (int j = 0; j < commands.size(); j++) {
					if(location == commands.get(j)) {
						commands.remove(j);
						break;
					}
				}
				}
				if(i%2 == 1) {
					Random r = new Random();
					r.nextInt(commands.size());
					commands.remove(r);
				}
				if(i>=4) {
					
				}
				
				
			}
			
		}
	}
}
