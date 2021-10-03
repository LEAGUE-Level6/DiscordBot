
package org.jointheleague.modules.pojo.Pokemon.types;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PokemonType {

    @SerializedName("damage_relations")
    @Expose
    private DamageRelations damageRelations;
    @SerializedName("game_indices")
    @Expose
    private List<GameIndex> gameIndices = null;
    @SerializedName("generation")
    @Expose
    private Generation__1 generation;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("move_damage_class")
    @Expose
    private MoveDamageClass moveDamageClass;
    @SerializedName("moves")
    @Expose
    private List<Move> moves = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("names")
    @Expose
    private List<Name> names = null;
    @SerializedName("past_damage_relations")
    @Expose
    private List<PastDamageRelation> pastDamageRelations = null;
    @SerializedName("pokemon")
    @Expose
    private List<Pokemon> pokemon = null;

    public DamageRelations getDamageRelations() {
        return damageRelations;
    }

    public void setDamageRelations(DamageRelations damageRelations) {
        this.damageRelations = damageRelations;
    }

    public List<GameIndex> getGameIndices() {
        return gameIndices;
    }

    public void setGameIndices(List<GameIndex> gameIndices) {
        this.gameIndices = gameIndices;
    }

    public Generation__1 getGeneration() {
        return generation;
    }

    public void setGeneration(Generation__1 generation) {
        this.generation = generation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MoveDamageClass getMoveDamageClass() {
        return moveDamageClass;
    }

    public void setMoveDamageClass(MoveDamageClass moveDamageClass) {
        this.moveDamageClass = moveDamageClass;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Name> getNames() {
        return names;
    }

    public void setNames(List<Name> names) {
        this.names = names;
    }

    public List<PastDamageRelation> getPastDamageRelations() {
        return pastDamageRelations;
    }

    public void setPastDamageRelations(List<PastDamageRelation> pastDamageRelations) {
        this.pastDamageRelations = pastDamageRelations;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

}
