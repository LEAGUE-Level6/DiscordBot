package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class HangMan extends CustomMessageCreateListener {

	private Random r = new Random();
	private HashMap<String, Game> gameMap = new HashMap<>();
	
	
	public HangMan(String channelName) {
		super(channelName);
	}

	public void handle(MessageCreateEvent event) {		
		String input = event.getMessageContent();
		if (!input.startsWith("!hangman")) return;

			if(input.equals("!hangman")) {
				event.getChannel().sendMessage("```Hello and Welcome to Hangman\nThe Rules are simple. Guess a letter and see if it's a part of the word.\n"
						+ "For each letter you get wrong, the hangman will get closer to being complete.\nIf he's complete, you lose!\n\nHere are some commands you can use."
						+ "```\n\n**!hangman start [difficulty]**  (will start a game)\n**!hangman end**  (will end the game)\n**!hangman**  (shows this message) \n**!hangman [letter]**"
						+ "  (Make a guess if the game is running)\n**!hangman scoreboard**  (shows the top ten players)"
						+ "\n\nThe selectable difficulties are [easy, medium, hard]");
			}
			
			String playerName = event.getMessageAuthor().getName();
			Game game = gameMap.get(playerName);
			
			if(input.equals("!hangman scoreboard")) {
				ArrayList<PlayerScore> scoreBoardData = Game.currentScores();
				StringBuilder scoreBoardFormatter = new StringBuilder("```\n");
				int placeValue = 1;
				for (PlayerScore playerScore : scoreBoardData) {
					scoreBoardFormatter.append(placeValue++ + ".  ").append(playerScore.getLeaderBoardDisplay()).append("\n");
				}
				event.getChannel().sendMessage(scoreBoardFormatter.append("```").toString());
			}
			
			if(game == null) {
				//no active game
				if(input.contains("!hangman start")) {
					if(input.contains("easy")) game = new Game(r, 12);			
					else if(input.contains("hard")) game = new Game(r, 6);
					else game = new Game(r, 9);
					gameMap.put(playerName, game);
					game.start(event);
					game.display(event);
					
				}
			}
			else {
				//has active game
				if (input.length() == 10) if(!game.guess(event)) gameMap.remove(playerName);
				if(input.contains("!hangman end")) gameMap.remove(playerName);
			}
			

	}
}
