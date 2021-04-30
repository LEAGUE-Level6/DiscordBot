package org.jointheleague.modules;
import java.lang.reflect.Field;
import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rate {
@SerializedName("USD")
@Expose
private double usd;
@SerializedName("EUR")
@Expose
private double eur;
@SerializedName("JPY")
@Expose
private double jpy;
@SerializedName("BGN")
@Expose
private double bgn;
@SerializedName("CZK")
@Expose
private double czk;
@SerializedName("DKK")
@Expose
private double dkk;
@SerializedName("GBP")
@Expose
private double gbp;
@SerializedName("HUF")
@Expose
private double huf;
@SerializedName("PLN")
@Expose
private double pln;
@SerializedName("RON")
@Expose
private double ron;
@SerializedName("SEK")
@Expose
private double sek;
@SerializedName("CHF")
@Expose
private double chf;
@SerializedName("ISK")
@Expose
private double isk;
@SerializedName("NOK")
@Expose
private double nok;
@SerializedName("HRK")
@Expose
private double hrk;
@SerializedName("RUB")
@Expose
private double rub;
@SerializedName("TRY")
@Expose
private double tryy;
@SerializedName("AUD")
@Expose
private double aud;
@SerializedName("BRL")
@Expose
private double brl;
@SerializedName("CAD")
@Expose
private double cad;
@SerializedName("CNY")
@Expose
private double cny;
@SerializedName("HKD")
@Expose
private double hkd;
@SerializedName("IDR")
@Expose
private double idr;
@SerializedName("ILS")
@Expose
private double ils;
@SerializedName("INR")
@Expose
private double inr;
@SerializedName("KRW")
@Expose
private double krw;
@SerializedName("MXN")
@Expose
private double mxn;
@SerializedName("MYR")
@Expose
private double myr;
@SerializedName("NZD")
@Expose
private double nzd;
@SerializedName("PHP")
@Expose
private double php;
@SerializedName("SGD")
@Expose
private double sgd;
@SerializedName("THB")
@Expose
private double thb;
@SerializedName("ZAR")
@Expose
private double zar;

HashMap<String, Double> ratios = new HashMap<String, Double>();

void initializeRatios2() throws IllegalArgumentException, IllegalAccessException {
	for(Field f : getClass().getDeclaredFields()) {
		String name = f.getName();
		Object value =  f.get(this);
		double val = Double.parseDouble(value.toString());
		ratios.put(name, val);
	}
}

double getRatio(String country) {
	return ratios.get(country);
}

void setRatio(String c, double v) {
	ratios.put(c, v);
}


}
