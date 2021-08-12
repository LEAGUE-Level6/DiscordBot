package org.jointheleague.modules.pojo.dnd;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Monster {
	 @SerializedName("name")
	    @Expose
	    private String name;
	 @SerializedName("size")
	    @Expose
	    private String size;
	 @SerializedName("type")
	    @Expose
	    private String type;
	 @SerializedName("subtype")
	    @Expose
	    private String subtype;
	 @SerializedName("alignment")
	    @Expose
	    private String alignment;
	 @SerializedName("armor_class")
	    @Expose
	    private int ac;
	 @SerializedName("hit_points")
	    @Expose
	    private int hp;
	 @SerializedName("hit_dice")
	    @Expose
	    private String hd;
	 @SerializedName("speed")
	    @Expose
	    private Speed spd;
	 @SerializedName("strength")
	    @Expose
	    private int str;
	 @SerializedName("dexterity")
	    @Expose
	    private int dex;
	 @SerializedName("constitution")
	    @Expose
	    private int con;
	 @SerializedName("intelligence")
	    @Expose
	    private int intel;
	 @SerializedName("wisdom")
	    @Expose
	    private int wis;
	 @SerializedName("charisma")
	    @Expose
	    private int cha;
	 @SerializedName("proficiency")
	    @Expose
	    private Proficiency[] proficiencies;
	 @SerializedName("damage_vulnerabilities")
	    @Expose
	    private String[] dv;
	 @SerializedName("damage_immunities")
	@Expose
	private String[] di;
	@SerializedName("damage_resistances")
	    @Expose
	    private String[] dr;
	@SerializedName("condition_immunities")
    @Expose
    private MiniClass[] ci;
	 @SerializedName("senses")
	    @Expose
	    private Sense senses;
	 @SerializedName("languages")
	    @Expose
	    private String langs;
	 @SerializedName("challenge_rating")
	    @Expose
	    private int cr;
	 @SerializedName("xp")
	    @Expose
	    private int xp;
	 @SerializedName("special_abilities")
	    @Expose
	    private Action[] abilities;
	 @SerializedName("actions")
	    @Expose
	    private Action[] actions;
	 @SerializedName("legendary_actions")
	    @Expose
	    private Action[] lactions;
	 
	 public String getName() {
		 return "***"+name.toUpperCase()+"***";
	 }
	 public String getSmallName() {
		 return name;
	 }
	 
	 public String getTypes() {
		 String s="*"+size+" "+type;
		 if(subtype !=null) {
			 s+=" ("+subtype+")"+", ";
		 }else {
			 s+=", ";
		 }
		 s+=alignment+"*";
		 
		 return s;
	 }
	 public String getAC() {
		
		 return "**Armor Class** "+ac;
	 }
	 public String getHP() {
			
		 return "**Hit Points** "+hp+" ("+hd+")";
	 }
	 public String getSpeed() {
			
		 return "**Speed** "+spd.getSpeeds();
	 }
	 public String getScores() {
		String s="";	
		int mstr=((str-10)/2)-(((str-10)/2)%1);
		if(mstr>=0) {
			s+="**str** "+str+" (+"+mstr+") ";
		}else {
			s+="**str** "+str+" ("+mstr+") ";
		}
		int mdex=((dex-10)/2)-(((dex-10)/2)%1);
		if(mdex>=0) {
			s+="**dex** "+dex+" (+"+mdex+") ";
		}else {
			s+="**dex** "+dex+" ("+mdex+") ";
		}
		int mcon=((con-10)/2)-(((con-10)/2)%1);
		if(mcon>=0) {
			s+="**con** "+con+" (+"+mcon+") ";
		}else {
			s+="**con** "+con+" ("+mcon+") ";
		}
		int mintel=((intel-10)/2)-(((intel-10)/2)%1);
		if(mintel>=0) {
			s+="**int** "+intel+" (+"+mintel+") ";
		}else {
			s+="**int** "+intel+" ("+mintel+") ";
		}
		int mwis=((wis-10)/2)-(((wis-10)/2)%1);
		if(mwis>=0) {
			s+="**wis** "+wis+" (+"+mwis+") ";
		}else {
			s+="**wis** "+wis+" ("+mwis+") ";
		}
		int mcha=((cha-10)/2)-(((cha-10)/2)%1);
		if(mcha>=0) {
			s+="**cha** "+cha+" (+"+mcha+") ";
		}else {
			s+="**cha** "+cha+" ("+mcha+") ";
		}
		
		return s;
	 }
	 
	 public String getSaves() {
			
			String s="";
			if(proficiencies !=null) {
			for (int i = 0; i < proficiencies.length; i++) {
			 if(proficiencies[i].getStyle()=="saving throw") {
				 if(s.length()==0) {
					 s+="**Saving Throws** "+proficiencies[i].getProf();
				 }else {
					 s+=", "+proficiencies[i].getProf();
				 }
			 }
			}	
			}
			 return s;
		 }
	 
	 public String getProfs() {
		
		String s="";
		if(proficiencies !=null) {
		for (int i = 0; i < proficiencies.length; i++) {
		 if(proficiencies[i].getStyle()=="skill") {
			 if(s.length()==0) {
				 s+="**Skills** "+proficiencies[i].getProf();
			 }else {
				 s+=", "+proficiencies[i].getProf();
			 }
		 }
		}
		}
		 return s;
	 }
	 
	 public String getV() {
			
			String s="";
		 if(dv.length!=0) {
			 s="**Damage Vulnerabilities** ";
			for (int i = 0; i < dv.length; i++) {
				s+=", "+dv[i];
			}
		 }
			
			return s;
		 }
	 public String getR() {
			
			String s="";
		 if(dr.length!=0) {
			 s="**Damage Resistances** ";
			for (int i = 0; i < dr.length; i++) {
				s+=", "+dr[i];
			}
		 }
			return s;
		 }
	 public String getCI() {
			
			String s="";
		 if(ci.length!=0) {
			 s="**Condition Immunities** ";
			for (int i = 0; i < ci.length; i++) {
				s+=", "+ci[i].getName();
			}
		 }
			return s;
		 }
	 public String getI () {
			
			String s="";
		 if(di.length!=0) {
			 s="**Damage Immunities** ";
			for (int i = 0; i < di.length; i++) {
				s+=", "+di[i];
			}
		 }
			return s;
		 }
	 public String getSenses() {
			return senses.getSenses();
		 }
	 public String getLangs() {
		 return "**Languages** "+langs;
	 }
	 
	 public String getCR() {
		 return "**Challenge** "+cr+" ("+xp+" XP)";
	 }

	 public String getAbilities() {
		 String s="";
		 for (int i = 0; i < abilities.length; i++) {
			s+="***"+abilities[i].getName()+".***";
			s+=" "+abilities[i].getText()+"\n";
		}
		 return s;
	 }
	 
	 
	 
	 public String getActions() {
		 String s="";
		 for (int i = 0; i < actions.length; i++) {
			s+="***"+actions[i].getName()+".***";
			s+=" "+actions[i].getText()+"\n";
		}
		 return s;
	 }
	 
	 public String getLActions() {
		 String s="";
		 for (int i = 0; i < lactions.length; i++) {
			s+="***"+lactions[i].getName()+".***";
			s+=" "+lactions[i].getText()+"\n";
		}
		 return s;
	 }

}
