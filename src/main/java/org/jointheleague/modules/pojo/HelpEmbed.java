package org.jointheleague.modules.pojo;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class HelpEmbed {
	
	private final Color color = Color.green;
	private final String title;
	private final String description;

	public HelpEmbed(String title, String description){
		this.title = title;
		this.description = description; 
	}

	public Color getColor() {
		return color;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
	
	public EmbedBuilder getEmbed() {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(this.color);
		embed.setTitle(this.title);
		embed.setDescription(this.description);
		return embed;
	}
	
	
}
