
package org.jointheleague.pojo.bitcoin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EUR {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rate_float")
    @Expose
    private Double rateFloat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(Double rateFloat) {
        this.rateFloat = rateFloat;
    }

}
