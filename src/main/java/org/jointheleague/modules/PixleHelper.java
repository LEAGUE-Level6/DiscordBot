package org.jointheleague.modules;


public class PixleHelper {
	public int r;
	public int g;
	public int b;
	public boolean exists;
	
	PixleHelper(int r, int g, int b){
		this.r = r;
		this.b = b;
		this.g = g;
		exists = true;
	}
	
	PixleHelper(){
		exists = false;
	}
	
	PixleHelper(int rgb){
		r = (rgb >> 16) & 0xFF;
		g = (rgb >> 8) & 0xFF;
		b = rgb & 0xFF;
	}
	
	public int convertToInt() {
		int rgb = r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;
		return rgb;
	}
	
	
}
