package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class BrainFacts extends CustomMessageCreateListener {
	ArrayList<String>brain;
	public BrainFacts(String channelName) {
		super(channelName);
		brain = new ArrayList<String>();
		brain.add("https://www.thescienceofpsychotherapy.com/wp-content/uploads/2017/11/ventromedial.png" + 
		"\nDamage to the ventromedial prefrontal cortex makes "
		+ "it so that people lose their ability to associate emotions with moral reasoning.");
		brain.add("https://www.sciencemag.org/sites/default/files/styles/inline__450w__no_aspect/public/ca_1214NID_Brain_Bank_online.jpg?itok=StWRHpMY"
				+ "\nyour brain weighs approximately 3 pounds");
		brain.add("https://upload.wikimedia.org/wikipedia/commons/d/d1/Preserved_sperm_whale_brain.jpg"
				+ "\na sperm whale brain weighs approximately 17 pounds");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().startsWith("!brain")) {
			event.getChannel().sendMessage(brain.get(new Random().nextInt(brain.size())));
		}
		
		
	}
}
