package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

/**
 * The representation of an individual game
 *
 */
public class MinesweeperGame {
	public static enum BlockType {
		ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,EMPTY,BOMB;
	}
	public static enum BlockState {
		DISCOVERED,UNDISCOVERED,FLAGGED
	}
	
	private int dimensions;
	private int mineCount;
	Cell[][] playingField;
	int tileCounter = 0;
	int flagCounter = 0;
	int winAmount;
	boolean gameOver = false;
	boolean winOver = false;
	
	/**
	 * The constructor makes sure that the side length does not exceed 10 and there are more cells than
	 * mines on the playing field.
	 * 
	 * @param dimensions The side length of the playing field
	 * @param mineCount The amount of mines in the playing field
	 */
	MinesweeperGame(int dimensions, int mineCount) {
		
		if(dimensions > 10 || mineCount > dimensions*dimensions) {
			this.dimensions = 10;
			this.mineCount = 25;
		}
		
		this.dimensions = dimensions;
		this.mineCount = mineCount;
		this.winAmount = (dimensions*dimensions)-mineCount;
		generateMap();
	}
	
	
	/**
	 * Generates a new board based on the given member variables of dimension/side length and mine count
	 * */
	void generateMap() {
		playingField = new Cell[dimensions][dimensions];
		
		//Create a Cell for each slot on the playing field
		for(int i = 0; i < playingField.length; i++) {
			for(int j = 0; j < playingField[i].length; j++) {
				playingField[i][j] = new Cell();
			}
		}
		
		//Generate the Mines based on the specified amount
		int minesGenerated = 0;
		while(minesGenerated < mineCount) {
			int newX = new Random().nextInt(dimensions);
			int newY = new Random().nextInt(dimensions);
			if(playingField[newX][newY].type != BlockType.BOMB) {
				playingField[newX][newY].type = BlockType.BOMB;
				minesGenerated++;
			}
		}
		
		//Assign number values to cells which aren't mines but are next to mines
		for(int i = 0; i < playingField.length; i++) {
			for(int j = 0; j < playingField[i].length; j++) {
				if(playingField[i][j].type != BlockType.BOMB) {
					Cell[] surroundingCells = getSurroundingCells(i,j);
					int incrementer = 0;
					for(int k = 0; k < surroundingCells.length; k++) {
						if(surroundingCells[k].type == BlockType.BOMB) {
							incrementer++;
						}
					}
					if(incrementer > 0) {
						playingField[i][j].type = getTypeFromNumber(incrementer);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param xPosition The x-position of the desired cell
	 * @param yPosition The y-position of the desired cell
	 * @return This returns an array of all the cells surrounding the given cell
	 */
	Cell[] getSurroundingCells(int xPosition, int yPosition) {
		ArrayList<Cell> cellTempList = new ArrayList<Cell>();
		if(xPosition > 0) {
			cellTempList.add(playingField[xPosition-1][yPosition]);
		}
		if(yPosition > 0) {
			cellTempList.add(playingField[xPosition][yPosition-1]);
		}
		if(xPosition < this.dimensions-1) {
			cellTempList.add(playingField[xPosition+1][yPosition]);
		}
		if(yPosition < this.dimensions-1) {
			cellTempList.add(playingField[xPosition][yPosition+1]);
		}
		if(xPosition > 0 && yPosition > 0) {
			cellTempList.add(playingField[xPosition-1][yPosition-1]);
		}
		if(xPosition < this.dimensions-1 && yPosition > 0) {
			cellTempList.add(playingField[xPosition+1][yPosition-1]);
		}
		if(xPosition > 0 && yPosition < this.dimensions-1) {
			cellTempList.add(playingField[xPosition-1][yPosition+1]);
		}
		if(xPosition < this.dimensions-1 && yPosition < this.dimensions-1) {
			cellTempList.add(playingField[xPosition+1][yPosition+1]);
		}
		
		return cellTempList.toArray(new Cell[cellTempList.size()]);
	}
	
	//Alternate map generation technique which may be added later on
	void generateMapPercentage() {
		
	}
	
	
	/**
	 * The function will toggle the flag state on cells. Cells which are already discovered can't be flagged. Flagged cells will revert to undiscovered cells when the flag is toggled off
	 * 
	 * @param xPos X-position of target cell
	 * @param yPos Y-position of target cell
	 */
	public void flagCell(int xPos, int yPos) {
		if(playingField[xPos][yPos].state == BlockState.UNDISCOVERED) {
			playingField[xPos][yPos].state = BlockState.FLAGGED;
			flagCounter++;
		}
		else if(playingField[xPos][yPos].state == BlockState.FLAGGED) {
			playingField[xPos][yPos].state = BlockState.UNDISCOVERED;
			flagCounter--;
		}
		checkForWin();
	}
	/**
	 * The function will uncover cells which are undiscovered (flagged cells are not considered undiscovered, so they won't be able to be discovered)
	 * If the discovered cell is a mine, the game is over.
	 * 
	 * @param xPos X-position of target cell
	 * @param yPos Y-Position of target cell
	 * @see #loseGame
	 */
	public void interactCell(int xPos, int yPos) {
		if(playingField[xPos][yPos].state == BlockState.UNDISCOVERED) {
			playingField[xPos][yPos].state = BlockState.DISCOVERED;
			if(playingField[xPos][yPos].type != BlockType.BOMB) {
				tileCounter++;
			}
		}
		if(playingField[xPos][yPos].type == BlockType.BOMB) {
			loseGame();
		}
		checkForWin();
	}
	/**
	 * Checks if all tiles which aren't mines have been discovered and all mines have been flagged
	 */
	void checkForWin() {
		if(tileCounter == winAmount) {
			if(flagCounter == mineCount) {
				//Notifies MinesweeperListener that the game has been won
				winOver = true;
			}
		}
	}
	/**
	 * 
	 * @param value Integer to convert into the form of BlockType. Must be between 1 and 8 inclusive.
	 * @return The BlockType equivalent of the given number
	 */
	BlockType getTypeFromNumber(int value) {
		int index = value-1;
		if(value >= 0 && value < 8) {
			return BlockType.values()[index];
		}
		else {
			return null;
		}	
	}
	
	/**
	 * The functions requests the MinesweeperListener to display the completely undiscovered grid and delete the game object.
	 */
	void loseGame() {
		//Set every cell to the state Discovered
		for(int i = 0; i < playingField.length; i++) {
			for(int j = 0; j < playingField[i].length; j++) {
				if(playingField[i][j].state == BlockState.FLAGGED && playingField[i][j].type == BlockType.BOMB) {
					//Do Nothing
				}
				else {
					playingField[i][j].state = BlockState.DISCOVERED;
				}
				
			}
		}
		
		//Set the gameOver boolean to true to notify MinesweeperListener that the game is over
		gameOver = true;
	}
	
	/**
	 * Object representation of an individual game cell
	 */
	class Cell {
		BlockType type = BlockType.EMPTY;
		BlockState state = BlockState.UNDISCOVERED;
	}
}
