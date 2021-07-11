
package org.jointheleague.modules.pojo.Pokemon;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PokemonWrapper {

  //  @SerializedName("abilities")
   // @Expose
  //  private List<Ability> abilities = null;
    @SerializedName("base_experience")
    @Expose
    private Integer baseExperience;
   // @SerializedName("forms")
   // @Expose
  //  private List<Form> forms = null;
    @SerializedName("game_indices")
    @Expose
    private List<Object> gameIndices = null;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("held_items")
    @Expose
    private List<Object> heldItems = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_default")
    @Expose
    private Boolean isDefault;
    @SerializedName("location_area_encounters")
    @Expose
    private String locationAreaEncounters;
    @SerializedName("moves")
    @Expose
    private List<Object> moves = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("past_types")
    @Expose
    private List<Object> pastTypes = null;
   // @SerializedName("species")
   // @Expose
   // private Species species;
   // @SerializedName("sprites")
  //  @Expose
  //  private Sprites sprites;
   // @SerializedName("stats")
   // @Expose
   // private List<Stat> stats = null;
   // @SerializedName("types")
   // @Expose
    //private List<Type> types = null;
    @SerializedName("weight")
    @Expose
    private Integer weight;

   // public List<Ability> getAbilities() {
   //     return abilities;
   // }

    //public void setAbilities(List<Ability> abilities) {
   //     this.abilities = abilities;
  //  }

    public Integer getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }

   // public List<Form> getForms() {
   //     return forms;
   // }

   // public void setForms(List<Form> forms) {
    //    this.forms = forms;
   // }

    public List<Object> getGameIndices() {
        return gameIndices;
    }

    public void setGameIndices(List<Object> gameIndices) {
        this.gameIndices = gameIndices;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<Object> getHeldItems() {
        return heldItems;
    }

    public void setHeldItems(List<Object> heldItems) {
        this.heldItems = heldItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getLocationAreaEncounters() {
        return locationAreaEncounters;
    }

    public void setLocationAreaEncounters(String locationAreaEncounters) {
        this.locationAreaEncounters = locationAreaEncounters;
    }

    public List<Object> getMoves() {
        return moves;
    }

    public void setMoves(List<Object> moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<Object> getPastTypes() {
        return pastTypes;
    }

    public void setPastTypes(List<Object> pastTypes) {
        this.pastTypes = pastTypes;
    }

    //public Species getSpecies() {
   //     return species;
   // }

   // public void setSpecies(Species species) {
   //     this.species = species;
   // }

    //public Sprites getSprites() {
   //     return sprites;
   // }

   // public void setSprites(Sprites sprites) {
   //     this.sprites = sprites;
   // }

    //public List<Stat> getStats() {
   //     return stats;
   // }

   // public void setStats(List<Stat> stats) {
   //     this.stats = stats;
   // }

   // public List<Type> getTypes() {
    //    return types;
   // }

    //public void setTypes(List<Type> types) {
    //    this.types = types;
    //}

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
