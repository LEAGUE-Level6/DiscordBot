package org.jointheleague.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Works {
	@SerializedName("title")
	@Expose
	String title;
	
	@SerializedName("genre")
	@Expose
	String genre;
	
	public String toString() {
		String str = "title: " + title + ", genre: " + genre;
		return str;
	}
}
