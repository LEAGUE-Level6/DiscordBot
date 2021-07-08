package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.dnd.Feature;
import org.jointheleague.modules.pojo.dnd.Spell;

import com.google.gson.Gson;

public class DnDRules extends CustomMessageCreateListener{
	public DnDRules(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Add a category (\"spell\", \"monster\", or \"feature\") and then the specific object. Example: !dndspellacid arrow");

	}

			private static final String COMMAND = "!dnd";
			
			private final Gson gson = new Gson();
			

			@Override
			public void handle(MessageCreateEvent event) {
				if(event.getMessageContent().contains(COMMAND)) {

					//remove the command so we are only left with the search term
					String msg = event.getMessageContent().replaceAll(" ", "-").replace(COMMAND, "");
					
					if(msg.contains("feature")) {
						String lol=msg.replace("feature", "");
						getFeature(lol, event);
					}else if(msg.contains("spell")) {
						String lol=msg.replace("spell", "");
						getSpell(lol, event);
					}
				}
			}
			
			public void getFeature(String msg, MessageCreateEvent event) {

				String requestURL = "https://www.dnd5eapi.co/api/features/"+msg;
				System.out.println(requestURL);
				
					URL url;
					try {
						url = new URL(requestURL);
					
						HttpURLConnection con = (HttpURLConnection) url.openConnection();
						con.setRequestMethod("GET");
						JsonReader repoReader = Json.createReader(con.getInputStream());
					    JsonObject userJSON = ((JsonObject) repoReader.read());
					    con.disconnect();

					//turn the json response into a java object
					//you will need to create a java class that represents the response in org.jointheleague.modules.pojo
					//you can use a tools like Postman and jsonschema2pojo.com to help with that
				    
				    //you can use postman to make the request and receive a response, then take that and put it right into jsonschema2pojo.com
					//If using jsonschema2pojo.com, select Target Langue = java, Source Type = JSON, Annotation Style = Gson


					//get the first article (these are just java objects now)
					Feature feat= gson.fromJson(userJSON.toString(), Feature.class);

					
					//get the title of the article 
					String name = feat.getTitle();
					
					//get the content of the article 
					String cclass = feat.getParent();
					int level =feat.getLevel();
					String[] desc=feat.getText();
					//create the message
					
					event.getChannel().sendMessage("**"+name+"**");
					event.getChannel().sendMessage("*level "+level+" "+cclass+" class feature*");
				for (int i = 0; i < desc.length; i++) {
					event.getChannel().sendMessage(desc[i]);

				}

					
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			
				
			}
			
			public void getSpell(String msg, MessageCreateEvent event) {

				String requestURL = "https://www.dnd5eapi.co/api/spells/"+msg;
				System.out.println(requestURL);
				
					URL url;
					try {
						url = new URL(requestURL);
					
						HttpURLConnection con = (HttpURLConnection) url.openConnection();
						con.setRequestMethod("GET");
						JsonReader repoReader = Json.createReader(con.getInputStream());
					    JsonObject userJSON = ((JsonObject) repoReader.read());
					    con.disconnect();

					//turn the json response into a java object
					//you will need to create a java class that represents the response in org.jointheleague.modules.pojo
					//you can use a tools like Postman and jsonschema2pojo.com to help with that
				    
				    //you can use postman to make the request and receive a response, then take that and put it right into jsonschema2pojo.com
					//If using jsonschema2pojo.com, select Target Langue = java, Source Type = JSON, Annotation Style = Gson


					//get the first article (these are just java objects now)
					Spell spell= gson.fromJson(userJSON.toString(), Spell.class);

					
					//get the title of the article 
					String name = spell.getName();
					
					//get the content of the article 
					String l2 = spell.getLine2();
					String duration=spell.getDuration();
					String[] desc=spell.gettext();
					String range=spell.getRange();
					String tcast=spell.getTime();
					String components=spell.getComponents();
					String upcast=spell.athigherlevels();
					Boolean ritual=spell.ritual();
					String classes=spell.getClasses();
					//create the message
					if(ritual==false) {
						event.getChannel().sendMessage("**"+name+"**");	
					}else {
						event.getChannel().sendMessage("**"+name+"**"+" (ritual)");	
					}
					event.getChannel().sendMessage("*"+l2+"*");
					event.getChannel().sendMessage("**casting time:** "+tcast);
					event.getChannel().sendMessage("**range:** "+range);
					event.getChannel().sendMessage("**components:** "+components);
					event.getChannel().sendMessage("**duration:** "+duration);
				for (int i = 0; i < desc.length; i++) {
					event.getChannel().sendMessage(desc[i]);
				}
				if(upcast.length()>0) {
					event.getChannel().sendMessage("**at higher levels:** "+upcast);
				}
				event.getChannel().sendMessage("Available to: "+classes);
					
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			
				
			}
			
}
