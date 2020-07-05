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
	int Status = 5;
	int Stamina = 5;
	int Smarts = 0; 
	Boolean started = false;
	long tStart = System.currentTimeMillis();
	
	public Tomagachi(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Don't let your tamagachi pet die and get them to maximum smartness to win! Run for a high score. You have multiple actions [eat, sleep, play, learn] to balence out your tomagachi's various needs [Hunger, Happiness, Stamina, Statue, and Intellegence]");
	}
	@Override
	public void handle(MessageCreateEvent event) {	
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
						do {
							try {
								event.getChannel().sendMessage("Attrition starting in 5");
								Thread.sleep(1000);
								event.getChannel().sendMessage("Attrition starting in 4");
								Thread.sleep(1000);
								event.getChannel().sendMessage("Attrition starting in 3");
								Thread.sleep(1000);
								event.getChannel().sendMessage("Attrition starting in 2");
								Thread.sleep(1000);
								event.getChannel().sendMessage("Attrition starting in 1");
								Thread.sleep(1000);
								event.getChannel().sendMessage("Attrition...-1 Happiness, -1 Stamina, -1 Hunger, -1 Intelligence ");
							}catch(InterruptedException e) {
								e.printStackTrace();
							}
							Happiness -= 1;
							Stamina -= 1;
							Hunger -= 1;
							Smarts -= 1;
							int total = 0;
							if (2 < Happiness && Happiness < 8) {
								total += 1;
							}else {
								total -= 2;
							}
							if (2 < Stamina && Stamina < 8) {
								total +=1;
							}else {
								total -= 2;
							}
							if (2 < Hunger && Hunger < 8) {
								total +=1;
							}else {
								total -= 2;
							}
							if (Status + total>10) {
								total = 10 - Status;
								Status = 10;
							}
							if (Status - total <0) {
								total = -(Status);
								Status = 0;
							}
							Status += total;
							System.out.println(Status);
							event.getChannel().sendMessage("Health Attrition..." + total +" Health");
							displayGraphs(event);
							try {
								Thread.sleep(15000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}while(escapeLoop(event));
					}
				};
				scheduler.schedule(thread, 20, TimeUnit.SECONDS);
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
		Status = 10;
		Stamina = 5;
		Smarts = 0; 
		started = true;
		tStart = System.currentTimeMillis();	
	}
	
	public void displayGraphs(MessageCreateEvent event) {
		String hungerBar =    "Hunger:          ";
		String happinessBar = "Happiness:    ";
		String StatusBar =    "Status:            ";
		String staminaBar =   "Stamina:        ";
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
			if(i<Status) {
				StatusBar += "▓";
			}else {
				StatusBar += "░";
			}
			if(i<Stamina) {
				staminaBar += "▓";
			}else {
				staminaBar += "░";
			}
			if(i<Smarts) {
				smartsBar += "▓";
			} else {
				smartsBar += "░";
			}
		}
		if(Hunger>=8) {
			hungerBar += " Too Full!";
		}else if(Hunger<=2) {
			hungerBar += " Too Hungry!";
		}
		if(Happiness>=8) {
			happinessBar += " Too Happy!";
		}else if(Happiness<=2) {
			happinessBar += " Too Sad!";
		}
		if(Stamina>=8) {
			staminaBar += " Too much Energy!";
		}else if(Stamina<=2) {
			staminaBar += " Too Weak!";
		}
		if(Status<=2) {
			StatusBar += " LOW HEALTH!";
		}
		event.getChannel().sendMessage(hungerBar + "\n\t\t\t\t\t\t " + happinessBar + "\n\t\t\t\t\t\t " + staminaBar + "\n\t\t\t\t\t\t " + StatusBar + "\n\t\t\t\t\t\t " +smartsBar);
	}
	public boolean escapeLoop(MessageCreateEvent event) {
		if (Status<=0||Smarts>=10) {
			if (Status<=0) {
				event.getChannel().sendMessage("You died: better luck next time.");
			}else {
				long tEnd = System.currentTimeMillis();
				event.getChannel().sendMessage("You have won! You completed the game in: " + ((int)((tEnd-tStart)/1000)));
			}
			started = false;
			return false;
		}
		return true;
	}
	public void feed(MessageCreateEvent event) {//refills the food bar by 3. lowers stamina by 2
		event.getChannel().sendMessage("Eating...+3 Hunger, -2 Stamina");
		Hunger += 3;
		Stamina -= 2;
		displayGraphs(event);
	}
	public void sleep(MessageCreateEvent event) {//refills stamina by 3, happiness by 1,  lowers hunger by 2, wait 5 seconds
		event.getChannel().sendMessage("Sleeping...+3 Stamina, -2 Hunger, 5 Sec Wait");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Stamina += 3;
		Hunger -= 2;
		displayGraphs(event);
	}
	public void play(MessageCreateEvent event) { //refills happiness by 3, lowers stamina by 2
		event.getChannel().sendMessage("Playing...+3 Happiness, -2 Stamina");
		Happiness += 3;
		Stamina -= 2;
		displayGraphs(event);
	}
	public void learn(MessageCreateEvent event) { //lowers happiness by 1, lowers stamina by 3, lowers food by 2, raises smarts by 2
		event.getChannel().sendMessage("Learning...-5 Happiness, -3 Stamina, -2 food, +2 Intelligence");
		Happiness -= 5;
		Stamina -= 3;
		Hunger -= 2;
		Smarts += 2;
		displayGraphs(event);
	}
}
