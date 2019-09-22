package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class Screamer extends CustomMessageCreateListener {

	private static final String[] COMMANDS = {"scream", "dats scawry", "dats sad", "spider", "spiders", "those them there be spids"};
	private static final String[] NOUNS = {"Matt", "Mike", "Cody", "a spider", "a flying merchant from the moon", "a living human????", "THE LEAGUE OF AMAZING PROGRAMMERS", "not even alive", "death", "me, but I am older", "me, but I am poor", "my ladybug wallclimber lossing its stickyness"};
	
	
	public Screamer(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String mes = event.getMessageContent();
		boolean contains = false;
		for (int i = 0; i < COMMANDS.length; i++)
		{
			if (mes.toLowerCase().contains(COMMANDS[i])) contains = true;
		}
		if (contains == true)
		{
			Random r = new Random();
			int numberOfAs = r.nextInt(400);
			int numberOfHs = r.nextInt(400);
			String scream = "";
			for (int i = 0; i < numberOfAs + 800; i++)
			{
				scream+=(r.nextInt()%2==0)? "a" : "A";
			}
			for (int i = 0; i < numberOfHs + 800; i++)
			{
				scream+=(r.nextInt()%2==0) ? "h" : "H";
			}
			scream+="!";
			scream+="\nIts " + NOUNS[r.nextInt(NOUNS.length)];
			event.getChannel().sendMessage(scream);
		}
	}
	
}