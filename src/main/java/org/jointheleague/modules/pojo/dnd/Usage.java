package org.jointheleague.modules.pojo.dnd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Usage {
	

	
@SerializedName("type")
@Expose
private String type;

@SerializedName("min_value")
@Expose
private int minimum;

@SerializedName("times")
@Expose
private int times;

@SerializedName("rest_types")
@Expose
private String[] types;

		 public String getUsage() {
			 String d="";
			 if(type.equals("recharge on roll")) {
				 System.out.println("roll works");
				 if(minimum==6) {
					 d=" (recharge 6)";
				 }else {
					 d=" (recharge "+minimum+"-6)";
				 }
			 }else if (type.equals("per day")) {
				 d=" ("+times+"/day)";
				 System.out.println("/day works");
			
			 }else if (type.equals("recharge after rest")) {
				 if(types.length==1) {
					 d=" (1/long rest)"; 
				 }else {
					 d=" (1/long or short rest)";
				 }
			
				 System.out.println("/day works");

			 } 
				 
			 
			 return d;
		 }

}
