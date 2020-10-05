package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import net.aksingh.owmjapis.api.APIException;

public class RiddleGenerator extends CustomMessageCreateListener {
	private static final String COMMAND = "!riddle";
	private static final String ANS = "!answer";
	private static final String INSTRUCTIONS = "!instructions";
	ArrayList<String> riddleArr;
	ArrayList<String> answerArr;
	int index;
	boolean nextRiddle = false;

	public RiddleGenerator(String channelName) {
		super(channelName);
		riddleArr = new ArrayList<String>();
		answerArr = new ArrayList<String>();
		

		riddleArr.add("I am not alive, but I grow; I don't have lungs, but I need air; I don't have a mouth, but water kills me. What am I?"); // fire
		answerArr.add("fire");

		riddleArr.add("It has a long neck, it's the name of a bird, it feeds on cargo of ships and it's not alive. What is it?"); //a crane
		answerArr.add("a crane");
		
		riddleArr.add("What has to be broken before you can use it?"); //an egg
		answerArr.add("an egg");
		
		riddleArr.add("What is full of holes but still holds water?"); //a sponge
		answerArr.add("a sponge");
		
		riddleArr.add("The more of this there is, the less you see. What is it?"); //darkness
		answerArr.add("darkness");
		
		riddleArr.add("David’s parents have three sons: Snap, Crackle, and what’s the name of the third son?"); //David
		answerArr.add("david");
		
		riddleArr.add("It belongs to you, but other people use it more than you do. What is it?"); //your name
		answerArr.add("your name");
		
		riddleArr.add("What has words, but never speaks?"); //a book
		answerArr.add("a book");
		
		riddleArr.add("What can travel all around the world without leaving its corner?"); //a stamp
		answerArr.add("a stamp");
		
		riddleArr.add("I am an odd number. Take away a letter and I become even. What number am I?"); //seven
		answerArr.add("seven");
		
		riddleArr.add("What is so fragile that saying its name breaks it?"); //silence
		answerArr.add("silence");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		Random r = new Random();

		String userContent = event.getMessageContent();

		if(userContent.contains(INSTRUCTIONS)) {
			event.getChannel().sendMessage("To get a new riddle, respond with a ! before the word riddle. "
					+ "To answer the riddle, respond with !, 'answer' + your answer. Be sure it is in all lowercase letters. "
					+ "If you don't know the answer, but want to find out, respond with just ! and answer.");
		}
		
		if (userContent.contains(COMMAND)) {
			index = r.nextInt(riddleArr.size());
			String randRiddle = riddleArr.get(index);
			event.getChannel().sendMessage("Your random riddle is: " + randRiddle);
			nextRiddle = true;
		}
		
		if (userContent.contains(ANS)) {
			if (nextRiddle == false) {
				event.getChannel().sendMessage("Ask for a new riddle.");
			} else if (userContent.contains(answerArr.get(index))) {
				event.getChannel().sendMessage("That is correct!");
				nextRiddle = false;
			}
			else if (userContent.equals("!answer")) {
				event.getChannel().sendMessage(answerArr.get(index));
				nextRiddle = false;
			} else {
				event.getChannel().sendMessage("Try again.");
			}
		}
	}

}
