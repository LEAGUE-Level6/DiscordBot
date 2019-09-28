package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

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
	int winAmount;
	
	//Double Checks the given parameters and calls the generateMap function
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
	
	//Generates the Playing Field
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
	
	//Manually checks if each of the eight possible slots around a slot exists, and if it does, adds it to an array of all surrounding cells.
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
	
	public void flagCell(int xPos, int yPos) {
		if(playingField[xPos][yPos].state == BlockState.UNDISCOVERED) {
			playingField[xPos][yPos].state = BlockState.FLAGGED;
		}
	}
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
	void checkForWin() {
		
	}
	
	BlockType getTypeFromNumber(int value) {
		int index = value-1;
		if(value >= 0 && value < 8) {
			return BlockType.values()[index];
		}
		else {
			return null;
		}	
	}
	
	void loseGame() {
		
	}
	
	class Cell {
		BlockType type = BlockType.EMPTY;
		BlockState state = BlockState.UNDISCOVERED;
		
		void interact() {
			if(state == BlockState.UNDISCOVERED) {
				state = BlockState.DISCOVERED;
				if(type == BlockType.BOMB) {
					loseGame();
				}
			}
		}
		void flag() {
			if(state == BlockState.FLAGGED) {
				state = BlockState.UNDISCOVERED;
			}
			else if(state == BlockState.UNDISCOVERED) {
				state = BlockState.FLAGGED;
			}
		}
	}
}
