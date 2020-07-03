package org.jointheleague.modules;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class Tomagachi extends CustomMessageCreateListener {
	private static final String COMMAND = "!toma";
	int Hunger = 5; 
	int Happiness = 5;
	int Health = 5;
	int Stamina = 5;
	int Smarts = 0; 
	Boolean dead = false;
	Boolean started = false;
	long tStart = System.currentTimeMillis();
	
	public Tomagachi(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Don't let your tamagachi pet die and get them to maximum smartness! Run for a high score.");
	}
	@Override
	public void handle(MessageCreateEvent event) {	
		long tEnd = System.currentTimeMillis();
		int multiples = 1;
		//add a die and record score clause
			// how to do this entire loop better
		if (event.getMessageContent().contains(COMMAND)) {
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!Tomagachi","");
			if (cmd.contains("Start") || cmd.contains("start")) {
				reset();
				displayGraphs(event);
				ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
				Runnable thread = new Runnable(){
					public void run() { //runnable can already see event and thus doesn't need to implement it // every 15 secs lowers everything by 1
						while(true) {
							if (2 < Happiness && Happiness < 8) {
								Health +=1;
							}else {
								Health -= 2;
							}
							if (2 < Stamina && Stamina < 8) {
								Health +=1;
							}else {
								Health -= 2;
							}
							if (2 < Hunger && Hunger < 8) {
								Health +=1;
							}else {
								Health -= 2;
							}
							if (Health>10) {
								Health = 10;
							}else if (Health<0) {
								Health = 0;
							}
							Happiness -= 1;
							Stamina -= 1;
							Hunger -= 1;
							Smarts -= 1;
							displayGraphs(event);
							try {
								Thread.sleep(15000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				};
				scheduler.schedule(thread, 15, TimeUnit.SECONDS);
			}
			if(started) {
				if(cmd.contains("Eat") || cmd.contains("eat")) {
					feed(event);
				}
				if(cmd.contains("Sleep") || cmd.contains("sleep")) {
					sleep(event);
				}
				if(cmd.contains("Play") || cmd.contains("play")) {
					play(event);
				}
				if(cmd.contains("Learn") || cmd.contains("learn")) {
					learn(event);
				}
			}else {
				event.getChannel().sendMessage("\nYou must start the game first...\n");
			}
		}
	}
	private void reset() {
		Hunger = 5; 
		Happiness = 5;
		Health = 5;
		Stamina = 5;
		Smarts = 0; 
		dead = false;
		started = true;
		tStart = System.currentTimeMillis();	
	}
	
	public void displayGraphs(MessageCreateEvent event) {
		String hungerBar =    "Hunger:       ";
		String happinessBar = "Happiness:    ";
		String healthBar =    "Health:       ";
		String staminaBar =   "Stamina:      ";
		String smartsBar =    "Intellegence: ";
		for (int i = 0; i < 10; i++) {
			if(i<Hunger) {
				hungerBar += "▓";
			}else {
				hungerBar += "░";
			}
			if(i<Happiness) {
				happinessBar += "▓";
			}else {
				happinessBar += "░";
			}
			if(i<Health) {
				healthBar += "▓";
			}else {
				healthBar += "░";
			}
			if(i<Stamina) {
				staminaBar += "▓";
			}else {
				staminaBar += "░";
			}
			if(i<Smarts) {
				smartsBar += "▓";
			}else {
				smartsBar += "░";
			}
		}
		event.getChannel().sendMessage(hungerBar);
		event.getChannel().sendMessage(happinessBar);
		event.getChannel().sendMessage(staminaBar);
		event.getChannel().sendMessage(healthBar);
		event.getChannel().sendMessage(smartsBar);
	}
	public boolean escapeLoop() {
		if (dead||Smarts>=10) {
			return false;
		}
		return true;
	}
	public void feed(MessageCreateEvent event) {//refills the food bar by 3. lowers stamina by 2
		event.getChannel().sendMessage("\nEating...\n");
		Hunger += 3;
		Stamina -= 2;
		displayGraphs(event);
	}
	public void sleep(MessageCreateEvent event) {//refills stamina by 3, happiness by 1,  lowers hunger by 2, wait 5 seconds
		event.getChannel().sendMessage("\nSleeping...\n");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Stamina += 3;
		Happiness += 1;
		Hunger -= 2;
		displayGraphs(event);
	}
	public void play(MessageCreateEvent event) { //refills happiness by 3, lowers stamina by 2
		event.getChannel().sendMessage("\nPlaying...\n");
		Happiness += 3;
		Stamina -= 2;
		displayGraphs(event);
	}
	public void learn(MessageCreateEvent event) { //lowers happiness by 1, lowers stamina by 3, lowers food by 2, raises smarts by 2
		event.getChannel().sendMessage("\nLearning...\n");
		Happiness -= 1;
		Stamina -= 3;
		Hunger -= 2;
		Smarts += 2;
		displayGraphs(event);
	}
}
