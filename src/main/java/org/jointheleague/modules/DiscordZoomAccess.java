package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class DiscordZoomAccess extends CustomMessageCreateListener {
	
	public static final String COMMAND = "$add zoom";
	public static final String CLEARCOMMAND = "$clear";
	public static final String SHOW = "$show";  
	
	public DiscordZoomAccess(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed("You Need a List, You got a list", COMMAND + "\n" + "Activates in the form: " + COMMAND + " Subject Link Passcode" +  
		"\nPurpose: adds your zoom subject, link, and passcode to an individual list in which you, yes you, can call and access from discord\n\n" + CLEARCOMMAND + "\nActivates in the form: " + CLEARCOMMAND + "\nPurpose: Clears your list you made above\n\n" +
		SHOW + "\nActivates in the form: " + SHOW + "\nPurpose: Shows you your list\n\n" + "Now Remeber Kids: Gaby is God");
	}
	
	public String getUserInfo(String UserInfo) {
		return "src/main/java/FilePackage/" + UserInfo + ".txt";
	}
	
	String fondou; 
	FileWriter fw;
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		MessageAuthor user = event.getMessageAuthor();
		String w = user.getName();
		fondou = getUserInfo(w);
if (event.getMessageContent().contains(COMMAND)) {
	//"~add zoom <name> <link>
			String cmd = event.getMessageContent().substring(10); 
			//System.out.println(cmd);
			String subject = cmd;
			String link = cmd;
			String passcode = cmd;
			subject = subject.substring(0, subject.indexOf(" "));
			link = link.substring(link.indexOf(" ")+1, link.length());
			
		
			
			try {
				fw = new FileWriter(fondou, true);
				String classex = subject + ":" + "\n" + "Link + Passcode: " + link;
				classex = classex.trim();
				classex += "\n";
				fw.write(classex);
				fw.close();
				//System.out.println(classex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}	
			try {
				BufferedReader br;
				br = new BufferedReader(new FileReader(fondou));
				String line = br.readLine(); 
				String candy = "";
				while (line!=null) {
					candy += line + "\n"; 
					line = br.readLine();
					}
				br.close();
				if (candy == "") {
					event.getChannel().sendMessage("🍓🎉  𝓝๏ 𝕝𝕚şт  ♘♩");
				}
				else {
					event.getChannel().sendMessage(candy);
				}
				System.out.println("Candy: " +candy);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
}
if (event.getMessageContent().contains(CLEARCOMMAND)) {
	try {
		fw = new FileWriter(fondou);
		event.getChannel().sendMessage("ꪻꫝꫀ ꪶ꠸ᦓꪻ ᭙ꪖᦓ ᥴꪶꫀꪖ᥅ꫀᦔ");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
if (event.getMessageContent().contains(SHOW)) {
	try {
		BufferedReader br;
		br = new BufferedReader(new FileReader(fondou));
		String line = br.readLine(); 
		String candy = "";
		while (line!=null) {
			candy += line + "\n"; 
			line = br.readLine();
			}
		br.close();
		if (candy == "") {
			event.getChannel().sendMessage("♘♩ 𝓝๏ 𝕝𝕚şт  ♘♩");
		}
		else {
			event.getChannel().sendMessage(candy);
		}
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	}
}
	}
}
