package org.jointheleague.discordBotExample.config;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BotList {

	@SerializedName("bots")
	@Expose
	private List<BotInfo> bots = null;

	public List<BotInfo> getBots() {
		return bots;
	}

	public void setBots(List<BotInfo> bots) {
		this.bots = bots;
	}

}