package org.jointheleague.modules.pojo.dictionary;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DictionaryRequest {
	
	@SerializedName("shortdef")
	@Expose
	private List<String> shortdef = null;
}
