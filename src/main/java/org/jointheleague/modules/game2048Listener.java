package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class game2048Listener extends CustomMessageCreateListener{
	
	boolean playing = false;
	
	public int[][] gameBoard = new int[4][4];
	
	Random randy = new Random();
	
	int score;

	public game2048Listener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		
		String a = event.getMessageContent();
		if(a.equals("!2048")&&!playing) {
			playing = true;
			score = 0;
			event.getChannel().sendMessage("Welcome to 2048!");
			event.getChannel().sendMessage("Use WASD+enter to play");
			event.getChannel().sendMessage("Combine numbers to get 2048");
			placeRandom();
			placeRandom();
			event.getChannel().sendMessage("Score: "+score);
			displayBoard(event);
			
		}
		boolean gameOver = false;
		if(playing) {
			switch(a) {
			case "w":
				gameOver =move(0);
				event.getChannel().sendMessage("Score: "+score);
				displayBoard(event);
				break;
			case "s":
				gameOver =move(1);
				event.getChannel().sendMessage("Score: "+score);
				displayBoard(event);
				break;
			case "a":
				gameOver =move(2);
				event.getChannel().sendMessage("Score: "+score);
				displayBoard(event);
				break;
			case "d":
				gameOver =move(3);
				event.getChannel().sendMessage("Score: "+score);
				displayBoard(event);
				break;
			case "!stop":
				playing = false;
				break;
			}
		}
		if(gameOver) {
			event.getChannel().sendMessage("Game Over");
			event.getChannel().sendMessage("Score: "+score);
			event.getChannel().sendMessage("Type !2048 to play again");
			playing = false;
		}
		
		
	}
	
	public void displayBoard(MessageCreateEvent event) {
		String out = "";
		for(int y = 0; y<gameBoard.length;y++) {
			for(int x = 0; x<gameBoard[0].length;x++) {
				out += format(gameBoard[y][x]);
				out += " ";
			}
			out += "\n";
		}
		event.getChannel().sendMessage("```"+out+"```");
		
	}
	
	public void setUpBoard() {
		for(int y = 0; y<gameBoard.length;y++) {
			for(int x = 0; x<gameBoard[0].length;x++) {
				gameBoard[y][x] = 0;
			}
		}
	}
	
	public ArrayList<int[]> placeRandom() {
	    ArrayList<int[]> zeroList = new ArrayList<int[]>();
	    for(int i = 0;i<gameBoard.length;i++) {
	        for(int j = 0;j<gameBoard[0].length;j++) {
	        	if(gameBoard[i][j] == 0) {
	        		zeroList.add(new int[] {i,j});
	        	}
	        }
	    }
	    	int[] newBlockPos;
	    	if(zeroList.size()==1) {
	    		newBlockPos = zeroList.get(0);
	    	}else if(zeroList.size()==0){
	    		return new ArrayList<int[]>();
	    	}else {
	    		newBlockPos = zeroList.get(randy.nextInt(zeroList.size()-1));
	    	}
	        
	        gameBoard[newBlockPos[0]][newBlockPos[1]] = get1or2();
	    

	    return zeroList;
	}
	
	public int getLargestTile() {
		int amount = 0;
		for(int i = 0;i<gameBoard.length;i++) {
			for(int j = 0;j<gameBoard[0].length;j++) {
				if(gameBoard[i][j]>amount) {
					amount = gameBoard[i][j];
				}
			}
		}
		return (int) Math.pow(2, amount);
	}
	
	public int getDigits(int num) {
		int dig = 1;
		int sub = num;
		while(sub>=10) {
			dig++;
			sub = sub/10;
		}
		return dig;
	}
	
	public String format(int num) {
		int spaces = getDigits(getLargestTile())-getDigits((int)Math.pow(2, num));
		String out = "";
		if(num==0) {
			out+= 0;
		}else {
			out +=((int)Math.pow(2, num));
		}
		
		for(int i = 0;i<spaces;i++) {
			out+=" ";
		}
		
		
		return out;
	}
	
	public int get1or2() {
		double temp = Math.random()*10;
		int out = 1;
		if(temp>9) {
			out = 2;
		}
		return out;
	}
	
	public boolean move(int direction){
		int[][] preboard = gameBoard;
		if(direction == 0) {  //UP
			for(int x = 0;x<gameBoard[0].length;x++) {
				int lastNumber = gameBoard[0][x];
				int lastNumIndex = 0;
				for(int y = 1;y<gameBoard.length;y++) {
					if(gameBoard[y][x] != 0) {
	                    if(lastNumber == 0) {
	                    	gameBoard[lastNumIndex][x] = gameBoard[y][x];
	    	                gameBoard[y][x] = 0;
	    	                lastNumber = gameBoard[lastNumIndex][x];
	                    }else if(lastNumber == gameBoard[y][x]){
	                    	gameBoard[lastNumIndex][x] = lastNumber+1;
	                        gameBoard[y][x] = 0;
	                        score += Math.pow(gameBoard[lastNumIndex][x],2);
	                        lastNumIndex +=1;
	                        lastNumber = gameBoard[lastNumIndex][x];
	                    }else {
	                    	if(lastNumIndex+1 < y) {
	                    		gameBoard[lastNumIndex+1][x] = gameBoard[y][x];
                                gameBoard[y][x] = 0;
	                    	}
	                        lastNumIndex += 1;
	                        lastNumber = gameBoard[lastNumIndex][x];
	                    }
					}
				}
			}
		}
		if(direction == 1) {  //DOWN
			for(int x = 0;x<gameBoard[0].length;x++) {
				int lastNumber = gameBoard[gameBoard.length-1][x];
				int lastNumIndex = gameBoard.length-1;
				for(int y = gameBoard.length-2;y>=0;y--) {
					if(gameBoard[y][x] != 0) {
	                    if(lastNumber == 0) {
	                    	gameBoard[lastNumIndex][x] = gameBoard[y][x];
	    	                gameBoard[y][x] = 0;
	    	                lastNumber = gameBoard[lastNumIndex][x];
	                    }else if(lastNumber == gameBoard[y][x]) {
	                    	gameBoard[lastNumIndex][x] = lastNumber+1;
	                        gameBoard[y][x] = 0;
	                        score += Math.pow(gameBoard[lastNumIndex][x],2);
	                        lastNumIndex -=1;
	                        lastNumber = gameBoard[lastNumIndex][x];
	                    }else {
	                    	if(lastNumIndex-1 > y) {
	                    		gameBoard[lastNumIndex-1][x] = gameBoard[y][x];
                                gameBoard[y][x] = 0;
	                    	}
	                        lastNumIndex -= 1;
	                        lastNumber = gameBoard[lastNumIndex][x];
	                    }
					}
				}
			}
		}
		if(direction == 2) {  //LEFT
			for(int y = 0;y<gameBoard[0].length;y++) {
				int lastNumber = gameBoard[y][0];
				int lastNumIndex = 0;
				for(int x = 1;x<gameBoard[0].length;x++) {
					if(gameBoard[y][x] != 0) {
	                    if(lastNumber == 0) {
	                    	gameBoard[y][lastNumIndex] = gameBoard[y][x];
	    	                gameBoard[y][x] = 0;
	    	                lastNumber = gameBoard[y][lastNumIndex];
	                    }else if(lastNumber == gameBoard[y][x]) {
	                    	gameBoard[y][lastNumIndex] = lastNumber+1;
	                        gameBoard[y][x] = 0;
	                        score += Math.pow(gameBoard[y][lastNumIndex],2);
	                        lastNumIndex +=1;
	                        lastNumber = gameBoard[y][lastNumIndex];
	                    }else {
	                    	if(lastNumIndex+1 < x) {
	                    		gameBoard[y][lastNumIndex+1] = gameBoard[y][x];
                                gameBoard[y][x] = 0;
	                    	}
	                        lastNumIndex += 1;
	                        lastNumber = gameBoard[y][lastNumIndex];
	                    }
					}
				}
			}
		}
		if(direction == 3) {  //RIGHT
			for(int y = 0;y<gameBoard[0].length;y++) {
				int lastNumber = gameBoard[y][gameBoard.length-1];
				int lastNumIndex = gameBoard.length-1;
				for(int x = gameBoard[0].length-2;x>=0;x--) {
					if(gameBoard[y][x] != 0) {
	                    if(lastNumber == 0) {
	                    	gameBoard[y][lastNumIndex] = gameBoard[y][x];
	    	                gameBoard[y][x] = 0;
	    	                lastNumber = gameBoard[y][lastNumIndex];
	                    }else if(lastNumber == gameBoard[y][x]) {
	                    	gameBoard[y][lastNumIndex] = lastNumber+1;
	                        gameBoard[y][x] = 0;
	                        score += Math.pow(gameBoard[y][lastNumIndex],2);
	                        lastNumIndex -=1;
	                        lastNumber = gameBoard[y][lastNumIndex];
	                    }else {
	                    	if(lastNumIndex-1 > x) {
	                    		gameBoard[y][lastNumIndex-1] = gameBoard[y][x];
                                gameBoard[y][x] = 0;
	                    	}
	                        lastNumIndex -= 1;
	                        lastNumber = gameBoard[y][lastNumIndex];
	                    }
					}
				}
			}
		}
		ArrayList<int[]> zeroList;
		zeroList = placeRandom();

	System.out.println(zeroList.size());
		if(zeroList.size()<1) {
			boolean gameTest = true;
			for(int y = 0; y<gameBoard.length;y++) {
				for(int x = 0; x<gameBoard[0].length;x++) {
					if(x != gameBoard[0].length-1) {
						System.out.println("not x limit");
						System.out.println(x);
						System.out.println(gameBoard[0].length);
						System.out.println(x+", "+y);
						if(gameBoard[y][x] == gameBoard[y][x+1]) {
							System.out.println("game false");
							gameTest = false;
						}
					}
					if(y != gameBoard.length-1) {
						System.out.println("not y limit");
						System.out.println(y);
						System.out.println(gameBoard.length);
						System.out.println(x+", "+y);
						if(gameBoard[y][x] == gameBoard[y+1][x]) {
							System.out.println("game false");
							gameTest = false;
						}
					}
				}
			}
			if(gameTest) {
				return true;
			}
		}
		return false;
}
}
