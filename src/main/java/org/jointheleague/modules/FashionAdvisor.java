package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class FashionAdvisor extends CustomMessageCreateListener {
	 private String c1 = "Wear all black!";
	 private String c2 = "Be colorful!";
	 private String c3 = "Look dapper today :)";
	 private String c4 = "Wear red, it's attractive ;)";
	 private String c5 = "Warm, cute, and cozy winter clothes";
	 private String c6 = "Summer clothes, show off your body!";
	 private String c7 = "Wear all white!";
	 private String c8 = "Wear gray tones";
	ArrayList<String> styles = new ArrayList<String>();
	

	public FashionAdvisor (String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		styles.add(c1);
		styles.add(c2);
		styles.add(c3);
		styles.add(c4);
		styles.add(c5);
		styles.add(c6);
		styles.add(c7);
		styles.add(c8);
		
		String a = event.getMessageContent();
		if(a.equals("!fashion")) {
			event.getChannel().sendMessage(styles.get(new Random().nextInt(8)));
		}
		
	}
}
