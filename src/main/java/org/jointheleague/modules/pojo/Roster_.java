package org.jointheleague.modules.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Roster_ {

@SerializedName("person")
@Expose
private Person person;
@SerializedName("jerseyNumber")
@Expose
private String jerseyNumber;
@SerializedName("position")
@Expose
private Position position;

public Person getPerson() {
return person;
}

public void setPerson(Person person) {
this.person = person;
}

public String getJerseyNumber() {
return jerseyNumber;
}

public void setJerseyNumber(String jerseyNumber) {
this.jerseyNumber = jerseyNumber;
}

public Position getPosition() {
return position;
}

public void setPosition(Position position) {
this.position = position;
}

}
