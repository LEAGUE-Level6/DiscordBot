package org.jointheleague.discord_bot_example.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BotInfo {

@SerializedName("channel")
@Expose
private String channel;
@SerializedName("token")
@Expose
private String token;

public String getChannel() {
return channel;
}

public void setChannel(String channel) {
this.channel = channel;
}

public String getToken() {
return token;
}

public void setToken(String token) {
this.token = token;
}

}