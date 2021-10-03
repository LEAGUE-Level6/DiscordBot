
package org.jointheleague.modules.pojo.Pokemon.types;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DamageRelations {

    @SerializedName("double_damage_from")
    @Expose
    private List<DoubleDamageFrom> doubleDamageFrom = null;
    @SerializedName("double_damage_to")
    @Expose
    private List<DoubleDamageTo> doubleDamageTo = null;
    @SerializedName("half_damage_from")
    @Expose
    private List<HalfDamageFrom> halfDamageFrom = null;
    @SerializedName("half_damage_to")
    @Expose
    private List<HalfDamageTo> halfDamageTo = null;
    @SerializedName("no_damage_from")
    @Expose
    private List<Object> noDamageFrom = null;
    @SerializedName("no_damage_to")
    @Expose
    private List<NoDamageTo> noDamageTo = null;

    public List<DoubleDamageFrom> getDoubleDamageFrom() {
        return doubleDamageFrom;
    }

    public void setDoubleDamageFrom(List<DoubleDamageFrom> doubleDamageFrom) {
        this.doubleDamageFrom = doubleDamageFrom;
    }

    public List<DoubleDamageTo> getDoubleDamageTo() {
        return doubleDamageTo;
    }

    public void setDoubleDamageTo(List<DoubleDamageTo> doubleDamageTo) {
        this.doubleDamageTo = doubleDamageTo;
    }

    public List<HalfDamageFrom> getHalfDamageFrom() {
        return halfDamageFrom;
    }

    public void setHalfDamageFrom(List<HalfDamageFrom> halfDamageFrom) {
        this.halfDamageFrom = halfDamageFrom;
    }

    public List<HalfDamageTo> getHalfDamageTo() {
        return halfDamageTo;
    }

    public void setHalfDamageTo(List<HalfDamageTo> halfDamageTo) {
        this.halfDamageTo = halfDamageTo;
    }

    public List<Object> getNoDamageFrom() {
        return noDamageFrom;
    }

    public void setNoDamageFrom(List<Object> noDamageFrom) {
        this.noDamageFrom = noDamageFrom;
    }

    public List<NoDamageTo> getNoDamageTo() {
        return noDamageTo;
    }

    public void setNoDamageTo(List<NoDamageTo> noDamageTo) {
        this.noDamageTo = noDamageTo;
    }

}
