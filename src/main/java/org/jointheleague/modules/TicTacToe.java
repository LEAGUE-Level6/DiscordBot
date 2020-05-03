//Elijah Bowers 3/11/2020
//  -ref- youtube.com/watch?v=l-hh51ncgDI

package org.jointheleague.modules;

import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

public class TicTacToe extends CustomMessageCreateListener {

	String[][] commands = { { "!TicCommands", "!TC", "Use this to display the list of the Tic-tac-toe game commands" },
			{ "!Tic.getStage", "!T.gS", "Returns the stage that the game is on" },
			{ "!Tic.start", "!T.s", "Begins the game making process" },
			{ "!Tic.finish", "!T.f", "Finishes the game making process with the current variables (players, board size). You can also "
							+ "make a normal game with: `basic game` (`X` or `O`)", "basic game" },
			{ "!TicSet", "!TS",
					"Allows you to set the size of the board `set size` (`num >= 3`), remove a player `remove player` "
							+ "(`num of player in player list` or `player character`), add a player `add player` (`player character` , "
							+ "`whether or not it is a computer`)`, or see all the players `display players` "
							+ ". All during the gameMaking phase", "add player", "remove player", "set board size", "display players"},
			{ "!TicPos", "!TP",
					"Inputs the decided position for the current players turn (`0 - (boardwidth-1)`, `0 - (boardwidth-1)`)"
							+ " or (`-1`, `-1`) if you want the computer to decide your move" },
			{ "!Tic.end", "!T.e", "Use this to go back to the start phase (end the game)" },
			{ "!Tic.display", "!T.d", "Re-displays the board in its current state" },
			{ "!Tic.setSuggestions", "!T.sS", "Allows you to toggle the displaying of suggestions"} };                                         // Input commands / descriptions for the player

	GameMaker gm;                                                                                  // The game maker instance

	Game g;                                                                                        // The game instance

	boolean suggestion;                                                                            // whether or not the game displays suggestions

	int stage;                     	             	                                               // the state the game is in

	final int Start = 0;                                                                           // the beginning stage (use !Tic.start to go to next)
	final int Deciding = 1;                                                                        // the stuff changing stage (use !Tic.finish to go to next) (gm used in this)
	final int Game = 2;                                                                            // the game-playing stage (g used in this)

	public TicTacToe(String channelName) {
		super(channelName);
		stage = Start;
		gm = new GameMaker();
		suggestion = true;
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String input = event.getMessageContent();                                                  // the input it gets from the channel
		String responses = "";                                                                     // the message it sends out to the channel
		if (!event.getMessageAuthor().isYourself()) {                                              // check if it is someone else writing
			if (canFindCommand(input, 8, 0, false)) {                                              // testing for command to change suggestion status 
				if (input.contains("false")) {                                                     // if changing to false
					suggestion = false;     
					responses += "\nYou will now not get suggestions";
				} else if (input.contains("true")) {                                               // if changing to true
					suggestion = true;
					responses += "\nYou will now get suggestions";
				}
			} else if (canFindCommand(input, 0, 0, false)) {     								   // testing for command to display commands
				for (int i = 0; i < commands.length; i++) {                                        // looping through all commands
					responses += "\n" + commands[i][0] + " or " + commands[i][1] + " :\n> " + commands[i][2];// displaying them with suggestions
				}
			} else if (canFindCommand(input, 1, 0, false)) {                                       // testing for display command command
				switch (stage) {
				case Start:
					responses += "\n You dont have any games running now\n> use: `" + commands[2][0] + "` or `"
					        + commands[2][1] + "` to begin the game making process";
					break;
				case Deciding:
					responses += "\n You are in the process of making a game\n> use: `" + commands[4][0] + "` or `"
							+ commands[4][1] + "` to change the settings";
					break;
				case Game:
					responses += "\n You are in the middle of a game\n> it is player " + g.getCurrentPlayer() 
					        + "'s turn now";
					break;
				}
			} else if (canFindCommand(input, 6, 0, false)) {                                       // testing for command to end current game
				switch (stage) {
				case Start:                                                                        // if nothing in process
					responses += "\nThere is no Game to end\n> Use `" + commands[2][0] + "` or `"
							+ commands[2][1] + "` to make a new Tic-Tac-Toe Game";
					break;
				case Deciding:                                                                     // if game making in process
					responses += "\nThe game making process has been aborted \n> Use `" + commands[2][0] + "` or `"
							+ commands[2][1] + "` to make a new Tic-Tac-Toe Game";
					stage = Start;
					break;
				case Game:                                                                         // if game in process
					responses += "\nThe game has been ended\n> Use `" + commands[2][0] + "` or `" + commands[2][1]
							+ "` to make a new Tic-Tac-Toe Game";
					stage = Start;
					break;
				}
			} else if (canFindCommand(input, 2, 0, false)) {                                       // begin making a game
				responses += "\nYou have started a tic-tac-toe game"
						+ "\n> Now either do a normal tic tac toe game using `" + commands[3][0] + "` or `" + commands[3][1]
						+ "` basic game (`X` or `O`). Or make a custom game with: `" + commands[4][0] + "` or `"
						+ commands[4][1] + "` and then use `" + commands[3][0] + "` or `" + commands[3][1] + "` to finish it";
				gm = new GameMaker();
				stage = Deciding;
			}
			if (stage == Deciding) {                                                               // if in changing stuff stage
				if (canFindCommand(input, 4, 0, false)) {                                          // testing for command to change something
					if (canFindCommand(input, 4, 3, true)) {                                       // testing for command to add player
						String cha = getInBetweenTwoChars(input, '(', ',');
						if (cha.length() == 1) {                                                   // testing if character selection is a length of one
							switch (getInBetweenTwoChars(input, ',', ')').toLowerCase()) {
							case "true":                                                           // testing if input says for player to be a computer
								responses += "\n" + gm.addNewPlayer(true, cha);                    
								break;
							case "false":                                                          // testing if input says for player to not be a computer
								responses += "\n" + gm.addNewPlayer(false, cha);              
								break;
							default :                                       					   // if neither
								responses += "\nThis isn't a valid character decleration\n> Do: `add player` (`the character`, `whether or not it is a computer`)";
								break;
							}
						} else {                                                                   // if character selection isn't
							responses += "\nThis isn't a valid character\n> Use a char `a single Letter`";
						}
					} else if (canFindCommand(input, 4, 4, true)) {                                // remove player
						String inOrCha = getInBetweenTwoChars(input, '(', ')');                    // getting inputs
						try {                                                                      // catch for the error if there would be no actual numbers
							responses += "\n" + gm.removePlayer(Integer.parseInt(inOrCha));        // test if input is the number of a player and if yes remove
						} catch (Exception e) {
							e.printStackTrace();
							responses += "\n" + gm.removePlayer(inOrCha);                          // otherwise test if it is the char of a player and if yes remove
						}
					} else if (canFindCommand(input, 4, 5, true)) {                                // testing for command to change board size
						String in = getInBetweenTwoChars(input, '(', ')');                         // getting inputs
						try {                                                                      // catch for if input isn't a number
							responses += "\n" + gm.setWidth(Integer.parseInt(in));                 // test if input is a number and if yes set
						} catch (Exception e) {
							e.printStackTrace();
							responses += "\n That is not a valid number\n> Use a number like `4` ";
						}
					}else if(canFindCommand(input, 4, 6, false)) {
						responses += gm.showPlayers();
					}
				}
				if (canFindCommand(input, 3, 0, false)) {                                            // testing for command to finish game
					if (canFindCommand(input, 3, 3, true)) {                                         // testing for command basic game
						switch (getInBetweenTwoChars(input, '(', ')').toUpperCase()){
						case "X":
							g = gm.initAverageGame("X");                                           // Make basic game with player being "X"
							stage = Game;                                                          // set stage game
							responses += "\nYou have begun the game\n> Now use: `" + commands[5][0] + "` or `" + commands[5][1]
									+ "` (`0 - (boardwidth-1)` ,`0 - (boardwidth-1)`) to select a position for the current player";
							responses += g.Start();                                                // do players until the next one is a human
							break;
						case "O":
							g = gm.initAverageGame("O");                                           // Make basic game with player being "O"
							stage = Game;                                                          // Set stage to game
							responses += "\nYou have begun the game.\n> Now use: `" + commands[5][0] + "` or `" + commands[5][1]
									+ "` (`0 - (boardwidth-1)`, `0 - (boardwidth-1)`) to select a position for the current player";
							responses += g.Start();                                                // do players until the next one is a human
							break;
						default:
							responses += "\nThat is an invalid game decleration\n> Use: `" + commands[3][0] + "` or `" + commands[3][1] 
									+ "`" + commands[3][3] + " (`X` or `O`) ";
							break;
						}
					} else {                                                                       // If not basic game
						if (gm.players.size() < 2) {                                               // If incomplete character list (< 2 players)
							responses += "\nYou cannot finish the game now there aren't enough people\n> You can add a new character to the game using: `"
									+ commands[4][0] + "` or `" + commands[4][1] + "' add player (`player character`, `whether or "
									+ "not it is a computer`)";
						} else {                                                                   // If complete character list
							g = gm.initGame();                                                     // Start game with current players, board size
							stage = Game;                                                          // Set stage to game 
							responses += "\nYou have begun the game\n> Now use: `" + commands[5][0] + "` or `" + commands[5][1]
									+ "` (`0 - (boardwidth-1)`, `0 - (boardwidth-1)`) to select a position for the current player";
							responses += g.Start();                                                // do players until the next one is a human
						}
					}
				}
			} else if (stage == Game) {                                                            // If stage is = to game stage
				if (canFindCommand(input, 7, 0, false)) {                                          // testing for command to display board
					responses += g.display();                                                      // display board
				}else if (canFindCommand(input, 5, 0, false)) {                                    // testing for command to do a move
					try {                                                                          // catch for input not integer
						responses += g.DoNextTurn(Integer.parseInt(getInBetweenTwoChars(input, '(', ',')), Integer.parseInt(getInBetweenTwoChars(input, ',', ')')));
						if (responses.contains("Has Won")) {                                       // check for game end
							stage = Start;                                                         // set stage to start
						}
					} catch (Exception e) {                                 
						e.printStackTrace();
						responses += "\nThat isn't a valid position\n> Do something like: (1, 2)";
					}
				}
			}
			if (!suggestion) {                                                                     // test if suggestion (variable) is false
				responses += "\n";                                                                 // add "\n" to the end for simplicity of testing
				while (responses.contains("> ")) {                                                 // while output contains ">" (suggestions)
					responses = removeSubstring(responses, "> ", "\n");                            // remove from that ">" to the next "\n" (the suggestion)
				}
			}
			event.getChannel().sendMessage(responses);                                             // return the messages to the channel
		}
	}

	private boolean canFindCommand(String input, int x, int y, boolean lookingForParen) {          // -method to find a command
		if(y == 0) {                                                                               // if it is the first in a set
			if(input.startsWith(commands[x][0]) || input.startsWith(commands[x][1])) {             // check for both the first and second
				return true;                                                                      
			}
			return false;
		}
		if(input.contains(commands[x][y])) {                                                       // if it contains that command
			if(lookingForParen) {                                                                  // if it is also looking for parentheses
				if(input.contains("(") && input.contains(")")) {
					return true;
				}
				return false;
			}
			return true;
		}
		return false;
	}
	
	private int increment(int on, int end) {                                                       // -method to increment the passed in int untill the end point
		return (on + 1) % end;
	}
	
	private String removeSubstring(String s, String first, String last) {                          // -method for removing a substring in a given string. With String positions
		int fir = s.indexOf(first);                                                                // getting index of first String in the main String
		return removeSubstring(s, fir, fir + s.substring(fir + 1).indexOf(last));                  // removing substring using next method with the index of the first String and the index of the second String after the first String in the main String
	}

	private String removeSubstring(String s, int first, int last) {                                // -method for removing a substring in a given string. With int positions
		return s.substring(0, first) + s.substring(last + 1, s.length());                          // set the string to: the substring from the beginning of the string to the first int and the substring from the second int to the end of the string
	}

	private String getInBetweenTwoChars(String s, char p1, char p2) {                              // -method for getting a substring within a string between two chars
		return s.substring(s.indexOf(p1) + 1, s.indexOf(p2)).trim();                               // return it
	}
	
	private boolean anyNumEquals(String... args) {
		if(args.length > 1 && !args[0].isBlank()) {
			for(int i = 0 ; i < args.length-1; i++) {
				if(!args[i].equals(args[i+1])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public class GameMaker {

		ArrayList<Player> players;                                                                 // an array list of players

		int boardWidth;                                                                            // the width of the board
		int numInARow;
		
		GameMaker() {
			players = new ArrayList<Player>();
			boardWidth = 3;
			numInARow = 3;
		}

		public String setWidth(int width) {                                                        // -method for changing the width of the board
			if (width < 3) {                                                                       // test if int less than 3
				return "You cannot set the board to that size. It is too small\n the minimum is 3";
			}
			if (width > 15) {                                                                      // test if int greater than 15
				return "You cannot set the board to that size. It is too large\n the maximum is 15";
			}
			boardWidth = width;                                                                    // set board width to said size
			return "You set the board's width to: " + width;
		}

		public String addNewPlayer(boolean b, String character) {                                  // -method for adding a new player
			for (Player p : players) {                                                             // go through all players
				if (character.contentEquals(p.character)) {                                        // if char already exists in another player
					return "That character already exists in another player";
				}
			}
			players.add(new Player(b, character));                                                 // adds the player
			return "You made an new " + players.get(players.size()-1).getType() + ": " + character;// outputs what you added
		}

		public String removePlayer(String character) {                                             // -method for removing a player (input char)
			for (int i = 0; i < players.size(); i++) {                                             // goes through all players
				if (players.get(i).character.equals(character)) {                                  // test if this player character matches the inputed one
					players.remove(i);                                                             // remove said player
					return "\nYou removed player" + character;
				}
			}
			return "\nThat player doesn't exist. Use " + commands[4][6] + " to get a list of the players";
		}

		public String removePlayer(int pos) {                                                      // -method for removing a player (input int)
			if (pos < players.size()) {                                                            // test if inputed num is less than length of the arr
				String s = players.get(pos).character;
				players.remove(pos);                                                               // remove said player
				return "\nYou removed player " + s;
			}
			return "\nThat player doesn't exist. Use " + commands[4][6] + " to get a list of the players";
		}

		public String showPlayers() {                                                              // -method to return a String of all players
			String output = "";                                                               
			for (int i = 0; i < players.size(); i++) {                                             // go through all players
				output += "\n" + i + ". " + players.get(i).character + " is a: " + players.get(i).getType();
			}
			return output;                                                         
		}

		public Game initGame() {                                                                   // -method to make a new game based off of the current values stored in the GameMaker
			Player[] players2 = new Player[players.size()];                                        // make new array of players with the length of the current ArrayList of players
			for (int i = 0; i < players.size(); i++) {                                             // go through every player in the array
				players2[i] = players.get(i);                                                      // set the current player in the array to the corresponding one in the ArrayList
			}
			return new Game(boardWidth, players2);                                      		   // return a new game with the current board size and players array
		}

		public Game initAverageGame(String player) {                                               // -method to make a regular game
			if (player.equals("X")) {                                                              // test if input is x
				return new Game(3, new Player[] { new Player(false, "X"), new Player(true, "O") });// make new regular game with the player being X
			}                                                     
			return new Game(3, new Player[] { new Player(true, "X"), new Player(false, "O") });    // make new regular game with the player being O
		}

	}
	
	public class Game {

		private Player[] players;

		private Board board;

		private int playerOn;
		final static private int depth = -10;

		Game(int boardWidth, Player[] players) {
			playerOn = 0;
			board = new Board(boardWidth);
			this.players = players;
		}
		
		public String Start() {
			if(players[playerOn].isComputer) {
				return DoNextTurn(-1, -1);
			}
			return "" + display();
		}

		public String DoNextTurn(int x, int y) {
			String output = "";
			if (x == -1 || y == -1) {
				output = players[playerOn].doMove(-1, -1, this.board, getPlayerChars(), playerOn, depth);
			} else if (x >= 0 && x < board.width && y >= 0 && y < board.width) {
				if (this.board.getAtPos(x, y).isBlank()) {
					output = players[playerOn].doMove(x, y, this.board, getPlayerChars(), playerOn, depth);
				} else {
					return "\nThat is an invalid location there is already a(n) " + board.getAtPos(x, y) + " there";
				}
			}else {
				return "\nThat is an invalid position\n> Do one within the board width: 0 - " + board.width;
			}
			if(!playerIncrement().isBlank()) {
				return output + playerIncrement();
			}
			while (players[playerOn].isComputer) {
				output += players[playerOn].doMove(-1, -1, this.board, getPlayerChars(), playerOn, depth);
				if(!playerIncrement().isBlank()) {
					return output + playerIncrement();
				}
			} 
			output += "\nIt is player " + players[playerOn].character + "'s move now";
			return output;
		}
		
		private String playerIncrement() {
			if(!getWinner().isBlank()) {
				if(getWinner().contains("tie")) {
					return "\n__**! It Is A Tie !**__";
				}
				return "\n__**! " + players[playerOn].getType() + " " + players[playerOn].character + " Has Won !**__";
			}
			this.playerOn = increment(this.playerOn, this.players.length);
			return "";
		}
		
		private String[] getPlayerChars() {
			String[] output = new String[players.length];
			for (int i = 0; i < players.length; i++) {
				output[i] = players[i].character;
			}
			return output;
		}

		public String display() {
			return board.display();
		}

		private String getWinner() {
			return this.board.checkWinner() ;
		}

		public String getCurrentPlayer() {
			return players[playerOn].character;
		}
	}
	
	public class Player {

		private boolean isComputer;

		String character;

		Player(boolean isComputer, String character) {
			this.isComputer = isComputer;
			this.character = character;
		}
		
		public String getType() {
			if(isComputer) {
				return "Computer";
			}
			return "Human";
		}

		public String doMove(int x, int y, Board b, String[] allPlayers, int posInList, int depth) {
			if (x == -1 || y == -1 || this.isComputer) {
				doBestMove(b, allPlayers, posInList, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
			} else {
				b.setPos(x, y, character);
			}
			return b.display() + "Player " + this.character + " went";
		}

		private void doBestMove(Board b, String[] allPlayers, int posInList, int depth, int alpha, int beta) {
			System.out.println("doBestMove at depth: " + depth);
			Point p = new Point (0,0);
			Point bestMove = p;
			Point start = p;
			int bestScore = Integer.MIN_VALUE;
			System.out.println("begining: " + start.x + ", " + start.y);
			outerloop:
			for (int i = start.x; i < b.width; i++) {
				for (int j = start.y; j < b.width; j++) {
					if (b.getAtPos(i, j).isBlank()) {
						b.setPos(i, j, character);
						int score = minimax(b, allPlayers, increment(posInList, allPlayers.length), depth + 1, alpha, beta);
						b.resetPos(i, j);
						if (score > bestScore) {
							bestScore = score;
							bestMove.setLocation(i, j);
							System.out.println("\n --  newBestMove: " + bestMove.getX() + ", " + bestMove.getY() + " score: " + score);
							alpha = score;
			                if(beta <= alpha) {
			                	break outerloop;
			                }
						}
					}
				}
			}
			System.out.println("End of doBestMove: " + bestMove.getX() + ", " + bestMove.getY());
			b.setPos(bestMove.x, bestMove.y, character);
		}
		private int minimax(Board b, String[] allPlayers, int posInList, int depth, int alpha, int beta) {
			System.out.println("  --  minimax at depth: " + depth);
			String s = b.checkWinner();	
			if (!s.isBlank()) {
				if (s.equals(this.character)) {
					System.out.print("  --  --  won");
					System.out.println(b.display());
			    	return Integer.MAX_VALUE;
			    }
			    if(s.equalsIgnoreCase("tie")){
			    	System.out.print("  --  --  tied");
			    	System.out.println(b.display());
			    	return 0;
			    }
			    System.out.print("  --  --  lost");
			    System.out.println(b.display());
			    return Integer.MIN_VALUE;
			}
			if(depth >= 0){
				System.out.print("  --  -- end of depth");
				System.out.println(b.display());
				return 1;
			}
			if(allPlayers[posInList].equals(this.character)){
				int bestScore = Integer.MIN_VALUE;
				outerloop:
			    for(int i = 0; i < b.width; i++){
			    	for(int j = 0; j < b.width; j++){
			        	if(b.getAtPos(i, j).isBlank()){
			                b.setPos(i,j, this.character);
			                int score = minimax(b, allPlayers, increment(posInList, allPlayers.length), depth + 1, alpha, beta);
			                b.resetPos(i,j);
			                bestScore = Math.max(score, bestScore);
			                alpha = Math.max(alpha, score);
			                if(beta <= alpha) {
			                	break outerloop;
			                }
			            }
			        }
			    }
			    return bestScore;
			}
			int worstScore = Integer.MAX_VALUE;
			outerloop:
			for(int i = 0; i < b.width; i++){
				for(int j = 0; j < b.width; j++){
			    	if(b.getAtPos(i,j).isBlank()){
			        	b.setPos(i,j, allPlayers[posInList]);
			        	int score = minimax(b, allPlayers, increment(posInList, allPlayers.length), depth + 1, alpha, beta);
			            b.resetPos(i,j);
			            worstScore = Math.min(score, worstScore);
			            beta = Math.min(beta, score);
		                if(beta <= alpha) {
		                 	break outerloop;
		                }
			        }
			    }
			}
			return worstScore;
		}
	}
	
	class Board {

		private String[][] board;

		int width;

		Board(int width) {
			board = new String[width][width];
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < width; j++) {
					board[i][j] = " ";
				}
			}
			this.width = width;
		}

		private String display() {
			String output = "\n```";
			for (int j = 0; j < width-1; j++) {
				output += "\n ";
				for (int i = 0; i < width - 1; i++) {
					output += board[i][j] + " | ";
				}
				output += board[width-1][j] + "\n -";
				for (int i = 0; i < width - 1; i++) {
					output += " + -";
				}
			}
			output += "\n ";
			for (int i = 0; i < width - 1; i++) {
				output += board[i][width-1] + " | ";
			}
			output += board[width-1][width-1];
			return output += "\n```";
		}

		public String getAtPos(int x, int y) {
			return board[x][y];
		}

		public void resetPos(int x, int y) {
			board[x][y] = " ";
		}
		
		public void setPos(int x, int y, String s) {
			board[x][y] = s;
		}

		public String checkWinner() {
			for (int i = 0; i < width - 2; i++) {
				for (int j = 0; j < width; j++) {
					if(anyNumEquals()){
						return board[i][j];
					}
					if (anyNumEquals(board[i][j], board[i+1][j], board[i+2][j])) {                 // vertical test of 3 in a row 
						return board[i][j];
					}
					if (anyNumEquals(board[j][i], board[j][i+1], board[j][i+2])) {                 // horizontal test of 3 in a row
						return board[j][i];
					}
					if(j < width - 2) {                                                            // testing if able to get diagonal of 3 in a row 
						if (anyNumEquals(board[i][j], board[i+1][j+1], board[i+2][j+2])) {         // diagonal test (from top left corner) for 3 in a row
							return board[i][j];
						}
						int jRev = (width-1)-j;
						if (anyNumEquals(board[i][jRev], board[i+1][jRev-1], board[i+2][jRev-2])) {// diagonal test (from bottom left corner) for 3 in a row
							return board[i][jRev];
						}
					}
				}
			}
			for (int i = 0; i < width; i++) {
				for(int j = 0; j < width; j++) {
					if(board[i][j].isBlank()) {                                                    // if a spot that is empty exits
						i = width;
						break;                                                                     // break from the loop
					} 
					if(i >= width-1 && j >= width-1) {                                       	   // if it reaches the end and all the spots were filled 
						return "tie";                                                              // it returns tie
					}
				}
			}
			return "";
		}
		
		public Point getFirstEmpty() {
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < width; j++) {
					if(board[i][j].isBlank()) {
						return new Point(i, j);
					}
				}
			}
			return null;
		}
	}
}
