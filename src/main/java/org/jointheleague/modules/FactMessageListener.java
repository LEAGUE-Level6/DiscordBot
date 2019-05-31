package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class FactMessageListener extends CustomMessageCreateListener {
	
	String[] facts; // Our fun facts

	public FactMessageListener(String channelName) {
		super(channelName);
		facts = new String[] {
				"One Egyptian pharaoh had his eyes replaced with onions when he was mummified. *gross*",
				"McDonalds once made bubblegum-flavored broccoli. *BLECH*",
				"Scotland has 421 words for snow. *brrr*",
				"Armadillos are bulletproof! *BANG* *zing!*",
				"Kleenex tissues were once intended for gas masks.",
				"A high school student designed the American flag!",
				"Your nose and ears never stop growing.",
				"Trailers used to play after the movie.",
				"Theodore Roosevelt owned a hyena. *wow*",
				"Bananas glow blue when you shine UV light on them.",
				"Boars wash their food.",
				"A lady called the police when her ice cream didn't have enough sprinkles."
		};
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!fact")) {
			event.getChannel().sendMessage("Dude, I am summoning fun facts up from the depths of my ESSENCE. Here I go...");
			event.getChannel().sendMessage(facts[new Random().nextInt(facts.length)]);
			event.getChannel().sendMessage("*Source: https://www.rd.com/culture/interesting-facts/*");
		}
	}

}
