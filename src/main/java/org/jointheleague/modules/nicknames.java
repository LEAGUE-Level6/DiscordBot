package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class nicknames extends CustomMessageCreateListener {
	private String input;
	private final String CMD = "!nickname";
	private final String INP = "!helpNickname";

	// String name1 = input + "iddle";
	// String name2 = input + "izzle";
	// String name3 = input + "able";

	public nicknames(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		Random randy = new Random();
		String[] suffixes = { "able", "izzle", "iddle", "gummy", "tude",  };
		
		String input = event.getMessageContent();
		
		String[] strArray = input.split(" ");
		//creates nickname
		if (strArray[0].equals(CMD)) {
			String name = strArray[1];
			event.getChannel().sendMessage(name + suffixes[randy.nextInt(suffixes.length)]);

		} else if (strArray[0].contains(INP)) {
			//help message
			event.getChannel().sendMessage("Format your messages like: !nickname ~your name here~");
		}// else {
			//error message
			//event.getChannel().sendMessage("Please format your answers correctly use !helpNickname for instructions");
		//}
	}

}
