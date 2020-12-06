package org.jointheleague.modules.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

@SerializedName("id")
@Expose
private String id;
@SerializedName("fullName")
@Expose
private String fullName;
@SerializedName("link")
@Expose
private String link;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getFullName() {
return fullName;
}

public void setFullName(String fullName) {
this.fullName = fullName;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

}