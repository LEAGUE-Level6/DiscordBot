package org.jointheleague.modules.pojo.dictionary;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

	@SerializedName("syns")
	@Expose
	private List<List<String>> syns = null;

	public List<List<String>> getSyns() {
		return syns;
	}

	public void setSyns(List<List<String>> syns) {
		this.syns = syns;
	}
}
