package org.jointheleague.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {
	@SerializedName("type")
	@Expose
	String type;
	
	@SerializedName("item")
	@Expose
	String item;
	
	public String toString() {
		String str = "type: " + type + ", " + "item: " + item;
		return str;
	}
}
