package org.jointheleague.modules.pojo.dnd;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class MiniProf {
	 @SerializedName("name")
	    @Expose
	    private String name;
	 
	 public String getName() {
		 String s=name;
		 s = s.replace("Skill: ", "");
		 s = s.replace("Saving Throw: ", "");
		 return s;
	 }
	 public String getType() {
		 if(name.contains("Skill:")) {
			return "skill"; 
		 }else if(name.contains("Saving Throw:")) {
				return "saving throw"; 
		 }
		 return null;
	 }
}
