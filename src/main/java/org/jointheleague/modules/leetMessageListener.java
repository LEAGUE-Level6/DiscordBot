package org.jointheleague.modules;
import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

public class leetMessageListener extends CustomMessageCreateListener
	{
	
	
	public leetMessageListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!leet")) {
				ArrayList<String> arr = new ArrayList<>();
				String s = event.getMessageContent();
				String st = s.substring(4,s.length()-1);
				System.out.println("1337 letters and translations: L = 1, Z = 2, E = 3, H = 4, S = 5, G = 6, T = 7, B = 8, and G = 9");
				for(int i =0;i<st.length()-1;i++)
				{
					arr.add(st.substring(i, i+1));
					//leet letters and translations. l=1 e=3 b = 8 s=5 7=t 4=h 2=z 6 = g 9 =g 
					
					if(arr.get(i).toLowerCase().equals("l"))
					{
						arr.set(i, "1");					}
					else if(arr.get(i).toLowerCase().equals("z"))
					{
						arr.set(i, "2");					}
					else if(arr.get(i).toLowerCase().equals("e"))
					{
						arr.set(i, "3");					}
					else if(arr.get(i).toLowerCase().equals("h"))
					{
						arr.set(i, "4");					}
					else if(arr.get(i).toLowerCase().equals("s"))
					{
						arr.set(i, "5");					}
					else if(arr.get(i).toLowerCase().equals("g"))
					{
						arr.set(i, "6");
					}
					else if(arr.get(i).toLowerCase().equals("t"))
					{
						arr.set(i, "7");
					}
					else if(arr.get(i).toLowerCase().equals("b"))
					{
						arr.set(i, "8");
					}
					else if(arr.get(i).toLowerCase().equals("g"))
					{
					
						arr.set(i, "9");
					}
					else
					{
					//i'm baby
					}
					
					
				}
				for (int i = 0; i < arr.size(); i++) {
					System.out.print(arr.get(i));
				}
				
				
			
		}
		
	}
	

}
