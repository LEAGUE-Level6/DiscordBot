package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class GuessingGame extends CustomMessageCreateListener {

	MessageAuthor player;
	boolean gameInSession = false;
	int lowerBound;
	int upperBound;
	int numberToGuess;
	int guessCount = 0;
	String COMMAND = "?guessgame";

	public GuessingGame(String channelName) {
		super(channelName);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		// is command?
		if (!event.getMessageAuthor().isYourself()) {

			String command = event.getMessageContent().toLowerCase();

			// starts game
			if (command.contains("?guessgame")) {

				if (!gameInSession) {
					player = event.getMessageAuthor();
					guessCount = 0;
					String[] commandSplit = command.split(" ");
					gameInSession = true;

					try {

						lowerBound = Integer.parseInt(commandSplit[1]);
						upperBound = Integer.parseInt(commandSplit[2]);
						Random rand = new Random();
						numberToGuess = rand.nextInt(upperBound - lowerBound + 1) + lowerBound;

						event.getChannel().sendMessage("Game started! Guess a number between " + lowerBound + " and "
								+ upperBound + " with ?guess");

						event.getChannel().sendMessage("Current player: " + player.getDiscriminatedName() + "<@"
								+ player.getIdAsString() + ">");

					} catch (Exception e) {
						event.getChannel().sendMessage("Please type the upper and lower bounds of my range of numbers like so:\n`?guessgame x y`. Also, don't make the upper limit too high!");
					}
					
					// game already going?	
				} else {
					
					event.getChannel().sendMessage("A game is already in progress. Type ?endgame to end the current game.");
				}
			}
			
			if (command.contains("?endgame") && gameInSession) {

				gameInSession = false;
				guessCount = 0;
				
			}

			// game itself
			if (gameInSession) {
				if (command.contains("?guess") && !command.contains("?guessgame")) {
					if (checkAuthor(player, event)) {

						String[] commandSplit = command.split(" ");

						try {

							int guess = Integer.parseInt(commandSplit[1]);
							guessCount++;

							if (guess == numberToGuess) {
								event.getChannel().sendMessage("You guessed correctly! The number was " + numberToGuess
										+ ". It took you " + guessCount + " tries to guess my number.");

								gameInSession = false;
							}

							if (guess > numberToGuess) {
								event.getChannel().sendMessage("Too high...");
							}

							if (guess < numberToGuess) {
								event.getChannel().sendMessage("Too low...");
							}

						} catch (Exception e) {
							event.getChannel().sendMessage("@" + player.getDiscriminatedName() + "Invalid guess!");

						}
					}
				}
			}
		}
	}

	public boolean checkAuthor(MessageAuthor player, MessageCreateEvent event) {

		if (gameInSession && !event.getMessageAuthor().equals(player)) {

			event.getChannel()
					.sendMessage("Don't interfere with our game! <@" + event.getMessageAuthor().getIdAsString() + ">");

			return false;

		}

		return true;

	}

}
