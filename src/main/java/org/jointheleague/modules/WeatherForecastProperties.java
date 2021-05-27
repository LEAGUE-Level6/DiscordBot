package org.jointheleague.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecastProperties {
	@SerializedName("periods")
	@Expose
	WeatherForecastPeriods[] periods;
	@SerializedName("forecast")
	@Expose
	String forecast;
}
