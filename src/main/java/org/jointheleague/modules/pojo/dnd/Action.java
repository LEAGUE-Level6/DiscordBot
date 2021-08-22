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
 @SerializedName("usage")
 @Expose
 private Usage usage;
 
 public String getName() {
	 String s=name;
	 if(usage!=null) {

	s+=usage.getUsage();
		 
	 }else {
	System.out.println("no usages for "+name);
	 }
	 return s;
 }
 
 public String getText() {
     return text;
 }
}
