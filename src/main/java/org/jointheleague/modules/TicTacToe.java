package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Stack;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class TicTacToe extends CustomMessageCreateListener {

	String userlocation ;
	String cpuLocation ;
	Boolean topLeft = false;
	Boolean topRight = false;
	Boolean topMiddle = false;
	Boolean middleLeft = false;
	Boolean middleRight = false;
	Boolean middleMiddle = false;
	Boolean bottomLeft = false;
	Boolean bottomRight = false;
	Boolean bottoMiddle = false;

	int counter = 0;

	int topRow = 0;
	int middleRow = 0;
	int bottomRow = 0;
	int leftColumn = 0;
	int middleColumn = 0;
	int rightColumn = 0;
	int leftDiagonal = 0;
	int rightDiagonal = 0;

	Stack<String> commands = new Stack<String>();
	Stack<String> userlist = new Stack<String>();
	Stack<String> CPUList = new Stack<String>();

	boolean[][] boardUser = new boolean[3][3];
	boolean[][] boardCpu = new boolean[3][3];

	boolean userWin = false;
	boolean cpuWin = false;

	public TicTacToe(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!TicTacToe")) {
			event.getChannel().sendMessage("Welcome to the game");
			commands.push("!TOPLEFT");
			commands.push("!TOPRIGHT");
			commands.push("TOPMIDDLE");
			commands.push("!MIDDLELEFT");
			commands.push("!MIDDLERIGHT");
			commands.push("!MIDDLEMIDDLE");
			commands.push("!BOTTOMLEFT");
			commands.push("!BOTTOMRIGHT");
			commands.push("!BOTTOMMIDDLE");

		}
		if (counter % 2 == 0) {
			event.getChannel().sendMessage("Select your position");
			userlocation = event.getMessageContent();
			if(userlocation!=null) {
				fillUserLocation(userlocation);
				checkWinner();
				if (userWin == true) {
					event.getChannel().sendMessage("User wins the game !");

				} else if (cpuWin == true) {
					event.getChannel().sendMessage("CPU wins the game");
				}
				counter++;

			}
			
		} else  if(counter%2==1){
			cpuLocation = commands.pop();
			event.getChannel().sendMessage("CPU selected " + CPUList.push(cpuLocation));
			fillCpuLocation(cpuLocation);
			checkWinner();
			
		
			if (userWin == true) {
				event.getChannel().sendMessage("User wins the game !");

			} else if (cpuWin == true) {
				event.getChannel().sendMessage("CPU wins the game");
			}
			counter++;
		}

	}

	void fillUserLocation(String location) {

		for (int i = commands.size() - 1; i > 0; i--) {
			if (location.equals(commands.get(i))) {
				commands.remove(i);
				break;
			}
		}

		if (location.equals("!TOPLEFT")) {

			boardUser[0][0] = true;

		} else if (location.equals("!TOPMIDDLE")) {
			boardUser[0][1] = true;
		} else if (location.equals("!TOPRIGHT")) {
			boardUser[0][2] = true;
		} else if (location.equals("!MIDDLELEFT")) {
			boardUser[1][0] = true;
		} else if (location.equals("!MIDDLEMIDDLE")) {
			boardUser[1][1] = true;
		} else if (location.equals("!MIDDLERIGHT")) {
			boardUser[1][2] = true;
		} else if (location.equals("!BOTTOMLEFT")) {
			boardUser[2][0] = true;
		} else if (location.equals("!BOTTOMMIDDLE")) {
			boardUser[2][1] = true;
		} else if (location.equals("!BOTTOMRIGHT")) {
			boardUser[2][2] = true;
		}
	}

	void fillCpuLocation(String location) {
	//	switch(location) {
		if (location.equals("!TOPLEFT")) {

			boardCpu[0][0] = true;
			

		} else if (location.equals("!TOPMIDDLE")) {
			boardCpu[0][1] = true;
		} else if (location.equals("!TOPRIGHT")) {
			boardCpu[0][2] = true;
		} else if (location.equals("!MIDDLELEFT")) {
			boardCpu[1][0] = true;
		} else if (location.equals("!MIDDLEMIDDLE")) {
			boardCpu[1][1] = true;
		} else if (location.equals("!MIDDLERIGHT")) {
			boardCpu[1][2] = true;
		} else if (location.equals("!BOTTOMLEFT")) {
			boardCpu[2][0] = true;
		} else if (location.equals("!BOTTOMMIDDLE")) {
			boardCpu[2][1] = true;
		} else if (location.equals("!BOTTOMRIGHT")) {
			boardCpu[2][2] = true;
	//	}

	}}

	void checkWinner() {
		// rows check
		if (boardUser[0][0] && boardUser[0][1] && boardUser[0][2]) {
			userWin = true;
		}

		else if (boardUser[1][0] && boardUser[1][1] && boardUser[1][2]) {
			userWin = true;
		} else if (boardUser[2][0] && boardUser[2][1] && boardUser[2][2]) {
			userWin = true;
		}

		// columns check
		else if (boardUser[0][0] && boardUser[1][0] && boardUser[2][0]) {
			userWin = true;

		}

		else if (boardUser[0][1] && boardUser[1][1] && boardUser[2][1]) {
			userWin = true;

		} else if (boardUser[0][2] && boardUser[1][2] && boardUser[2][2]) {
			userWin = true;

		}

		// diagonals check
		else if (boardUser[0][0] && boardUser[1][1] && boardUser[2][2]) {
			userWin = true;

		} else if (boardUser[2][0] && boardUser[1][1] && boardUser[2][0]) {
			userWin = true;

		}

		// rows check cpu
		else if (boardCpu[0][0] && boardCpu[0][1] && boardCpu[0][2]) {
			cpuWin = true;
		}

		else if (boardCpu[1][0] && boardCpu[1][1] && boardCpu[1][2]) {
			cpuWin = true;
		} else if (boardCpu[2][0] && boardCpu[2][1] && boardCpu[2][2]) {
			cpuWin = true;
		}

		// columns check
		else if (boardCpu[0][0] && boardCpu[1][0] && boardCpu[2][0]) {
			cpuWin = true;

		}

		else if (boardCpu[0][1] && boardCpu[1][1] && boardCpu[2][1]) {
			cpuWin = true;

		} else if (boardCpu[0][2] && boardCpu[1][2] && boardCpu[2][2]) {
			cpuWin = true;

		}

		// diagonals check
		else if (boardCpu[0][0] && boardCpu[1][1] && boardCpu[2][2]) {
			cpuWin = true;

		} else if (boardCpu[2][0] && boardCpu[1][1] && boardCpu[2][0]) {
			cpuWin = true;

		}

	}
}
