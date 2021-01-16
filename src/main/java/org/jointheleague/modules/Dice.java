package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Dice extends CustomMessageCreateListener{
	private static final String COMMAND1 = "!roll";
	private static final String COMMAND2 = "!format";
	private static final String COMMAND3 = "!reroll";
	private static final String COMMAND4 = "!beNice";
	private static final String COMMAND5 = "!playSound";
	int randy = 0;
	int tempRandy = 0;
	int tempRandy2 = 0;
	int modifier = 0;
	int sides = 0;
	boolean adv = false;
	boolean dis = false;
	boolean beNice = false;
	public Dice(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String eventContent = event.getMessageContent();
		
//		ServerVoiceChannel channel = ["General"];
//		channel.connect().thenAccept(audioConnection -> {
//		    // Do stuff
//		}).exceptionally(e -> {
//		    // Failed to connect to voice channel (no permissions?)
//		    e.printStackTrace();
//		    return null;
//		});
		
		if(eventContent.equals(COMMAND5)) {
			
		}
		if(eventContent.equals(COMMAND4)) {
			if(beNice) {
				beNice = false;
			}
			else {
				beNice = true;
			}
		}
		if(!event.getMessageAuthor().getName().contentEquals("Beckham's Bot")&&beNice) {
			event.getChannel().sendMessage("Hello "+event.getMessageAuthor().getName());
		}
		if(eventContent.equals(COMMAND3)) {
			if(adv) {
				randy = new Random().nextInt(sides)+1;
				tempRandy = new Random().nextInt(sides)+1;
				if(tempRandy>randy) {
					randy = tempRandy;
				}
				if(sides ==20&&randy == 20){
					event.getChannel().sendMessage("You rolled a natural 20!!!");
					}
				else if(sides ==20&&randy == 1){
					event.getChannel().sendMessage("You rolled a natural 1 :(");
					}
				display2(event);
				displayFinal(event);
			}
			else if (dis) {
				randy = new Random().nextInt(sides)+1;
				tempRandy = new Random().nextInt(sides)+1;
				if(tempRandy<randy) {
					randy = tempRandy;
				}
				display2(event);
				displayFinal(event);
			}
			else if(!dis&&!adv) {
				randy = new Random().nextInt(sides)+1;
				if(sides ==20&&randy == 20){
					event.getChannel().sendMessage("You rolled a natural 20!!!");
					}
				else if(sides ==20&&randy == 1){
					event.getChannel().sendMessage("You rolled a natural 1 :(");
					}
				display1(event);
			}
		}
		else if(eventContent.contains(COMMAND1)||eventContent.contains(COMMAND2)) {
		adv = false;
		dis = false;
		modifier = 0;
		}
		if(eventContent.contains("ADV")) {
			adv = true;
		}
		else if(eventContent.contains("DIS")) {
			dis = true;
		}
		else if(eventContent.contains(COMMAND2)) {
			event.getChannel().sendMessage("To roll a dice use this format:\n!roll d(any number)+(modifier (This is optional))ADV/DIS (optional, one or the other)\nYou may also choose to use the format !roll 2d(any number) or !roll 3d(anynumber)");
		}
		if (eventContent.contains(COMMAND1)) {
			if(eventContent.contains("+")&&!adv&&!dis) {
				modifier = Integer.parseInt(eventContent.substring(eventContent.indexOf("+"), eventContent.length()));
				if(eventContent.contains("d")) {
					sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.indexOf("+")));
					randy = new Random().nextInt(sides)+1;
					if(sides ==20&&randy == 20){
					event.getChannel().sendMessage("You rolled a natural 20!!!");
					}
					else if(sides ==20&&randy == 1){
						event.getChannel().sendMessage("You rolled a natural 1 :(");
						}
					display1(event);
				}
			}
			else if(eventContent.contains("2d")&&!adv&&!dis) {
				if(eventContent.contains("d")) {
					sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.length()));
					randy = new Random().nextInt(sides)+1;
					tempRandy = new Random().nextInt(sides)+1;
					display2(event);
					displayTotal(event);
				}
			}
			else if(eventContent.contains("3d")&&!adv&&!dis) {
				if(eventContent.contains("d")) {
					sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.length()));
					randy = new Random().nextInt(sides)+1;
					tempRandy = new Random().nextInt(sides)+1;
					tempRandy2 = new Random().nextInt(sides)+1;
					display3(event);
					displayTotal(event);
				}
			}
			else if(eventContent.contains("d")&&!adv&&!dis) {
			sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.length()));
			randy = new Random().nextInt(sides)+1;
			if(sides ==20&&randy == 20){
				event.getChannel().sendMessage("You rolled a natural 20!!!");
				}
			else if(sides ==20&&randy == 1){
				event.getChannel().sendMessage("You rolled a natural 1 :(");
				}
			display1(event);
		}
			else if(eventContent.contains("d")&&adv&&eventContent.contains("+")&&!eventContent.contains(COMMAND2)) {
				sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.indexOf("+")));
				modifier = Integer.parseInt(eventContent.substring(eventContent.indexOf("+"), eventContent.indexOf("A")));
				randy = new Random().nextInt(sides)+1;
					tempRandy = new Random().nextInt(sides)+1;
					if(tempRandy>randy) {
						randy = tempRandy;
					}
					if(sides ==20&&randy == 20){
						event.getChannel().sendMessage("You rolled a natural 20!!!");
						}
					else if(sides ==20&&randy == 1){
						event.getChannel().sendMessage("You rolled a natural 1 :(");
						}
					display2(event);
					displayFinal(event);
			}
			else if(eventContent.contains("d")&&adv&&!eventContent.contains("+")&&!eventContent.contains(COMMAND2)) {
					sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.indexOf("A")));
					randy = new Random().nextInt(sides)+1;
					tempRandy = new Random().nextInt(sides)+1;
					if(tempRandy>randy) {
						randy = tempRandy;
					}
					if(sides ==20&&randy == 20){
						event.getChannel().sendMessage("You rolled a natural 20!!!");
						}
					else if(sides ==20&&randy == 1){
						event.getChannel().sendMessage("You rolled a natural 1 :(");
						}
					display2(event);
					displayFinal(event);
			}
			else if(eventContent.contains("d")&&dis&&eventContent.contains("+")&&!eventContent.contains(COMMAND2)) {
				sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.indexOf("+")));
				modifier = Integer.parseInt(eventContent.substring(eventContent.indexOf("+"), eventContent.indexOf("D")));
				randy = new Random().nextInt(sides)+1;
				tempRandy = new Random().nextInt(sides)+1;
				if(tempRandy<randy) {
					randy = tempRandy;
				}
				display2(event);
				displayFinal(event);
		}
			else if(eventContent.contains("d")&&dis&&!eventContent.contains("+")&&!eventContent.contains(COMMAND2)) {
				sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.indexOf("D")));
				randy = new Random().nextInt(sides)+1;
				tempRandy = new Random().nextInt(sides)+1;
				if(tempRandy<randy) {
					randy = tempRandy;
				}
				display2(event);
				displayFinal(event);
		}
			else if(!eventContent.contains("d")&&(event.getMessageAuthor().getName().contentEquals("Bexdog")/*||event.getMessageAuthor().getName().contentEquals("holdencat")*/&&!eventContent.contains(COMMAND2))) {
				String setRoll = eventContent.substring(6,eventContent.length());
				if(setRoll.equals("20")) {
					event.getChannel().sendMessage("You rolled a natural 20!!!");
				}
				else if(setRoll.equals("1")){
					event.getChannel().sendMessage("You rolled a natural 1 :(");
					}
				event.getChannel().sendMessage("Rolled a "+ setRoll);
				
			}
			else if(!eventContent.contains("d")&&!(event.getMessageAuthor().getName().contentEquals("Bexdog")/*||event.getMessageAuthor().getName().contentEquals("holdencat")*/&&!eventContent.contains(COMMAND2))) {
				event.getChannel().sendMessage("Error\nMissing Letter");				
			}
		}
	}

	void display1(MessageCreateEvent event){
		event.getChannel().sendMessage("Rolled a "+(randy+modifier));
	}
	void display2(MessageCreateEvent event){
		event.getChannel().sendMessage("Rolled a "+(randy+modifier)+" and "+ (tempRandy+modifier));
	}
	void display3(MessageCreateEvent event){
		event.getChannel().sendMessage("Rolled a "+(randy+modifier)+", "+ (tempRandy+modifier)+", and "+(tempRandy2+modifier));
	}
	void displayFinal(MessageCreateEvent event){
		event.getChannel().sendMessage("Final roll is a "+(randy+modifier));
	}
	void displayTotal(MessageCreateEvent event){
		event.getChannel().sendMessage("Total roll is a "+(randy+tempRandy+tempRandy2));
	}
}