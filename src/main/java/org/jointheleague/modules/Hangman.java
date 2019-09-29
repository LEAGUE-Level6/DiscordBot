package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

public class Hangman extends CustomMessageCreateListener {

	private String current_guess;
	private String answer_string;
	private int num_incorrect;
	
	private ArrayList<Character> good_guesses;
	private ArrayList<Character> wrong_guesses = new ArrayList<Character>();
	
	private final int NUM_MAX = 6;
	private boolean GAME_RUNNING = false;
	
	
	public Hangman(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains("!begin hangman ")) {
			createGame(event);
		}
		
		else if(event.getMessageContent().equals("!hangman help"))
		{
			event.getChannel().sendMessage("begin hangman [word]: begin a game with the word [word], provided a game is not already running."
					+ "\n\nguess [letter]: guess the letter [letter]."
					+ "\n\nsee guesses: see the current incorrect guesses."
					+ "\n\nsee word: see the currently known part of the word."
					+ "\n\nsee lives: see how many attempts are left.");
		}
		
		else if (GAME_RUNNING)
		{
			if (event.getMessageContent().contains("!guess ")) {
				guess(event);
			}
			
			else if (event.getMessageContent().contains("!see guesses")) {
				event.getChannel().sendMessage("The current incorrect guesses are " + wrong_guesses.toString().substring(1, wrong_guesses.toString().length() - 1));
			}
			
			else if (event.getMessageContent().contains("!see word")) {
				event.getChannel().sendMessage("The currently known part of the word is " + current_guess);
			}
			
			else if (event.getMessageContent().contains("!see word")) {
				event.getChannel().sendMessage("The currently known part of the word is " + current_guess);
			}
			
			else if(event.getMessageContent().equals("!see lives"))
			{
				event.getChannel().sendMessage("There are " + (NUM_MAX - num_incorrect) + " guesses remaining.");
			}
		}
	}
	
	public void createGame(MessageCreateEvent event)
	{
		
		if(!GAME_RUNNING) {
			good_guesses = new ArrayList<Character>();
			wrong_guesses = new ArrayList<Character>();
			
			answer_string = event.getMessageContent().replace("!begin hangman ", "");
			current_guess = generateUnknown(answer_string);
			
			num_incorrect = 0;
			GAME_RUNNING = true;
			
			event.deleteMessage();
			
			event.getChannel().sendMessage("A hangman game has started! The format is " + current_guess + ". Begin guessing now!");	
		}
			
		else {
			event.deleteMessage();
			event.getChannel().sendMessage("A game is already being played.");
		}
	}
	
	public void guess(MessageCreateEvent event)
	{
		String guess = event.getMessageContent().replace("!guess ", "");
		if(guess.length() == 1)
		{
			boolean was_correct = false;
			char c_guess = guess.charAt(0);
			
			for(int i = 0; i < answer_string.length(); i++)
			{
				if(answer_string.charAt(i) == c_guess) {
					current_guess = current_guess.substring(0, i) + c_guess + current_guess.substring(i + 1, answer_string.length());
					was_correct = true;
				}
			}
			
			if(was_correct && !good_guesses.contains(c_guess)) {
					event.getChannel().sendMessage("The guess of " + c_guess + " is correct!");
					good_guesses.add(c_guess);
					checkGameState(event);
			}
			
			else if(!wrong_guesses.contains(c_guess))
			{
				num_incorrect++;
				wrong_guesses.add(c_guess);
				event.getChannel().sendMessage("The guess of " + c_guess + " is incorrect! " + (NUM_MAX - num_incorrect) + "  guesses remain.");
				checkGameState(event);
			}
			
			else if(wrong_guesses.contains(c_guess) || good_guesses.contains(c_guess))
			{
				event.getChannel().sendMessage("The character \'" + c_guess + "\' has already been guessed.");
			}
		}
	}
	
	public void checkGameState(MessageCreateEvent event)
	{
		if(answer_string.equals(current_guess)) {
			event.getChannel().sendMessage("The word \"" + answer_string + "\" is correct!");
			GAME_RUNNING = false;
		}
		
		else if (num_incorrect >= NUM_MAX) {
			event.getChannel().sendMessage("The game is lost! The word was \"" + answer_string + "\".");
			GAME_RUNNING = false;
		}
		
	}
	
	public String generateUnknown(String s)
	{
		String ret = "";
		for(char c: s.toCharArray())
		{
			if(c == ' ') {ret = ret + " ";}
			else {ret = ret + "-";}
		}
		
		return ret;
	}
}