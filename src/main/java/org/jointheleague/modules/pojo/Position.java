package org.jointheleague.modules.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Position {

@SerializedName("code")
@Expose
private String code;
@SerializedName("name")
@Expose
private String name;
@SerializedName("type")
@Expose
private String type;
@SerializedName("abbreviation")
@Expose
private String abbreviation;

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getAbbreviation() {
return abbreviation;
}

public void setAbbreviation(String abbreviation) {
this.abbreviation = abbreviation;
}

}
