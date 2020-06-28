package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import org.apache.logging.log4j.util.SystemPropertiesPropertySource;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class TicTacToe extends CustomMessageCreateListener {
	private static final String COMMAND = "!TicTacToe";
	final String TopLeft = "!TopLeft";
	final String TopMiddle = "!TopMiddle";
	final String TopRight = "!TopRight";
	final String MiddleLeft = "!MiddleLeft";
	final String MiddleMiddle = "!MiddleMiddle";
	final String MiddleRight = "!MiddleRight";
	final String BottomLeft = "!BottomLeft";
	final String BottomMiddle = "!BottomMiddle";
	final String BottomRight = "!BottomRight";

	Stack<String> commands = new Stack<String>();
	boolean[][] boardUser = new boolean[3][3];
	boolean[][] boardCPU = new boolean[3][3];
	boolean userWin = false;
	boolean CPUWin = false;
	boolean stop = false;
	boolean checkCommand = false;

	public TicTacToe(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String location = event.getMessageContent();
		if (location.startsWith(COMMAND)) {
			stop = false;
			userWin = false;
			CPUWin = false;
			boardUser = new boolean[3][3];
			boardCPU = new boolean[3][3];
			commands = new Stack<String>();
			commands.push(TopRight);
			commands.push(MiddleRight);
			commands.push(MiddleLeft);
			commands.push(BottomMiddle);
			commands.push(TopLeft);
			commands.push(TopMiddle);
			commands.push(BottomLeft);
			commands.push(BottomRight);
			commands.push(MiddleMiddle);
			
			event.getChannel().sendMessage("Welcome to the Game.");
			event.getChannel().sendMessage("Choose where you want to place the X");
		}
		location = event.getMessageContent();
		if (stop == false) {
			if (location.equals(TopLeft) || location.equals(TopMiddle) || location.equals(TopRight)
					|| location.equals(MiddleLeft) || location.equals(MiddleMiddle) || location.equals(MiddleRight)
					|| location.equals(BottomLeft) || location.equals(BottomMiddle) || location.equals(BottomRight)) {
				checkCommand = false;
				for (int i = 0; i < commands.size(); i++) {
					if (commands.get(i).equals(location)) {
						checkCommand = true;
						break;
					}
				}
				if (checkCommand) {
					event.getChannel().sendMessage("User place X at " + location);
					fillUserLocation(location);
					checkWinner();
				}
				if (userWin) {
					event.getChannel().sendMessage("User wins");
					stop = true;
					event.getChannel().sendMessage("Type !TicTacToe to start a new game");
				} else {
					event.getChannel().sendMessage("CPU's turn");
					if(commands.isEmpty() == false) {
					String CPULocation = commands.pop();
					event.getChannel().sendMessage("CPU places O at " + CPULocation);
					fillCPULocation(CPULocation);
					checkWinner();
					if (CPUWin) {
						event.getChannel().sendMessage("CPU wins");
						stop = true;
						event.getChannel().sendMessage("Type !TicTacToe to start a new game");
					} else {
						event.getChannel().sendMessage("User's turn");
					}
					
				}
					else {
						event.getChannel().sendMessage("Draw");
						stop = true;
						event.getChannel().sendMessage("Type !TicTacToe to start a new game");
					}
				}
			}
		}
	}

	public void fillUserLocation(String location) {
		for (int i = commands.size() - 1; i >= 0; i--) {
			if (commands.get(i).equals(location)) {
				commands.remove(i);
				break;
			}
		}
		// TODO Auto-generated method stub
		if (location.equals(TopLeft)) {
			boardUser[0][0] = true;
		} else if (location.equals(TopMiddle)) {
			boardUser[0][1] = true;
		} else if (location.equals(TopRight)) {
			boardUser[0][2] = true;
		} else if (location.equals(MiddleLeft)) {
			boardUser[1][0] = true;
		} else if (location.equals(MiddleMiddle)) {
			boardUser[1][1] = true;
		} else if (location.equals(MiddleRight)) {
			boardUser[1][2] = true;
		} else if (location.equals(BottomLeft)) {
			boardUser[2][0] = true;
		} else if (location.equals(BottomMiddle)) {
			boardUser[2][1] = true;
		} else if (location.equals(BottomRight)) {
			boardUser[2][2] = true;
		}
	}

	public void fillCPULocation(String location) {
		// TODO Auto-generated method stub
		if (location.equals(TopLeft)) {
			boardCPU[0][0] = true;
		} else if (location.equals(TopMiddle)) {
			boardCPU[0][1] = true;
		} else if (location.equals(TopRight)) {
			boardCPU[0][2] = true;
		} else if (location.equals(MiddleLeft)) {
			boardCPU[1][0] = true;
		} else if (location.equals(MiddleMiddle)) {
			boardCPU[1][1] = true;
		} else if (location.equals(MiddleRight)) {
			boardCPU[1][2] = true;
		} else if (location.equals(BottomLeft)) {
			boardCPU[2][0] = true;
		} else if (location.equals(BottomMiddle)) {
			boardCPU[2][1] = true;
		} else if (location.equals(BottomRight)) {
			boardCPU[2][2] = true;
		}
	}

	public void checkWinner() {
		// User checks
		// rows
		if (boardUser[0][0] && boardUser[0][1] && boardUser[0][2]) {
			userWin = true;
		} else if (boardUser[1][0] && boardUser[1][1] && boardUser[1][2]) {
			userWin = true;
		} else if (boardUser[2][0] && boardUser[2][1] && boardUser[2][2]) {
			userWin = true;
		}
		// columns
		else if (boardUser[0][0] && boardUser[1][0] && boardUser[2][0]) {
			userWin = true;
		} else if (boardUser[0][1] && boardUser[1][1] && boardUser[2][1]) {
			userWin = true;
		} else if (boardUser[0][2] && boardUser[1][2] && boardUser[2][2]) {
			userWin = true;
		}
		// diagonals
		else if (boardUser[0][0] && boardUser[1][1] && boardUser[2][2]) {
			userWin = true;
		} else if (boardUser[0][2] && boardUser[1][1] && boardUser[2][0]) {
			userWin = true;
		}

		// CPU checks
		// rows
		else if (boardCPU[0][0] && boardCPU[0][1] && boardCPU[0][2]) {
			CPUWin = true;
		} else if (boardCPU[1][0] && boardCPU[1][1] && boardCPU[1][2]) {
			CPUWin = true;
		} else if (boardCPU[2][0] && boardCPU[2][1] && boardCPU[2][2]) {
			CPUWin = true;
		}
		// columns
		else if (boardCPU[0][0] && boardCPU[1][0] && boardCPU[2][0]) {
			CPUWin = true;
		} else if (boardCPU[0][1] && boardCPU[1][1] && boardCPU[2][1]) {
			CPUWin = true;
		} else if (boardCPU[0][2] && boardCPU[1][2] && boardCPU[2][2]) {
			CPUWin = true;
		}
		// diagonals
		else if (boardCPU[0][0] && boardCPU[1][1] && boardCPU[2][2]) {
			CPUWin = true;
		} else if (boardCPU[0][2] && boardCPU[1][1] && boardCPU[2][0]) {
			CPUWin = true;
		}

	}

}
