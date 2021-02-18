
package org.jointheleague.modules.pojo.Recipes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measures {

    @SerializedName("us")
    @Expose
    private Us us;
    @SerializedName("metric")
    @Expose
    private Metric metric;

    public Us getUs() {
        return us;
    }

    public void setUs(Us us) {
        this.us = us;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

}
