package org.jointheleague.pojo.age;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Age {

@SerializedName("name")
@Expose
private String name;
@SerializedName("age")
@Expose
private Integer age;
@SerializedName("count")
@Expose
private Integer count;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Integer getAge() {
return age;
}

public void setAge(Integer age) {
this.age = age;
}

public Integer getCount() {
return count;
}

public void setCount(Integer count) {
this.count = count;
}

}