package org.jointheleague.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {
	@SerializedName("version")
	@Expose
	String version;
	
	@SerializedName("success")
	@Expose
	String success;
	
	@SerializedName("source")
	@Expose
	String source;
	
	@SerializedName("rows")
	@Expose
	int rows;
	
	@SerializedName("processingtime")
	@Expose
	float processingtime;
	
	@SerializedName("api")
	@Expose
	String api;
	
	public String toString() {
		String str = "version: " + version + ", success: " + success + ", rows: " + rows + ", processingtime: " + processingtime + ", api: " + api;
		return str;
	}
}
