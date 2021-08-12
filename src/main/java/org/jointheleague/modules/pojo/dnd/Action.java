package org.jointheleague.modules.pojo.dnd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Action {
@SerializedName("name")
    @Expose
    private String name;

 @SerializedName("desc")
    @Expose
    private String text;
 
 public String getName() {
	   return name;
 }
 
 public String getText() {
     return text;
 }
}
