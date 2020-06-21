package org.jointheleague.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class RockPaperScissorsListener extends CustomMessageCreateListener{

	public static final int rock = 0;
	public static final int paper = 1;
	public static final int scissors = 2;
	public static final String[] actions = {"Rock✊", "Paper✋", "Scissors✌"};
	public static final String[] wins = {"02","10","21"};
	public static final String[] losses = {"20","01","12"};
	public boolean playing = false;
	
	public static final String BEGIN_COMMAND = "!playRPS";
	public static final String END_COMMAND = "!stopRPS";
	public static final String ROCK_COMMAND = "!rock";
	public static final String PAPER_COMMAND = "!paper";
	public static final String SCISSORS_COMMAND = "!scissors";
	public static final String[] options = {ROCK_COMMAND,PAPER_COMMAND,SCISSORS_COMMAND};
//	File data;
//	FileWriter fr;	
//	public int twoBackYou;
//	public int twoBackThem;
//	public int oneBackYou;
//	public int oneBackThem;
//	public int testsDone = 0;
	Random randy = new Random();
	
	public RockPaperScissorsListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		//data = new File("src/main/resources/RPSdata.txt");
		
			
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String in = event.getMessageContent();
		
		if(!event.getMessageAuthor().isYourself()) {
			//event.getChannel().sendMessage("this is me: "+event.getMessageAuthor().isYourself());
			if(in.startsWith(BEGIN_COMMAND)) {
				playing = true;
				event.getChannel().sendMessage("Playing Rock Paper Scissors");
				event.getChannel().sendMessage("Enter !rock, !paper, or !scissors to play");
				//event.getChannel().sendMessage("For Example:");
			}else if(in.startsWith(END_COMMAND)){
				playing = false;
				event.getChannel().sendMessage("You quit Rock Paper Scissors");
				event.getChannel().sendMessage("You are a failure");
			}else if(playing == true){
			
				//Begin playing
				
				//Get your number
				
				int you = 4;
				for(int i = 0;i<3;i++) {
					if(in.equals(options[i])) {
						you = i;
					}
				}
				if(you!=4) {
					
					//get the bots number
					int them = randy.nextInt(3);
					
					//compare numbers
					int outcome = 1;/* 0=loss 1=tie 2=win */
					String compare = ""+you+them;
					for(String test : wins) {
						if(compare.equals(test)) {
							outcome = 2;
						}
					}
					for(String test : losses) {
						if(compare.equals(test)) {
							outcome = 0;
						}
					}
					
					//print outcome
					event.getChannel().sendMessage("You: "+actions[you]+" Them: "+actions[them]);
					if(outcome==0) {
						event.getChannel().sendMessage("	You Lose");
					}else if(outcome==1) {
						event.getChannel().sendMessage("	You Tied");
					}else if(outcome==2) {
						event.getChannel().sendMessage("	You Win");
					}
					
					//printing to file
					/*if(testsDone==0) {
						oneBackYou = you;
						oneBackThem = them;
					}else if(testsDone==1){
						twoBackYou = oneBackYou;
						twoBackThem = oneBackThem;
						oneBackYou = you;
						oneBackThem = them;
					}else {
						try {
							fr = new FileWriter(data);
							fr.append(""+twoBackYou+twoBackThem+" "+oneBackYou+oneBackThem+" "+you);
							fr.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					testsDone++;
					*/
				}else {
					event.getChannel().sendMessage("That is not an option");
					event.getChannel().sendMessage("Enter !rock, !paper, or !scissors to play");
				}
			}
		}
	}

}
