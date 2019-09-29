package org.jointheleague.modules;

import java.awt.Color;
import java.util.HashMap;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class MinesweeperListener extends CustomMessageCreateListener {
	
	//Final Todo List: Help command, Code cleanliness
	
	//Stores each game with a String of a user mention id so the code knows which game is being played based on the user
	HashMap<String, MinesweeperGame> games = new HashMap<String, MinesweeperGame>();
	
	//Commands
	static final String GENERATE_COMMAND = "!minesweeper-create";
	static final String INTERACT_COMMAND = "!m-tap";
	static final String FLAG_COMMAND = "!m-flag";
	static final String DISPLAY_COMMAND = "!minesweeper-display";
	static final String QUIT_COMMAND = "!minesweeper-quit";
	static final String SURRENDER_COMMAND = "!minesweeper-surrender";
	static final String LOCATE_COMMAND = "!m-loc";
	static final String HELP_COMMAND = "!minesweeper-help";
	
	public MinesweeperListener(String channelName) {
		super(channelName);
	}
	
	/**
	 * Ensures that the coordinates are within the boundaries of the board and returns them as integers.
	 * 
	 * @param parameters The two parameters given by the user
	 * @param game MinesweeperGame object which is mapped to the target player
	 * @return An integer array of size 2 with the x and y coordinates
	 */
	int[] parsePositions(String[] parameters, MinesweeperGame game) {
		if(parameters.length == 2) {
			int[] coordinates = new int[2];
			try {
				coordinates[0] = Integer.valueOf(parameters[0]);
				coordinates[1] = Integer.valueOf(parameters[1]);
				if(coordinates[0] >= game.playingField.length || coordinates[1] >= game.playingField.length) {
					return null;
				}
			}
			catch(NumberFormatException e) {
				return null;
			}
			return coordinates;
		}
		else {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param command The name of the command
	 * @param text The entirety of the message
	 * @return The parameters given by the user, split into separate Strings
	 */
	String[] parseParameters(String command, String text) {
		
		try {
			String[] p = text.substring(command.length()).trim().split(" ");
			return p;
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	
	/**
	 * Displays the playing field of the game of the player
	 * 
	 * @param event the MessageCreateEvent given by the Javacord listener
	 * @param locateCoords Used by locate command to place a mouse pointer emoji on the given coordinates
	 * @see #getEmojiForCell(org.jointheleague.modules.MinesweeperGame.Cell)
	 */
	void displayGrid(MessageCreateEvent event, int[] locateCoords) {
		EmbedBuilder invalidPlayer = new EmbedBuilder().setTitle("No Active Game").setDescription("You don't have a game running at the moment").setColor(Color.RED);
		
		//Makes sure that the player has a game running
		if(!(games.containsKey(event.getMessage().getAuthor().asUser().get().getMentionTag()))) {
			event.getChannel().sendMessage(invalidPlayer);
			return;
		}
		
		String playerId = event.getMessageAuthor().asUser().get().getMentionTag();
		
		//Gets the game's playing field
		MinesweeperGame.Cell[][] toDisplay = games.get(playerId).playingField;
		
		//Converts each row of the grid into a set of emojis using the getEmojiForCell function (Split into rows because of Discord's max characters per message limit)
		String[] lines = new String[toDisplay.length];
		
		for(int i = 0; i < toDisplay.length; i++) {
			for(int j = 0; j < toDisplay[i].length; j++) {
				if(i == 0) {
					lines[j] = "";
				}
				String emoji = getEmojiForCell(toDisplay[i][j]);
				if(locateCoords != null) {
					if(locateCoords[0] == i && locateCoords[1] == j) {
						emoji = ":mouse_three_button:";
					}
				}
				lines[j] = lines[j] + emoji;
			}
		}
		
		//Send the playing field on Discord row by row
		String messageString = "";
		for(int k = 0; k < lines.length; k++) {
			event.getChannel().sendMessage(lines[k]);
		}
		event.getChannel().sendMessage("Display Complete!");
		
	}
	
	/**
	 * 
	 * Returns an emoji representation of the given cell. Undiscovered cells are white squares. Flags are flag posts.
	 * Discovered empty cells are black squares. Number cells are their respective number emoji. Mines are the bomb emoji.
	 * 
	 * @param cell The target cell
	 * @return An emoji representation of the cell
	 */
	String getEmojiForCell(MinesweeperGame.Cell cell) {
		if(cell.state == MinesweeperGame.BlockState.UNDISCOVERED) {return ":white_large_square:";}
		else if(cell.state == MinesweeperGame.BlockState.FLAGGED) {return ":triangular_flag_on_post:";}
		else if(cell.state == MinesweeperGame.BlockState.DISCOVERED) {
			switch(cell.type) {
			case EMPTY:
				return ":black_large_square:";
			case ONE:
				return ":one:";
			case TWO:
				return ":two:";
			case THREE:
				return ":three:";
			case FOUR:
				return ":four:";
			case FIVE:
				return ":five:";
			case SIX:
				return ":six:";
			case SEVEN:
				return ":seven:";
			case EIGHT:
				return ":eight:";
			case BOMB:
				return ":bomb:";
			}
		}
		
		return null;
	}
	
	/**
	 * The listener-side code to call the game's interact function.
	 * @param event MessageCreateEvent given by Javacord Listener
	 * @see MinesweeperGame#interactCell(int, int)
	 */
	void interactCommand(MessageCreateEvent event) {
		
		EmbedBuilder invalidParameters = new EmbedBuilder().setTitle("Invalid Parameters").setDescription("!m-tap (x coordinate) (y coordinate)").setColor(Color.RED);
		EmbedBuilder invalidPlayer = new EmbedBuilder().setTitle("No Active Game").setDescription("You don't have a game running at the moment").setColor(Color.RED);
		
		//Gets the coordinate point of the target cell given by the player
		String[] parameterStr = parseParameters(INTERACT_COMMAND, event.getMessageContent());
		int[] coords = parsePositions(parameterStr, games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()));
		if(coords == null) {
			//Invalid Parameters
			event.getChannel().sendMessage(invalidParameters);
			return;
		}
		
		//Checks if the player has a game running
		if(games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()) != null) {
			//Calls the interact function of the MinesweeperGame class
			games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()).interactCell(coords[0], coords[1]);
			//Checks if the game was lost. If it was, it displays the discovered grid and deletes the game. Also sends a losing message.
			if(games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()).gameOver) {
				displayGrid(event, null);
				event.getChannel().sendMessage(new EmbedBuilder().setTitle("Game Over!").setColor(Color.ORANGE));
				quitCommand(event);
				return;
			}
			//Checks if the game was won. If it was, it deletes the game. Also sends a win message.
			else if(games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()).winOver) {
				displayGrid(event, null);
				event.getChannel().sendMessage(new EmbedBuilder().setTitle("You Won!").setColor(Color.BLUE));
				quitCommand(event);
				return;
			}
		}
		else {
			//No Game for Player
			event.getChannel().sendMessage(invalidPlayer);
			return;
		}
		
		//Re-Display Playing Field
		displayGrid(event, null);
	}
	
	/**
	 * The listener-side code to call the game's flag toggle function.
	 * @param event
	 * @see MinesweeperGame#flagCell(int, int)
	 */
	void flagCommand(MessageCreateEvent event) {
		EmbedBuilder invalidParameters = new EmbedBuilder().setTitle("Invalid Parameters").setDescription("!m-flag (x coordinate) (y coordinate)").setColor(Color.RED);
		EmbedBuilder invalidPlayer = new EmbedBuilder().setTitle("No Active Game").setDescription("You don't have a game running at the moment").setColor(Color.RED);
		
		//Gets the coordinates
		String[] parameterStr = parseParameters(FLAG_COMMAND, event.getMessageContent());
		int[] coords = parsePositions(parameterStr, games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()));
		if(coords == null) {
			//Invalid Parameters
			event.getChannel().sendMessage(invalidParameters);
			return;
		}
		
		//Checks if the player has a game running
		if(games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()) != null) {
			//Calls the MinesweperGame's toggle flag function
			games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()).flagCell(coords[0], coords[1]);
			//Checks if the game was won. If it was, it displays the undiscovered grid and deletes the game. Also sends a win message.
			if(games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()).winOver) {
				displayGrid(event, null);
				event.getChannel().sendMessage(new EmbedBuilder().setTitle("You Won!").setColor(Color.BLUE));
				quitCommand(event);
				return;
			}
		}
		else {
			//No Game for Player
			event.getChannel().sendMessage(invalidPlayer);
			return;
		}
		
		//Re-Display Playing Field
		displayGrid(event, null);
	}
	
	/**
	 * Creates a new game for the player, given he doesn't have one running already.
	 * 
	 * @param event MessageCreateEvent given by the Javacord Listener
	 * @see MinesweeperGame#MinesweeperGame(int, int)
	 */
	void createCommand(MessageCreateEvent event) {
		//Initialize all Possible Error Messages for this command
		EmbedBuilder errorMessage = new EmbedBuilder().setTitle("Invalid Usage").setDescription("!minesweeper-create (side length of field) (amount of mines)").setColor(Color.RED);
		EmbedBuilder dimensionTooLarge = new EmbedBuilder().setTitle("Invalid Parameter").setDescription("The playing field has a maximum side length of 10").setColor(Color.RED);
		EmbedBuilder tooManyMines = new EmbedBuilder().setTitle("Invalid Parameter").setDescription("There are more mines than spaces on the playing field").setColor(Color.RED);
		EmbedBuilder notNumbers = new EmbedBuilder().setTitle("Invalid Parameters").setDescription("The parameters must be integers").setColor(Color.RED);
		EmbedBuilder invalidUser = new EmbedBuilder().setTitle("Unable to Perform Command").setDescription("You already have a game running. Use !minesweeper-quit to delete your old game").setColor(Color.RED);
		
		//Use parseParameters to get the parameters required for this command
		String[] params = parseParameters(GENERATE_COMMAND, event.getMessageContent());
		if(params != null) {
			//If two parameters aren't received, it sends the "Invalid Usage" error message
			if(params.length != 2) {
				event.getChannel().sendMessage(errorMessage);
				return;
			}
			
			//Makes sure the parameters are given are both numbers
			int sideLength;
			int mineCount;
			try {
				sideLength = Integer.valueOf(params[0]);
				mineCount = Integer.valueOf(params[1]);
			}
			catch(NumberFormatException nfe) {
				event.getChannel().sendMessage(notNumbers);
				return;
			}
			//Makes sure the playing field is no large than 10x10
			if(sideLength > 10) {
				event.getChannel().sendMessage(dimensionTooLarge);
				return;
			}
			//Makes sure there are less mines than slots on the playing field
			if(mineCount > sideLength*sideLength) {
				event.getChannel().sendMessage(tooManyMines);
				return;
			}
			//Makes sure the user isn't currently in the middle of any other games
			if(games.containsKey(event.getMessageAuthor().asUser().get().getMentionTag())) {
				event.getChannel().sendMessage(invalidUser);
				return;
			}
			
			//Creates a new Minesweeper Game and displays the playing field. It will also add it to the map of all running games.
			MinesweeperGame brandNewGame = new MinesweeperGame(sideLength, mineCount);
			games.put(event.getMessageAuthor().asUser().get().getMentionTag(), brandNewGame);
			displayGrid(event, null);
		}
		else {
			event.getChannel().sendMessage(errorMessage);
		}
	}
	
	/**
	 * Deletes the player's existing game
	 * @param event MessageCreateEvent given by Javacord Listener
	 */
	void quitCommand(MessageCreateEvent event) {
		EmbedBuilder invalidPlayer = new EmbedBuilder().setTitle("No Active Game").setDescription("You don't have a game running at the moment").setColor(Color.RED);
		
		if(!(games.containsKey(event.getMessage().getAuthor().asUser().get().getMentionTag()))) {
			event.getChannel().sendMessage(invalidPlayer);
			return;
		}
		games.remove(event.getMessage().getAuthor().asUser().get().getMentionTag());
		event.getChannel().sendMessage(new EmbedBuilder().setTitle("Your Game has been Deleted").setColor(Color.ORANGE));
	}
	
	/**
	 * Automatically loses the player's existing game, showing him the completely discovered grid before deleting the game.
	 * @param event MessageCreateEvent given by Javacord Listener
	 */
	void surrenderCommand(MessageCreateEvent event) {
		EmbedBuilder invalidPlayer = new EmbedBuilder().setTitle("No Active Game").setDescription("You don't have a game running at the moment").setColor(Color.RED);
		
		if(!(games.containsKey(event.getMessage().getAuthor().asUser().get().getMentionTag()))) {
			event.getChannel().sendMessage(invalidPlayer);
			return;
		}
		games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()).loseGame();
		displayGrid(event, null);
		event.getChannel().sendMessage(new EmbedBuilder().setTitle("You have Surrendered!").setColor(Color.ORANGE));
		quitCommand(event);
	}
	
	/**
	 * Used to double check the coordinates in mind before playing a move. Uses the locateCoords functionality of displayGrid
	 * @param event MessageCreateEvent given by Javacord Listener
	 * @see #displayGrid(MessageCreateEvent, int[])
	 */
	void locateCommand(MessageCreateEvent event) {
		int[] wantedCoords = parsePositions(parseParameters(LOCATE_COMMAND, event.getMessageContent()), games.get(event.getMessage().getAuthor().asUser().get().getMentionTag()));
		displayGrid(event, wantedCoords);
	}
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		//Maps each command variable to its corresponding function
		if(event.getMessageContent().startsWith(GENERATE_COMMAND)) {
			createCommand(event);
		}
		if(event.getMessageContent().startsWith(INTERACT_COMMAND)) {
			interactCommand(event);
		}
		if(event.getMessageContent().startsWith(FLAG_COMMAND)) {
			flagCommand(event);
		}
		if(event.getMessageContent().startsWith(DISPLAY_COMMAND)) {
			displayGrid(event, null);
		}
		else if(event.getMessageContent().startsWith(QUIT_COMMAND)) {
			quitCommand(event);
		}
		else if(event.getMessageContent().startsWith(SURRENDER_COMMAND)) {
			surrenderCommand(event);
		}
		else if(event.getMessageContent().startsWith(LOCATE_COMMAND)) {
			locateCommand(event);
		}
		else if(event.getMessageContent().startsWith(HELP_COMMAND)) {
			EmbedBuilder helpMessage = new EmbedBuilder().setTitle("Minesweeper Help").setColor(Color.CYAN);
			helpMessage.setDescription("Note about Coordinates: Coordinates can be obtained using a system similar to a Cartesian plane."  + "\n" + "However, the y values are inverted. So y coordinate 0 would be the top row and x coordinate 0 would be the column to the very left. Use the locate command if you have any doubts.");
			helpMessage.addField(GENERATE_COMMAND, "Generates a new Minesweeper board. You can't have existing Minesweeper boards open to generate a new one."+"\n"+GENERATE_COMMAND+" (side length of board) (amount of mines on board)");
			helpMessage.addField(QUIT_COMMAND, "Quit and delete your current Minesweeper board.");
			helpMessage.addField(FLAG_COMMAND, "Toggles the flag on the chosen cell" + "\n"+FLAG_COMMAND+" (x coordinate) (y coordinate)");
			helpMessage.addField(INTERACT_COMMAND, "Discover the chosen cell"+"\n"+INTERACT_COMMAND+" (x coordinate) (y coordinate)");
			helpMessage.addField(SURRENDER_COMMAND, "Surrender the game. The entire board will be shown to you before the game is deleted");
			helpMessage.addField(LOCATE_COMMAND, "Double check that you are thinking of the right coordinate. The game will put a mouse icon on the coordinate that you chose to locate. The icon will disappear on the next move." + "\n" + LOCATE_COMMAND + " (x coordinate) (y coordinate)");
			event.getChannel().sendMessage(helpMessage);
		}
	}

}
