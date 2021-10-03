
package org.jointheleague.modules.pojo.Pokemon.types;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Name {

    @SerializedName("language")
    @Expose
    private Language language;
    @SerializedName("name")
    @Expose
    private String name;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
