package org.jointheleague.modules.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

@SerializedName("level")
@Expose
private Integer level;
@SerializedName("role")
@Expose
private String role;
@SerializedName("roleIcon")
@Expose
private String roleIcon;
@SerializedName("rankIcon")
@Expose
private String rankIcon;

public Integer getLevel() {
return level;
}

public void setLevel(Integer level) {
this.level = level;
}

public String getRole() {
return role;
}

public void setRole(String role) {
this.role = role;
}

public String getRoleIcon() {
return roleIcon;
}

public void setRoleIcon(String roleIcon) {
this.roleIcon = roleIcon;
}

public String getRankIcon() {
return rankIcon;
}

public void setRankIcon(String rankIcon) {
this.rankIcon = rankIcon;
}

}
