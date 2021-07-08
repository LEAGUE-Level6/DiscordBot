package org.jointheleague.modules.pojo.dnd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feature {
	 @SerializedName("name")
	    @Expose
	    private String name;
	 @SerializedName("class")
	    @Expose
	    private MiniClass parentclass;
	 @SerializedName("level")
	    @Expose
	    private int level;
	 @SerializedName("desc")
	    @Expose
	    private String[] text;
	  
	   public String getTitle() {
		   return(name);
	   }
	 
	   
	   public String getParent() {
	        return parentclass.getName();
	    }
	   
	   public String[] getText() {
	        return text;
	    }
	   public int getLevel() {
	        return level;
	    }
	
	   }
	   

