
package org.jointheleague.modules.pojo.Pokemon.types;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Pokemon {

    @SerializedName("pokemon")
    @Expose
    private Pokemon__1 pokemon;
    @SerializedName("slot")
    @Expose
    private Integer slot;

    public Pokemon__1 getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon__1 pokemon) {
        this.pokemon = pokemon;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

}
