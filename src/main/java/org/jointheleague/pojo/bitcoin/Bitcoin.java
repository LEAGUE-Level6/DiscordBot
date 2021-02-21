
package org.jointheleague.pojo.bitcoin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bitcoin {

    @SerializedName("time")
    @Expose
    private Time time;
    @SerializedName("disclaimer")
    @Expose
    private String disclaimer;
    @SerializedName("chartName")
    @Expose
    private String chartName;
    @SerializedName("bpi")
    @Expose
    private Bpi bpi;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public Bpi getBpi() {
        return bpi;
    }

    public void setBpi(Bpi bpi) {
        this.bpi = bpi;
    }

}
