package org.jointheleague.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecastProperties {
	@SerializedName("forecast")
	@Expose
	String forecast;
}
