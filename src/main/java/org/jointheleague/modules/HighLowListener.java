package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class HighLowListener extends CustomMessageCreateListener {
	 private String c1 = "im";
	 private String c2 = "i'm";
	 private String c3 = "i am";
	
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
			event.getChannel().sendMessage("Choose a number between 1 and 100. I am going to guess this number. Respond with !higher or !lower, or if I got it !correct (case sensitive)");
			event.getChannel().sendMessage("Is your number 50?");
			play = true;
		}
		
			int guess = (low + high) / 2;
			
			if (a.equals("!higher")) {
				low = guess + 1;
				 guess = (low + high) / 2;
				event.getChannel().sendMessage("Is your number " + guess);
			}
			else if (a.equals("!lower")) {
				high = guess - 1;
				 guess = (low + high) / 2;
				 event.getChannel().sendMessage("Is your number " + guess);
			}
			else if (event.getMessageContent().equals("!correct")) {
				event.getChannel().sendMessage("Yay, I got it, your number was " + guess);
				play = false;
			}
			
		
		
		//if (s.contains(c1) && !event.getMessageAuthor().isYourself()) {
		//	int x = s.indexOf(c1)+3;
		//	event.getChannel().sendMessage("Hi "+a.substring(x)+", I'm Dad!");
		//}
		//if (s.contains(c2) && !event.getMessageAuthor().isYourself()) {
		//	int x = s.indexOf(c2)+4;
		//	event.getChannel().sendMessage("Hi "+a.substring(x)+", I'm Dad!");
		//}
		//if (s.contains(c3) && !event.getMessageAuthor().isYourself()) {
		//	int x = s.indexOf(c3)+5;
		//	event.getChannel().sendMessage("Hi "+a.substring(x)+", I'm Dad!");
		//}
	}
}
