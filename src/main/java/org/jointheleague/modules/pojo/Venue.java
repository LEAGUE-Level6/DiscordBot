package org.jointheleague.modules.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venue {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("link")
@Expose
private String link;
@SerializedName("city")
@Expose
private String city;
@SerializedName("timeZone")
@Expose
private TimeZone timeZone;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public TimeZone getTimeZone() {
return timeZone;
}

public void setTimeZone(TimeZone timeZone) {
this.timeZone = timeZone;
}

}