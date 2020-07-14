package org.jointheleague.modules.pojo.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

	@SerializedName("temp")
	@Expose
	private Double temp;
	@SerializedName("feels_like")
	@Expose
	private Double feelsLike;
	@SerializedName("temp_min")
	@Expose
	private Double tempMin;
	@SerializedName("temp_max")
	@Expose
	private Double tempMax;
	@SerializedName("humidity")
	@Expose
	private Integer humidity;
	
	public Double getTemp() {
		return temp;
	}

	public void setTemp(Double temp) {
		this.temp = temp;
	}

	public Double getFeelsLike() {
		return feelsLike;
	}

	public void setFeelsLike(Double feelsLike) {
		this.feelsLike = feelsLike;
	}

	public Double getTempMin() {
		return tempMin;
	}

	public void setTempMin(Double tempMin) {
		this.tempMin = tempMin;
	}

	public Double getTempMax() {
		return tempMax;
	}

	public void setTempMax(Double tempMax) {
		this.tempMax = tempMax;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

}
