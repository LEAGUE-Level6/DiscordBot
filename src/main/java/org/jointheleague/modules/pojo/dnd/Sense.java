package org.jointheleague.modules.pojo.dnd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sense {
	 @SerializedName("blindsight")
	    @Expose
	    private String bs;
	 @SerializedName("darkvision")
	    @Expose
	    private String dv;
	 @SerializedName("tremorsense")
	    @Expose
	    private String tremor;
	 @SerializedName("truesight")
	    @Expose
	    private String ts;
	 @SerializedName("passive_perception")
	    @Expose
	    private int pp;
	 
	 public String getSenses() {
		 String s="**Senses** ";
		 if(bs != null) {
			 s+="Blindsight "+bs+", ";
		 }
		 if(dv != null) {
			 s+="Darkvision "+dv+", ";
		 }
		 if(tremor != null) {
			 s+="Tremorsense "+tremor+", ";
		 }
		 if(ts != null) {
			 s+="Truesight "+ts+", ";
		 }
		 
		 s+="passive Perception "+pp;
		 
		 return s;
	 }
}
