package org.jointheleague.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class MazeGame extends CustomMessageCreateListener {

	private static final String COMMAND = "!maze";
	private int[][] loadedMaze;

	public MazeGame(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "An ASCII maze game. The player is the small square in the upper right corner. Start games with !maze start <level>. "
				+ "Show levels with !maze list. Move with !maze move <direction> <distance>. Directions are north (up), east, south, and west."
				+ "The move command will not work if no maze is loaded. Separate all parameters by a single space. !maze help will show this message.");
		//Maybe I could allow people to load external mazes by using !maze load <upload> and attaching a maze file to the message.
	}

	@Override
	// listens for command and parses parameters
	public void handle(MessageCreateEvent event) throws APIException {
		System.out.println("Heard message: " + event.getMessageContent());
		if (event.getMessageContent().contains(COMMAND) && !event.getMessageAuthor().isBotUser()) {			
			if (event.getMessageContent().contains("start") || event.getMessageContent().contains("load")) {
				String target = event.getMessageContent().substring(11).strip();
				
				try {
					if(!target.equalsIgnoreCase("upload")) {
						loadedMaze = loadMaze(new FileReader("Mazes/" + target));
					}else {
						loadedMaze = loadMaze(new FileReader((File) event.getMessageAttachments().get(0)));
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					event.getChannel().sendMessage("Invalid maze.");
					loadedMaze = null;
				}
				
				
				if (loadedMaze != null) {
					new MessageBuilder().appendCode("java", displayMaze(loadedMaze)).send(event.getChannel());
				}
			}

			if (event.getMessageContent().contains("help") || event.getMessageContent().length() < 6) {
				System.out.println(event.getMessageContent().length());
				event.getChannel().sendMessage(
						"!maze is an ASCII maze game. Start games with !maze start <level>. Show levels with"
								+ " !maze list. Move with !maze move <direction> <distance>. Directions are north (up), east, south, and west. !maze help will show this message");
			}

			if (event.getMessageContent().contains("list")) {
				File file = new File("Mazes");
				File[] levels = file.listFiles();
				for (int i = 0; i < levels.length; i++) {
					event.getChannel().sendMessage(levels[i].getName());
				}
			}

			if (event.getMessageContent().contains("move") && loadedMaze != null) {
				try {
					loadedMaze = updateMaze(loadedMaze, event.getMessageContent().split(" ")[2], Integer.parseInt(event.getMessageContent().split(" ")[3]));
					if(loadedMaze == null) {
						event.getChannel().sendMessage("Congratulations! You completed the maze!");
					}else {
						new MessageBuilder().appendCode("java", displayMaze(loadedMaze)).send(event.getChannel());
					}
				} catch (NumberFormatException e) {
					event.getChannel().sendMessage(event.getMessageContent().split(" ")[3] + " is not a number.");
				} catch (InvalidDirectionException e) {
					event.getChannel().sendMessage(e.getDirection()
							+ " is not a valid direction. Valid directions are north (up), east, south, and west.");
				}
			}
			
			
		}
	}

	/*
	 * Mazes will be stored as two-dimensional arrays of ints in a text file. 0 =
	 * path 1 = wall 2 = player 3 = exit 4 = end of maze (I have an ASCII maze
	 * underneath the array in the file, so I need this.) -- Mazes will be DISPLAYED
	 * as maps of █ (for walls), spaces (for paths), x for the end, and ■ (for the
	 * player), like this: █ ███ █ ■ x █████ --
	 */

	private int[][] updateMaze(int[][] maze, String direction, int distance) throws InvalidDirectionException {
		int[][] newMaze = maze;

		int playerX = 0;
		int playerY = 0;
		
		int deltaX = 0;
		int deltaY = 0;

		boolean passable = true;
		
		// gets the user input direction string, and converts it to the corresponding
		// speed.
		switch (direction) {
		case "north":
			deltaX = -1;
			break;
		case "east":
			deltaY = 1;
			break;
		case "south":
			deltaX = 1;
			break;
		case "west":
			deltaY = -1;
			break;
		default:
			throw new InvalidDirectionException(direction);
		}

		//inverts speeds if the distance is negative
		if(distance < 0) {
			deltaX = 0 - deltaX;
			deltaY = 0 - deltaY;
			distance = 0 - distance;
		}
		
		// gets the current player position
		for (int i = 0; i < newMaze.length; i++) {
			for (int j = 0; j < newMaze[i].length; j++) {
				if (newMaze[i][j] == 2) {
					playerX = i;
					playerY = j;
					newMaze[i][j] = 0;
				}
			}
		}
		//Until the player has moved distance, move player by deltaX and deltaY, then check if that space is a wall. If it is, then
		//set the new maze to display the player in the space before the wall, and return the new maze. If the player hits the end of the maze, 
		//return a null maze.
		
		for (int i = 0; i < distance; i++) {
			playerX += deltaX;
			playerY += deltaY;
			
			//0 = path; 1 = wall; 2 = player; 3 = exit; 4 = end of maze
			if(newMaze[playerX][playerY] == 1) {
				newMaze[playerX-deltaX][playerY-deltaY] = 2;
				return newMaze;
			}//this is the maze updating code. 
			
			if(newMaze[playerX][playerY] == 3) {
				return null;
			}
		}
		
		newMaze[playerX][playerY] = 2;
		return newMaze;
	}

	private int[][] loadMaze(FileReader br) {
		int[][] maze = null;
		//System.out.println(target);
		try {
			//FileReader br = new FileReader(target);
			
			// get the number of rows/columns from the first two digit value in the file
			int line = Integer
					.parseInt("" + Character.getNumericValue(br.read()) + Character.getNumericValue(br.read()));
			maze = new int[line][line];
			System.out.println(line);
			br.skip(2);
			
			// this code takes the file and turns it into a 2D array.
			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze[i].length; j++) {
					maze[i][j] = Character.getNumericValue(br.read());
				}
			}

			br.close();
		} catch (FileNotFoundException e1) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// this code prints the 2D array to the console
		String print = "";
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				print += maze[i][j];
			}
			 System.out.println(print);
			print = "";
		}
		return maze;
	}

	private String displayMaze(int[][] maze) {
		String print = "";
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				switch (maze[i][j]) {
				case 0:
					print += " ";
					break;
				case 4:
				case 1:
					print += "█";
					break;
				case 2:
					print += "■";
					break;
				case 3:
					print += "x";
					break;
				}
			}
			print += "\n";
		}
		return print;
	}
}

class InvalidDirectionException extends Exception {
	String direction;

	public InvalidDirectionException(String direction) {
		this.direction = direction;
	}

	String getDirection() {
		return direction;
	}
}
