package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class _SammySamSam extends CustomMessageCreateListener {
	
	public static final String COMMAND = "~add zoom";
	
	public _SammySamSam(String channelName) {
		super(channelName);
	}
	
	public String getUserInfo(String UserInfo) {
		return "src/main/java/FilePackage/" + UserInfo + ".txt";
	}
	String fondou = getUserInfo("GrandPa");
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
if (event.getMessageContent().contains(COMMAND)) {
	//"~add zoom <name> <link>
			String cmd = event.getMessageContent().substring(10); 
			System.out.println(cmd);
			String subject = cmd;
			String link = cmd; 
			subject = subject.substring(0, subject.indexOf(" "));
			link = link.substring(link.indexOf(" ")+1, link.length());
			
		
			
			try {
				FileWriter fw = new FileWriter(fondou, true);
				String classex = subject + ":" + "\n" + "Link: " + link;
				classex = classex.trim();
				classex += "\n";
				fw.write(classex);
				fw.close();
				System.out.println(classex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
}

try {
	BufferedReader br;
	br = new BufferedReader(new FileReader(fondou));
	String line = br.readLine(); 
	String candy = "";
	while (line!=null) {
		candy += line + "\n"; 
		}
	br.close();
	event.getChannel().sendMessage(candy);
	System.out.println(candy);
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


	}
}
