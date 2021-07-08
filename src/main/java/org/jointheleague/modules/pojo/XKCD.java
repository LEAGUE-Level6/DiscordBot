package org.jointheleague.modules.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XKCD {
	 @SerializedName("month")
	    @Expose
	    private String month;
	 @SerializedName("num")
	    @Expose
	    private String num;
	 @SerializedName("link")
	    @Expose
	    private String link;
	 @SerializedName("year")
	    @Expose
	    private String year;
	 @SerializedName("news")
	    @Expose
	    private String news;
	 @SerializedName("safe_title")
	    @Expose
	    private String safe_title;
	 @SerializedName("transcript")
	    @Expose
	    private String transcript;
	 @SerializedName("alt")
	    @Expose
	    private String mouseovert;
	 @SerializedName("img")
	    @Expose
	    private String img;
	 @SerializedName("title")
	    @Expose
	    private String title;
	 @SerializedName("day")
	    @Expose
	    private String day;
	 
	   public String getTitle() {
	        return title;
	    }
	   
	   public String getImage() {
	        return img;
	    }
	   public String getAlt() {
	        return mouseovert;
	    }
	   public String getDate() {
		   return year+"-"+month+"-"+day;
		   //official ISO way to write a date
	   }
	   

}
