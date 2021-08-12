package org.jointheleague.modules.pojo.dnd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Speed {
		 @SerializedName("walk")
		    @Expose
		    private String walk;
		 @SerializedName("burrow")
		    @Expose
		    private String b;
		 @SerializedName("climb")
		    @Expose
		    private String c;
		 @SerializedName("fly")
		    @Expose
		    private String f;
		 @SerializedName("swim")
		    @Expose
		    private String sw;
		 public String getSpeeds() {
		 String s="";
			 s+=walk;
		 if(b != null) {
			 s+=", burrow "+b;
		 }
		 if(c != null) {
			 s+=", climb "+c;
		 }
		 if(f != null) {
			 s+=", fly "+f;
		 }
		 if(sw != null) {
			 s+=", swim "+sw;
		 }
		 
		 return s;
}
}
