package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class ElmoMessageListener extends CustomMessageCreateListener {
	
	ArrayList<String> elmos; // List of Elmo GIFs

	public ElmoMessageListener(String channelName) {
		super(channelName);
		elmos = new ArrayList<String>();
		
		// Adding the Elmo GIFs
		elmos.add("https://media.giphy.com/media/yr7n0u3qzO9nG/giphy.gif");
		elmos.add("https://media.giphy.com/media/csr9NH1g6qPRe/giphy.gif");
		elmos.add("https://media.giphy.com/media/l4HogOSqU3uupmvmg/giphy.gif");
		elmos.add("https://media.giphy.com/media/l3vR4Fp4U1DhW8bhS/giphy.gif");
		elmos.add("https://media.giphy.com/media/48U5cv2cdmK6Q/giphy.gif");
		elmos.add("https://media.giphy.com/media/12ttoBXEqixfmo/giphy.gif");
		elmos.add("https://media.giphy.com/media/BstmojEi81qg0/giphy.gif");
		elmos.add("https://media.giphy.com/media/3oEjHSuTCk0TBeLcGs/giphy.gif");
		elmos.add("https://media.giphy.com/media/3o6gDYtVxJ7t386pNe/giphy.gif");
		elmos.add("https://media.giphy.com/media/UI8tQzJNk8pVK/giphy.gif");
		elmos.add("https://media.giphy.com/media/3o7qDMFWSELevVIN7W/giphy.gif");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!elmo")) {
			event.getChannel().sendMessage(elmos.get(new Random().nextInt(elmos.size())));
		}
	}

}
