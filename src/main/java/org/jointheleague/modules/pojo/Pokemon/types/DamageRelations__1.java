
package org.jointheleague.modules.pojo.Pokemon.types;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DamageRelations__1 {

    @SerializedName("double_damage_from")
    @Expose
    private List<DoubleDamageFrom__1> doubleDamageFrom = null;
    @SerializedName("double_damage_to")
    @Expose
    private List<DoubleDamageTo__1> doubleDamageTo = null;
    @SerializedName("half_damage_from")
    @Expose
    private List<HalfDamageFrom__1> halfDamageFrom = null;
    @SerializedName("half_damage_to")
    @Expose
    private List<HalfDamageTo__1> halfDamageTo = null;
    @SerializedName("no_damage_from")
    @Expose
    private List<Object> noDamageFrom = null;
    @SerializedName("no_damage_to")
    @Expose
    private List<Object> noDamageTo = null;

    public List<DoubleDamageFrom__1> getDoubleDamageFrom() {
        return doubleDamageFrom;
    }

    public void setDoubleDamageFrom(List<DoubleDamageFrom__1> doubleDamageFrom) {
        this.doubleDamageFrom = doubleDamageFrom;
    }

    public List<DoubleDamageTo__1> getDoubleDamageTo() {
        return doubleDamageTo;
    }

    public void setDoubleDamageTo(List<DoubleDamageTo__1> doubleDamageTo) {
        this.doubleDamageTo = doubleDamageTo;
    }

    public List<HalfDamageFrom__1> getHalfDamageFrom() {
        return halfDamageFrom;
    }

    public void setHalfDamageFrom(List<HalfDamageFrom__1> halfDamageFrom) {
        this.halfDamageFrom = halfDamageFrom;
    }

    public List<HalfDamageTo__1> getHalfDamageTo() {
        return halfDamageTo;
    }

    public void setHalfDamageTo(List<HalfDamageTo__1> halfDamageTo) {
        this.halfDamageTo = halfDamageTo;
    }

    public List<Object> getNoDamageFrom() {
        return noDamageFrom;
    }

    public void setNoDamageFrom(List<Object> noDamageFrom) {
        this.noDamageFrom = noDamageFrom;
    }

    public List<Object> getNoDamageTo() {
        return noDamageTo;
    }

    public void setNoDamageTo(List<Object> noDamageTo) {
        this.noDamageTo = noDamageTo;
    }

}
