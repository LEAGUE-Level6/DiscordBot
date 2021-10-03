
package org.jointheleague.modules.pojo.Pokemon.types;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GameIndex {

    @SerializedName("game_index")
    @Expose
    private Integer gameIndex;
    @SerializedName("generation")
    @Expose
    private Generation generation;

    public Integer getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(Integer gameIndex) {
        this.gameIndex = gameIndex;
    }

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

}
