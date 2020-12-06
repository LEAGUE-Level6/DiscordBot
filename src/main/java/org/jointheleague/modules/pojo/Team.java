package org.jointheleague.modules.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("link")
@Expose
private String link;
@SerializedName("venue")
@Expose
private Venue venue;
@SerializedName("abbreviation")
@Expose
private String abbreviation;
@SerializedName("teamName")
@Expose
private String teamName;
@SerializedName("locationName")
@Expose
private String locationName;
@SerializedName("firstYearOfPlay")
@Expose
private String firstYearOfPlay;
@SerializedName("division")
@Expose
private Division division;
@SerializedName("conference")
@Expose
private Conference conference;
@SerializedName("franchise")
@Expose
private Franchise franchise;
@SerializedName("roster")
@Expose
private Roster roster;
@SerializedName("shortName")
@Expose
private String shortName;
@SerializedName("officialSiteUrl")
@Expose
private String officialSiteUrl;
@SerializedName("franchiseId")
@Expose
private String franchiseId;
@SerializedName("active")
@Expose
private String active;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

public Venue getVenue() {
return venue;
}

public void setVenue(Venue venue) {
this.venue = venue;
}

public String getAbbreviation() {
return abbreviation;
}

public void setAbbreviation(String abbreviation) {
this.abbreviation = abbreviation;
}

public String getTeamName() {
return teamName;
}

public void setTeamName(String teamName) {
this.teamName = teamName;
}

public String getLocationName() {
return locationName;
}

public void setLocationName(String locationName) {
this.locationName = locationName;
}

public String getFirstYearOfPlay() {
return firstYearOfPlay;
}

public void setFirstYearOfPlay(String firstYearOfPlay) {
this.firstYearOfPlay = firstYearOfPlay;
}

public Division getDivision() {
return division;
}

public void setDivision(Division division) {
this.division = division;
}

public Conference getConference() {
return conference;
}

public void setConference(Conference conference) {
this.conference = conference;
}

public Franchise getFranchise() {
return franchise;
}

public void setFranchise(Franchise franchise) {
this.franchise = franchise;
}

public Roster getRoster() {
return roster;
}

public void setRoster(Roster roster) {
this.roster = roster;
}

public String getShortName() {
return shortName;
}

public void setShortName(String shortName) {
this.shortName = shortName;
}

public String getOfficialSiteUrl() {
return officialSiteUrl;
}

public void setOfficialSiteUrl(String officialSiteUrl) {
this.officialSiteUrl = officialSiteUrl;
}

public String getFranchiseId() {
return franchiseId;
}

public void setFranchiseId(String franchiseId) {
this.franchiseId = franchiseId;
}

public String getActive() {
return active;
}

public void setActive(String active) {
this.active = active;
}

}