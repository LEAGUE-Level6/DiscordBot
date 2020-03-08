package org.jointheleague.modules;


public class CustomImage {
	PixleHelper[][] pixles;
	
	CustomImage(PixleHelper[] pixles, int width){
		for(int i = 0; i < pixles.length; i++) {
			this.pixles[(int) Math.floor(i * 1.0 / width)][i % width] = pixles[i];
		}
	}
	
	public PixleHelper getPixleAt(int x, int y) {
		return pixles[x][y];
	}
}
