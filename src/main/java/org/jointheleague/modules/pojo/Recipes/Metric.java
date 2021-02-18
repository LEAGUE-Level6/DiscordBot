
package org.jointheleague.modules.pojo.Recipes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metric {

    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("unitShort")
    @Expose
    private String unitShort;
    @SerializedName("unitLong")
    @Expose
    private String unitLong;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
    }

}
