package org.jointheleague.modules.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverwatchStatisticsRequest {

	@SerializedName("endorsement")
	@Expose
	private Integer endorsement;
	@SerializedName("endorsementIcon")
	@Expose
	private String endorsementIcon;
	@SerializedName("gamesWon")
	@Expose
	private Integer gamesWon;
	@SerializedName("icon")
	@Expose
	private String icon;
	@SerializedName("level")
	@Expose
	private Integer level;
	@SerializedName("levelIcon")
	@Expose
	private String levelIcon;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("prestige")
	@Expose
	private Integer prestige;
	@SerializedName("prestigeIcon")
	@Expose
	private String prestigeIcon;
	@SerializedName("private")
	@Expose
	private Boolean _private;
	@SerializedName("rating")
	@Expose
	private Integer rating;
	@SerializedName("ratingIcon")
	@Expose
	private String ratingIcon;
	@SerializedName("ratings")
	@Expose
	private List<Rating> ratings = null;

	public Integer getEndorsement() {
	return endorsement;
	}

	public void setEndorsement(Integer endorsement) {
	this.endorsement = endorsement;
	}

	public String getEndorsementIcon() {
	return endorsementIcon;
	}

	public void setEndorsementIcon(String endorsementIcon) {
	this.endorsementIcon = endorsementIcon;
	}

	public Integer getGamesWon() {
	return gamesWon;
	}

	public void setGamesWon(Integer gamesWon) {
	this.gamesWon = gamesWon;
	}

	public String getIcon() {
	return icon;
	}

	public void setIcon(String icon) {
	this.icon = icon;
	}

	public Integer getLevel() {
	return level;
	}

	public void setLevel(Integer level) {
	this.level = level;
	}

	public String getLevelIcon() {
	return levelIcon;
	}

	public void setLevelIcon(String levelIcon) {
	this.levelIcon = levelIcon;
	}

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public Integer getPrestige() {
	return prestige;
	}

	public void setPrestige(Integer prestige) {
	this.prestige = prestige;
	}

	public String getPrestigeIcon() {
	return prestigeIcon;
	}

	public void setPrestigeIcon(String prestigeIcon) {
	this.prestigeIcon = prestigeIcon;
	}

	public Boolean getPrivate() {
	return _private;
	}

	public void setPrivate(Boolean _private) {
	this._private = _private;
	}

	public Integer getRating() {
	return rating;
	}

	public void setRating(Integer rating) {
	this.rating = rating;
	}

	public String getRatingIcon() {
	return ratingIcon;
	}

	public void setRatingIcon(String ratingIcon) {
	this.ratingIcon = ratingIcon;
	}

	public List<Rating> getRatings() {
	return ratings;
	}

	public void setRatings(List<Rating> ratings) {
	this.ratings = ratings;
	}

	}

