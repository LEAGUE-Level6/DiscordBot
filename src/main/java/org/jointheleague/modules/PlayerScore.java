package org.jointheleague.modules;

public class PlayerScore implements Comparable{
	private float score;
	private String player;
	
	public PlayerScore(float score, String player) {
		this.score = score;
		this.player = player;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof PlayerScore) {
			return (int) (((PlayerScore) o).score-score);
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return (player+","+score+";");
	}
	
}
