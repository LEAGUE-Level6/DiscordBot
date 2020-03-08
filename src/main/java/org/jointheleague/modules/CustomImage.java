package org.jointheleague.modules;


public class CustomImage {
	PixleHelper[][] pixles;
	
	CustomImage(PixleHelper[] pixles, int width){
		this.pixles = new PixleHelper[pixles.length/width][width];
		for(int i = 0; i < pixles.length; i++) {
			this.pixles[(int) Math.floor(i * 1.0 / width)][i % width] = pixles[i];
		}
	}
	
	public PixleHelper getPixleAt(int x, int y) {
		try {
			return pixles[x][y];
		}catch(IndexOutOfBoundsException e) {
			return new PixleHelper();
		}
	}
	
	public int getHeight() {
		return pixles.length;
	}
	public int getWidth() {
		return pixles[0].length;
	}
	
}
