package org.jointheleague.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecastPeriods {
	@SerializedName("name")
	@Expose
	String name;
	@SerializedName("isDaytime")
	@Expose
	boolean isDaytime;
	@SerializedName("temperature")
	@Expose
	int temperature;
	@SerializedName("windSpeed")
	@Expose
	String windSpeed;
	@SerializedName("windDirection")
	@Expose
	String windDirection;
	@SerializedName("shortForecast")
	@Expose
	String shortForecast;
	@SerializedName("detailedForecast")
	@Expose
	String detailedForecast;
	
	public String toString() {
		String isDaytimes = "";
		if(isDaytime = true) {
				isDaytimes = "it is not night right now!";
		} else {
			isDaytimes = "it is night right now";
		}
		String str = name + "\n" + isDaytimes + "\n" + temperature + "\n" + windSpeed + "\n" + windDirection + "\n" + shortForecast + "\n" + detailedForecast;
		return str;
	}
}