package org.jointheleague.modules;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchData {
	@SerializedName("request")
	@Expose
	Request request;
	
	@SerializedName("status")
	@Expose
	Status status;
	
	@SerializedName("composers")
	@Expose
	Composer[] composers;
	
	@SerializedName("composer")
	@Expose
	Composer composer;
	
	@SerializedName("works")
	@Expose
	Works[] works;
	
	public String toString() {
		String str = request.toString() + "\n" + status.toString() + "\n";
		if (composers != null) {
			for (int i = 0; i < composers.length; i++) {
				str += composers[i].toString() + "\n";
			}
		}
		
		if (composer != null) {
			str += composer.toString() + "\n";
		}
		
		if (works != null) {
			for (int i = 0; i < works.length; i++) {
				if (str.length() + works[i].toString().length() <= 2000) {
					str += works[i].toString() + "\n";
				}
			}
		}
		System.out.println(str.length());
		return str;
	}
	
	public ArrayList<String> toStringWorks() {
		ArrayList<String> completeworks = new ArrayList<String>();
		
		String str = "";
		for (int i = 0; i < works.length; i++) {
			if (str.length() + works[i].toString().length() <= 2000) {
				str += works[i].toString() + "\n";
			}
			else {
				completeworks.add(str);
				str = works[i].toString() + "\n";
			}
		}
		return completeworks;
	}
}
