package org.jointheleague.modules;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.javacord.api.event.message.MessageCreateEvent;

public class Riddle extends CustomMessageCreateListener {

	private Random r = new Random();
	private int random;

	public Riddle(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) {

		
		if (event.getMessageContent().contains("!riddle")) {
			random = r.nextInt(5);
			switch (random) {
			case 0:
				event.getChannel().sendMessage("The shorter I am, the bigger I am. What am I?");
				input(event,"Your temper");
				break;
			case 1:
				event.getChannel().sendMessage("What belongs to you, but others use it more than you do?");
				input(event,"Your name");
				break;
			case 2:
				event.getChannel().sendMessage("I am the beginning of sorrow and the end of sickness. There's no happiness without me nor is there sadness. I am always in risk, yet never in danger. You will find me in the sun, but I am never out of darkness.");
				input(event,"The letter s");
				break;
			case 3:
				event.getChannel().sendMessage("What binds two people yet touches only one?");
				input(event,"A wedding ring");
				break;
			case 4:
				event.getChannel().sendMessage("You could call me a home, but I have no doors. Someone lives inside me, but leaves when there's no more. What am I?");
				input(event,"An egg");
				break;
			}
		}

	}
	public void input(MessageCreateEvent event, String answer) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				event.getChannel().sendMessage(answer);
				t.cancel();
			}

		}, 5000);
	}

}
