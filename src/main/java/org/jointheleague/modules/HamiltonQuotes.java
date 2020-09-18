package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class HamiltonQuotes extends CustomMessageCreateListener {
	
	private static final String COMMAND = "!hamilton";
	String[] hamquotes;

	public HamiltonQuotes(String channelName) {
		super(channelName);
		hamquotes = new String[] {
			"Pardon me, are you Aaron Burr, sir?",
			"If you stand for nothing, Burr, what'll you fall for?",
			"I am not throwing away my shot.",
			"Why should a tiny island across the sea regulate the price of tea?",
			"I will send a fully armed battalion to remind you of my love.",
			"I may not live to see our glory, but I will gladly join the fight.",
			"I'll rise above my station, organize your information.",
			"He has something to prove, he has nothing to lose.",
			"Immigrants, we get the job done.",
			"Man, the man is nonstop."
		};
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {
			System.out.println(event.getMessageAuthor());
			event.getChannel().sendMessage("Your Random Hamilton Quote: " + hamquotes[new Random().nextInt(hamquotes.length)]);
		}
	}
	
}
