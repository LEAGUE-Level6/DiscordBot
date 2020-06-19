package org.jointheleague.modules.pojo.dictionary;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThesaurusWrapper {

	@SerializedName("meta")
	@Expose
	private Meta meta = null;

	public List<List<String>> getSyns() {
		return meta.getSyns();
	}
}
