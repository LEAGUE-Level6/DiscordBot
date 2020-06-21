package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class ComplimentListener extends CustomMessageCreateListener {
	Random rand = new Random();

	public ComplimentListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!luna")) {
			event.getChannel().sendMessage("Hi, I'm Luna.");
		} else if (event.getMessageContent().contains("!compliment")) {
			event.getChannel().sendMessage(compliments[rand.nextInt(compliments.length)]);
			event.getChannel().sendMessage("I hope you have a great day! (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧");
		};
		
	}
	
	String[] compliments = {"You look great today.",
			"You’re a smart cookie.",
			"I like your style.",
			"You light up the room.",
			"You have a great sense of humor.",
			"You’re more helpful than you realize.",
			"On a scale from 1 to 10, you’re an 11.",
			"You’re even more beautiful on the inside than you are on the outside.",
			"You’re like sunshine on a rainy day.",
			"You’re a great listener.",
			"Being around you makes everything better!",
			"You’re one of a kind!",
			"You’re wonderful.",
			"You have the best ideas.",
			"You should be thanked more often. So thank you!!",
			"You’re a great example to others.",
			"You’re more fun than bubble wrap.",
			"The people you love are lucky to have you in their lives.",
			"There’s ordinary, and then there’s you.",
			"You’re someone’s reason to smile."};

}
