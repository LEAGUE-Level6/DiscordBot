package org.jointheleague.modules.pojo.PredictGender;


	import javax.annotation.Generated;
	import com.google.gson.annotations.Expose;
	import com.google.gson.annotations.SerializedName;

	@Generated("jsonschema2pojo")
	public class PredictedGender {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("gender")
	@Expose
	private String gender;
	@SerializedName("probability")
	@Expose
	private Double probability;
	@SerializedName("count")
	@Expose
	private Integer count;

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public String getGender() {
	return gender;
	}

	public void setGender(String gender) {
	this.gender = gender;
	}

	public Double getProbability() {
	return probability;
	}

	public void setProbability(Double probability) {
	this.probability = probability;
	}

	public Integer getCount() {
	return count;
	}

	public void setCount(Integer count) {
	this.count = count;
	}

	}

