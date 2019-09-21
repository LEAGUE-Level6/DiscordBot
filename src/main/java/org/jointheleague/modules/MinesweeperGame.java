package org.jointheleague.modules;

public class MinesweeperGame {
	public static enum BlockType {
		ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,EMPTY,BOMB;
	}
	public static enum BlockState {
		DISCOVERED,UNDISCOVERED,FLAGGED
	}
	
	private int dimensions;
	private int mineCount;
	
	MinesweeperGame(int dimensions, int mineCount) {
		
		if(dimensions > 10 || mineCount > dimensions*dimensions) {
			this.dimensions = 10;
			this.mineCount = 25;
		}
		
		this.dimensions = dimensions;
		this.mineCount = mineCount;
		generateMap();
	}
	
	void generateMap() {
		
	}
	
	public void flagCell(int xPos, int yPos) {
		
	}
	public void interactCell(int xPos, int yPos) {
		
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
		BlockType type;
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
