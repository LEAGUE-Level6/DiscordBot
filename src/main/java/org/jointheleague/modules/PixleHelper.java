package org.jointheleague.modules;


public class PixleHelper {
	public int r;
	public int g;
	public int b;
	
	PixleHelper(int r, int g, int b){
		this.r = r;
		this.b = b;
		this.g = g;
	}
	
	PixleHelper(int rgb){
		r = (rgb >> 16) & 0xFF;
		g = (rgb >> 8) & 0xFF;
		b = rgb & 0xFF;
	}
	
	
}
