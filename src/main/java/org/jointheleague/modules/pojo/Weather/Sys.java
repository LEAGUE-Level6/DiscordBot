package org.jointheleague.modules.pojo.Weather;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

@SerializedName("country")
@Expose
private String country;

public String getCountry() {
return country;
}

public void setCountry(String country) {
this.country = country;
}

}
