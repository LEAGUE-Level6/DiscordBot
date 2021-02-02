
package org.jointheleague.modules.pojo.imageSearchAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type {

    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("pretty_slug")
    @Expose
    private String prettySlug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPrettySlug() {
        return prettySlug;
    }

    public void setPrettySlug(String prettySlug) {
        this.prettySlug = prettySlug;
    }

}
