package org.jointheleague.modules.pojo.dictionary;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DictionaryWrapper {

	@SerializedName("shortdef")
	@Expose
	private List<String> shortdef = null;

	public List<String> getShortdef() {
		return shortdef;
	}

	public void setShortdef(List<String> shortdef) {
		this.shortdef = shortdef;
	}
}
