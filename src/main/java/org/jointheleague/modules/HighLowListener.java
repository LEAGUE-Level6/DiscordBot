package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class HighLowListener extends CustomMessageCreateListener {
	private String c1 = "im";
	private String c2 = "i'm";
	private String c3 = "i am";
	String HIGH ="";
	String LOW ="";

	int high = 100;
	int low = 0;

	public HighLowListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String a = event.getMessageContent();
		boolean play = false;

		if (a.equals("!highlowgame")) {
			high = 100;
		    low = 0;
		    HIGH ="!higher";
		    LOW = "!lower";
			event.getChannel().sendMessage(
					"Choose a number between 1 and 100. I am going to guess this number. Respond with !higher or !lower, or if I got it !correct (case sensitive)");
			event.getChannel().sendMessage("Is your number 50?");
			play = true;
		}

		int guess = (low + high) / 2;

		if (a.equals(HIGH)) {
			low = guess + 1;
			guess = (low + high) / 2;
			event.getChannel().sendMessage("Is your number " + guess);
		} else if (a.equals(LOW)) {
			high = guess - 1;
			guess = (low + high) / 2;
			event.getChannel().sendMessage("Is your number " + guess);
		} else if (event.getMessageContent().equals("!correct")) {
			HIGH ="";
			LOW ="";
			event.getChannel().sendMessage("Yay, I got it, your number was " + guess);
			play = false;
		}

	}
}
