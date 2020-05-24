package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class RandomWord extends CustomMessageCreateListener{
	 static String[] strings = {"hi","bye","iphone","cats","Red",":watermelon:"};
	private static final String COMMAND = "!randomWord";
	public RandomWord(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
	if(event.getMessageContent().contains(COMMAND)) {
		String cmd = event.getMessageContent().replaceAll(" ", "").replace("!randomWord","");
		if(cmd.equals("")){
			Random r = new Random();

				event.getChannel().sendMessage("Your random word of the day is: " + strings[r.nextInt(6)] );
			
			
				
		}
		
		
	}
		
	}

}
