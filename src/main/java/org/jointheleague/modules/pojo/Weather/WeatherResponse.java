package org.jointheleague.modules.pojo.Weather;



import java.util.List;

import org.jointheleague.modules.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
	@SerializedName("weather")
	@Expose
	private List<org.jointheleague.modules.pojo.Weather.Weather> weather = null;
	@SerializedName("main")
	@Expose
	private Main main;
	@SerializedName("wind")
	@Expose
	private Wind wind;
	@SerializedName("sys")
	@Expose
	private Sys sys;
	@SerializedName("name")
	@Expose
	private String name;

	public List<org.jointheleague.modules.pojo.Weather.Weather> getWeather() {
		return weather;
	}

	public void setWeather(List<org.jointheleague.modules.pojo.Weather.Weather> weather) {
		this.weather = weather;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
