package org.jointheleague.modules;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.CachedMessagePinEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Gomoku extends CustomMessageCreateListener {

	private static final String COMMAND = "!gomoku";
	String[][] gameboard = new String[15][15];
	boolean inProgress = false;
	boolean playerWon = false;
	boolean player1Down = false;
	boolean player2Down = false;
	String player1Name = "Player 1";
	String player2Name = "Player 2";
	int playerNum = 0;
	String board = "";

	public Gomoku(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Starts a game of gomoku. !gomokuRules shows the rules. To play, put in a coordinate (e.g. !gm0,0) to place a marker there. Player 1's marker is ㊋. Player 2's marker is ㊌. To rename yourself, use !gmName (e.g. !gmName Player) during your turn.");
	}



public String checkWin() {
		
		for(int i=0; i<gameboard.length; i++) {
			for(int j=0; j<gameboard[i].length; j++) {
				if(gameboard[i][j] != "▢") {
					if(checkDown(i, j) || checkRight(i,j) || checkDiagonal(i,j) || checkOtherDiagonal(i,j)) {
						 if(gameboard[i][j] == "㊋") {
							 return "player1";
						 }
						 else {
							 return "player2";
						 }
					}
				}
			}
		}
		return "no-one";
	}



	public boolean checkDown(int x, int y) {
		if (y <= gameboard[x].length - 5) {
			for (int i = 1; i < 5; i++) {
				if (gameboard[x][y + i] != gameboard[x][y]) {
					return false;
				}
			}
			return true;

		} else {
			return false;
		}
	}

	public boolean checkRight(int x, int y) {
		if (x <= gameboard.length - 5) {
			for (int i = 1; i < 5; i++) {
				if (gameboard[x + i][y] != gameboard[x][y]) {
					return false;
				}
			}
			return true;

		} else {
			return false;
		}
	}

	public boolean checkDiagonal(int x, int y) {
		if (x <= gameboard.length - 5 && y <= gameboard.length - 5) {
			for (int i = 1; i < 5; i++) {
				if (gameboard[x + i][y + i] != gameboard[x][y]) {
					return false;
				}
			}
			return true;

		} else {
			return false;
		}
	}

	public boolean checkOtherDiagonal(int x, int y) {
		if (x >= 4 && y <= gameboard.length - 5) {
			for (int i = 1; i < 5; i++) {
				if (gameboard[x - i][y - i] != gameboard[x][y]) {
					return false;
				}
			}
			return true;

		} else {
			return false;
		}
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {

			String command = event.getMessageContent().replaceAll(" ", "").replace("!gomoku", "");

			if (inProgress == false) {
				board = "";
				for (int i = 0; i < gameboard.length; i++) {
					for (int j = 0; j < gameboard[i].length; j++) {
						gameboard[i][j] = "▢";
					}
				}
				inProgress = true;
			}

			if (command.equals("")) {
				for (int i = 0; i < gameboard.length; i++) {
					for (int j = 0; j < gameboard[i].length; j++) {
						board += gameboard[i][j];
						if (j == 14) {
							board += "\n";
							break;
						}
					}

				}
				event.getChannel().sendMessage(board);
				event.getChannel().sendMessage(player1Name + "'s turn");

			} else if (command.equals("Rules")) {
				event.getChannel().sendMessage(
						"Players alternate turns placing a stone of their color on an empty square.\nThe winner is the first player to form an unbroken chain of five stones horizontally, vertically, or diagonally.\nThe origin of this board (0,0) is in the top left corner.");
			}

		} else if (event.getMessageContent().contains("!gm") && inProgress) {
			String command = event.getMessageContent().replace("!gm", "").replaceAll(" ", "");;

			if (command.contains(",")) {

				

				String ycoor = command.substring(0, command.indexOf(','));
				String xcoor = command.substring(command.indexOf(',') + 1);

				int xc = Integer.parseInt(xcoor);
				int yc = Integer.parseInt(ycoor);

				if (xc > 14 || xc < 0 || yc > 14 || yc < 0) {
					event.getChannel().sendMessage("That coordinate is not on the board!");
				}
				if (playerNum == 0) {
					if (gameboard[xc][yc].equals("▢")) {
						gameboard[xc][yc] = "㊋";
						playerNum = 1;
					} else {
						event.getChannel().sendMessage("You can't put a marker there!");
					}

					
				} else if (playerNum == 1) {
					if (gameboard[xc][yc].equals("▢")) {
						gameboard[xc][yc] = "㊌";
						playerNum = 0;
					} else {
						event.getChannel().sendMessage("You can't put a marker there!");
					}
				}

				board = "";

				for (int i = 0; i < gameboard.length; i++) {
					for (int j = 0; j < gameboard[i].length; j++) {
						board += gameboard[i][j];
						if (j == 14) {
							board += "\n";
							break;
						}
					}

				}


				event.getChannel().sendMessage(board);
						if(checkWin().equals("player1")) {
							event.getChannel().sendMessage(player1Name+" has won!");
							inProgress = false;
							event.getChannel().sendMessage("Game Over!");
						} else if(checkWin().equals("player2")) {
							event.getChannel().sendMessage(player2Name+" has won!");
							inProgress = false;
							event.getChannel().sendMessage("Game Over!");
						} else {
							
							if (playerNum == 0) {
								event.getChannel().sendMessage(player1Name + "'s turn");
							} else {
								event.getChannel().sendMessage(player2Name + "'s turn");
							}
						}
					
				

			} else if (command.contains("Name")) {
				String name = command.substring(command.indexOf('e') + 2, command.length());
				if (playerNum == 0) {
					event.getChannel().sendMessage("Player 1's new name is " + name);
					player1Name = name;
				} else {
					event.getChannel().sendMessage("Player 2's new name is " + name);
					player2Name = name;
				}
			} else {
				event.getChannel().sendMessage("That coordinate is not on the board!");
			}

		}
	}

}
