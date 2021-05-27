package org.jointheleague.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecastData {
	@SerializedName("properties")
	@Expose
	WeatherForecastProperties properties;
}
