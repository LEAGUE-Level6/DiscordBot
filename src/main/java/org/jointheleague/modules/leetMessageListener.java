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
		String s = event.getMessageContent();
		String str = "";
				ArrayList<String> arr = new ArrayList<>();
				
				String st = s.substring(5,s.length()-1);
				event.getChannel().sendMessage("1337 letters and translations: O = 0, L = 1, Z = 2, E = 3, H = 4");
				event.getChannel().sendMessage(" S = 5, G = 6, T = 7, B = 8, and G = 9");
				for(int i =0;i<st.length()-1;i++)
				{
					arr.add(st.substring(i, i+1));
					//leet letters and translations. l=1 e=3 b = 8 s=5 7=t 4=h 2=z 6 = g 9 =g 
					if(arr.get(i).toLowerCase().equals("o"))
					{
					arr.set(i,"1");}
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
						arr.set(i, "4");	
						s.a
						}
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
					
					str+=arr.get(i);
				}
				event.getChannel().sendMessage(str);
				
			
		}
		
	}
	

}
