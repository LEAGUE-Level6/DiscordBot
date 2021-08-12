package org.jointheleague.modules.pojo.dnd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Proficiency {
	 @SerializedName("proficiency")
	    @Expose
	    private MiniProf p;
	 @SerializedName("value")
	    @Expose
	    private int v;
	 
	 public String getProf() {
		 if(v>=0) {
			 return p.getName()+" +"+v;
		 }else {
			 return p.getName()+" "+v;
		 }
	 }
	 
	 public String getStyle() {
		 return p.getType();
	 }
	 
}
