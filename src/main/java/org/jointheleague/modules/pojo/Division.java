package org.jointheleague.modules.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Division {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("nameShort")
@Expose
private String nameShort;
@SerializedName("link")
@Expose
private String link;
@SerializedName("abbreviation")
@Expose
private String abbreviation;

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

public String getNameShort() {
return nameShort;
}

public void setNameShort(String nameShort) {
this.nameShort = nameShort;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

public String getAbbreviation() {
return abbreviation;
}

public void setAbbreviation(String abbreviation) {
this.abbreviation = abbreviation;
}

}