
package org.jointheleague.modules.pojo.Pokemon.types;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PastDamageRelation {

    @SerializedName("damage_relations")
    @Expose
    private DamageRelations__1 damageRelations;
    @SerializedName("generation")
    @Expose
    private Generation__2 generation;

    public DamageRelations__1 getDamageRelations() {
        return damageRelations;
    }

    public void setDamageRelations(DamageRelations__1 damageRelations) {
        this.damageRelations = damageRelations;
    }

    public Generation__2 getGeneration() {
        return generation;
    }

    public void setGeneration(Generation__2 generation) {
        this.generation = generation;
    }

}
