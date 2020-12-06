package org.jointheleague.modules.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeZone {

@SerializedName("id")
@Expose
private String id;
@SerializedName("offset")
@Expose
private String offset;
@SerializedName("tz")
@Expose
private String tz;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getOffset() {
return offset;
}

public void setOffset(String offset) {
this.offset = offset;
}

public String getTz() {
return tz;
}

public void setTz(String tz) {
this.tz = tz;
}

}