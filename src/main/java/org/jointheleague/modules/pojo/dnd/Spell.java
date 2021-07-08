package org.jointheleague.modules.pojo.dnd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Spell {
	@SerializedName("name")
    @Expose
    private String name;
 
 @SerializedName("desc")
    @Expose
    private String[] text;
 
 @SerializedName("higher_level")
 @Expose
 private String[] upcast;
 
 @SerializedName("components")
 @Expose
 private String[] comps;
 
 @SerializedName("material")
 @Expose
 private String material;
 
 @SerializedName("range")
 @Expose
 private String range;
 
 @SerializedName("ritual")
 @Expose
 private Boolean ritual;
 
 @SerializedName("duration")
 @Expose
 private String duration;
 
 @SerializedName("concentration")
 @Expose
 private Boolean concentration;
 
 @SerializedName("casting_time")
 @Expose
 private String tcast;
 
 @SerializedName("level")
 @Expose
 private int level;

 @SerializedName("school")
 @Expose
 private SchoolOMagic school;
 
 @SerializedName("classes")
 @Expose
 private MiniClass[] available;
 
 
 public String getName() {
 
	 return name;
 }
 public Boolean ritual() {
	 
	 return ritual;
 }
 
 public String getLine2() {
	  String s="";
	 if(level==0) {;
	  s=school.getName()+" cantrip";
	  }else if(level==1) {
	  s=level+"st-level "+school.getName(); 
	  } else if(level==2) {
	  s=level+"nd-level "+school.getName();
	  }else if(level==3) {
		  s=level+"rd-level "+school.getName();
	  }else {
		  s=level+"th-level "+school.getName();
	  }
		  
	  return s;
	 }
 public String getTime() {
	  return tcast;
	 }
 public String getRange() {
	  return range;
	 }
 public String getClasses() {
	String s="";
	for (int i = 0; i < available.length; i++) {
		if(i<(available.length-1)) {
			 s+=available[i].getName()+", ";
			}else {
				s+=available[i].getName();
			}

	}
	 return s;
	 
 }
 public String getComponents() {
	 String s="";
	
	 for (int i = 0; i < comps.length; i++) {
		if(i<(comps.length-1)) {
		 s+=comps[i]+", ";
		}else {
			s+=comps[i];
		}
	 }
	 
	 
	 if(comps[comps.length-1].contentEquals("M")) {
		 s+=" ("+material+")";
	 }
			 
	 return s;
 	 }
 public String getDuration() {
	 String s=duration;
	 if(concentration==true) {
		 s+=" (concentration)";
	 }
	 return s;
 }

 
 
 public String[] gettext() {
	 return text;
 }
 public String athigherlevels() {
	String s="";
	if(upcast !=null) {
	for (int i = 0; i < upcast.length; i++) {
	s+=upcast[i];	
	}
	}
	 return s;
 }
 
 
 
}
