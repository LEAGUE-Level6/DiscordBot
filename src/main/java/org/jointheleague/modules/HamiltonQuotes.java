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
			"Looks proximity to power.",
			"If the tomcat can get married, there's hope for our *ss afterall.",
			"He has something to prove, he has nothing to lose.",
			"He sh*ts the bed at the Battle of Monmouth.",
			"Look 'em in the eye, aim no higher, summon all the courage you require.",
			"I'm notcha son.",
			"Immigrants, we get the job done.",
			"You have no control who lives who dies who tells your story.",
			"Man, the man is nonstop.",
			"Where have you been? / Uh, France?",
			"Madison, you mad as a Hatter, son take your medicine!",
			"When you got skin in the game you stay in the game, but you won't get a win unless you play in the game.",
			"Uh do whatever you want, I'm super dead.",
			"I wanna sit under my own vine and fig tree, a moment alone in the shade.",
			"This is the eye of the hurricane, this is the only way I can protect my legacy.",
			"We push away the unimaginable.",
			"When Alexander aimed at the sky, he may have been the first one to die, but I'm the one who paid for it."
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
