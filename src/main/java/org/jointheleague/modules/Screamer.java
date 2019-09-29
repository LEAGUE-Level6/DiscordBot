package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.entity.message.Message;
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
			if (mes.toLowerCase().contains(COMMANDS[i])) 
			{
				contains = true;
				break;
			}
		}
		if (contains)
		{
			Random r = new Random();
			int numberOfAs = r.nextInt(300);
			int numberOfHs = r.nextInt(300);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < numberOfAs + 400; i++)
			{
				sb.append((r.nextInt(2)==0)? "a" : "A");
			}
			for (int i = 0; i < numberOfHs + 400; i++)
			{
				sb.append((r.nextInt(2)==0) ? "h" : "H");
			}
			sb.append("!\nIts " + NOUNS[r.nextInt(NOUNS.length)]);
			CompletableFuture<Message> compFuture = event.getChannel().sendMessage(sb.toString());
			try {
				compFuture.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
