package org.jointheleague.modules.pojo.dictionary;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("uuid")
	@Expose
	private String uuid;
	@SerializedName("sort")
	@Expose
	private String sort;
	@SerializedName("src")
	@Expose
	private String src;
	@SerializedName("section")
	@Expose
	private String section;
	@SerializedName("stems")
	@Expose
	private List<String> stems = null;
	@SerializedName("offensive")
	@Expose
	private Boolean offensive;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public List<String> getStems() {
		return stems;
	}

	public void setStems(List<String> stems) {
		this.stems = stems;
	}

	public Boolean getOffensive() {
		return offensive;
	}

	public void setOffensive(Boolean offensive) {
		this.offensive = offensive;
	}
}
