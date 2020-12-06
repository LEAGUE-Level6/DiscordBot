package org.jointheleague.modules.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

@SerializedName("copyright")
@Expose
private String copyright;
@SerializedName("teams")
@Expose
private List<Team> teams = null;

public String getCopyright() {
return copyright;
}

public void setCopyright(String copyright) {
this.copyright = copyright;
}

public List<Team> getTeams() {
return teams;
}

public void setTeams(List<Team> teams) {
this.teams = teams;
}

}