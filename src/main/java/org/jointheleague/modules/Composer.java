package org.jointheleague.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Composer {
	@SerializedName("id")
	@Expose
	String id;
	
	@SerializedName("name")
	@Expose
	String name;
	
	@SerializedName("complete_name")
	@Expose
	String complete_name;
	
	@SerializedName("epoch")
	@Expose
	String epoch;
	
	public String toString() {
		String str = "id: " + id + ", name: " + name + ", complete_name: " + complete_name + ", epoch: " + epoch;
		return str;
	}
}
