package org.jointheleague.modules;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Conversions {
	@SerializedName("base")
	@Expose
	private String base;
	@SerializedName("date")
	@Expose
	private String date;
	@SerializedName("rates")
	@Expose
	private Rate rates;
	
	Rate getRates() {
		return rates;
	}
	void setRates(Rate rate) {
		this.rates = rate;
	}
}
