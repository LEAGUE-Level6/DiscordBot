package org.jointheleague.modules;

import java.util.Random;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class RockPaperScissors extends CustomMessageCreateListener {
	private static final String COMMAND = "!rockpaperscissors";
	private static final String[] RPS = {"rock", "paper", "scissors"};
	private static boolean gameStarted = false;
	public RockPaperScissors(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!rockpaperscissors","");
			if(cmd.equals("")) {
				gameStarted = true;
			    event.getChannel().sendMessage("Please enter your choice (rock/paper/scissors)");
			}
		}
		if ((!arrayContains(event.getMessageContent()).equalsIgnoreCase("-1")) && gameStarted) {
	    	String userChoiceS = event.getMessageContent();
	    	int userChoice;
	    	Random r = new Random();
	    	int botChoice = r.nextInt(3);
	    	event.getChannel().sendMessage("The bot played " + RPS[botChoice]);
	    	if(userChoiceS.equalsIgnoreCase("rock")) {
	    		userChoice = 0;
	    	}else if(userChoiceS.equalsIgnoreCase("paper")) {
	    		userChoice = 1; 
	    	}else {
	    		userChoice = 2;
	    	}
	    	if(userChoice == botChoice) {
	    		event.getChannel().sendMessage("You tied! :neutral_face:");			    	
	    	}else if(userChoice == 0 && botChoice == 2) {
	    		event.getChannel().sendMessage("You won! :smiley:");
	    	}else if(userChoice == 2 && botChoice == 0) {
	    		event.getChannel().sendMessage("You lost! :frowning2:");
	    	}else if(botChoice > userChoice) {
	    		event.getChannel().sendMessage("You lost! :frowning2:");
	    	}else {
	    		event.getChannel().sendMessage("You won! :smiley:");
	    	}
	    	gameStarted = false;
	    }
	}
	public static String arrayContains(String msg) {
		for(int i = 0; i < 3; i++) {
			if(msg.equalsIgnoreCase(RPS[i])) {
				return RPS[i];
			}
		}
		return "-1";
	}
}
