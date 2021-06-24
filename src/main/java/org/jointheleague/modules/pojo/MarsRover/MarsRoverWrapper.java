
package org.jointheleague.modules.pojo.MarsRover;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MarsRoverWrapper {

    @SerializedName("rover")
    @Expose
    private ManifestRover rover;
    
    public ManifestRover getRover() {
        return rover;
    }

    public void setRover(ManifestRover rover) {
        this.rover = rover;
    }

}
