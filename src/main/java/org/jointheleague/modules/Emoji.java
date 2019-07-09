
package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Emoji {

    @SerializedName("emoji")
    @Expose
    private String emoji;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("aliases")
    @Expose
    private List<String> aliases = null;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("unicode_version")
    @Expose
    private String unicodeVersion;
    @SerializedName("ios_version")
    @Expose
    private String iosVersion;

    public Emoji(JsonObject o) {
    	emoji = o.get("emoji").getAsString();
    	description = o.get("description").getAsString();
    	category = o.get("category").getAsString();
    	aliases = convertToList(o.get("aliases").getAsJsonArray());
    	tags = convertToList(o.get("tags").getAsJsonArray());
    	unicodeVersion = o.get("unicode_version").getAsString();
    	iosVersion = o.get("ios_version").getAsString();
    }
    
    public List<String> convertToList(JsonArray a) {
    	ArrayList<String> list = new ArrayList<>();
    	for (int i = 0; i < a.size(); i++) {
    		list.add(a.get(i).getAsString());
    	}
    	
    	return list;
    }
    
    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUnicodeVersion() {
        return unicodeVersion;
    }

    public void setUnicodeVersion(String unicodeVersion) {
        this.unicodeVersion = unicodeVersion;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

}
