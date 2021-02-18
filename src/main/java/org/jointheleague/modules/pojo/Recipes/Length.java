
package org.jointheleague.modules.pojo.Recipes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Length {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("unit")
    @Expose
    private String unit;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
