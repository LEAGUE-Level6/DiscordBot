package org.jointheleague.modules.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Franchise {

@SerializedName("franchiseId")
@Expose
private String franchiseId;
@SerializedName("teamName")
@Expose
private String teamName;
@SerializedName("link")
@Expose
private String link;

public String getFranchiseId() {
return franchiseId;
}

public void setFranchiseId(String franchiseId) {
this.franchiseId = franchiseId;
}

public String getTeamName() {
return teamName;
}

public void setTeamName(String teamName) {
this.teamName = teamName;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

}