package org.jointheleague.modules.mafia;

import java.util.ArrayList;
import java.util.Random;

public class RandomText {
	
	ArrayList<String> cod;
	
	public RandomText()
	{
		ArrayList<String> cod = new ArrayList<String>();
		cod.add("suffocated.");
		cod.add("thrown into a well.");
		cod.add("stabbed to death.");
		cod.add("thrown off of a building.");
	}
	
	public String murderText(Player p)
	{
		String ret = "Everyone wakes up the next morning. To your horror, you find that " + p.getName().getDisplayName() + " has been ";
		ret = ret + cod.get((new Random()).nextInt(cod.size()));
		return ret + " Everyone warily gathers in the town hall to discuss who to blame.";
		
	}
	
	
}
