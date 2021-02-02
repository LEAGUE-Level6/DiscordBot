
package org.jointheleague.modules.pojo.imageSearchAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ancestry {

    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("category")
    @Expose
    private Category category;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
