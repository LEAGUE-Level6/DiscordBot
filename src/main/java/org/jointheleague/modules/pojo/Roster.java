package org.jointheleague.modules.pojo;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Roster {

@SerializedName("roster")
@Expose
private List<Roster_> roster = null;
@SerializedName("link")
@Expose
private String link;

public List<Roster_> getRoster() {
return roster;
}

public void setRoster(List<Roster_> roster) {
this.roster = roster;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

}
