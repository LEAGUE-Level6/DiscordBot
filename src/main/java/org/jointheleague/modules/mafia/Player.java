package org.jointheleague.modules.mafia;

import org.javacord.api.entity.message.MessageAuthor;

public class Player {

	MessageAuthor name;
	private int accusations;
	public boolean hasAccused;
	private String accused;
	private String role;
	
	
	public Player(MessageAuthor name, String role)
	{
		this.name = name;
		this.role = role;
		
		accusations = 0;
		hasAccused = false;
		accused = "";
	}
	
	public void accuse(String s)
	{
		if(!hasAccused)
		{
			accused = s;
			hasAccused = true;
		}
	}
	
	public void removeAccusation()
	{
		if(hasAccused)
		{
			accused = "";
			hasAccused = false;
		}
	}
	
	public int getAccusations() {return accusations;}
	public String getAccused() {return accused;}
	public MessageAuthor getName() {return name;}
	public String getRole() {return role;}
	
	public void setAccusations(int i) {accusations = i;}
	
}
