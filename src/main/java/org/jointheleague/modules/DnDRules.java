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
import org.jointheleague.modules.pojo.dnd.Monster;
import org.jointheleague.modules.pojo.dnd.Spell;

import com.google.gson.Gson;



//NOTE TO SELF REDO TOKEN ENTRY


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
					}else if(msg.contains("monster")) {
						String lol=msg.replace("monster", "");
						getMonster(lol, event);
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
			public void getMonster(String msg, MessageCreateEvent event) {

				String requestURL = "https://www.dnd5eapi.co/api/monsters/"+msg;
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
				Monster m= gson.fromJson(userJSON.toString(), Monster.class);

		
					event.getChannel().sendMessage(m.getName());
					event.getChannel().sendMessage(m.getTypes());
					event.getChannel().sendMessage("**----------------**");
					event.getChannel().sendMessage(m.getAC());
					event.getChannel().sendMessage(m.getHP());
					event.getChannel().sendMessage(m.getSpeed());
					event.getChannel().sendMessage("**----------------**");
					event.getChannel().sendMessage(m.getScores());
					event.getChannel().sendMessage("**----------------**");
					if(m.getSaves().length()>=0) {
						event.getChannel().sendMessage(m.getSaves());
					}
					if(m.getProfs().length()>=0) {
						event.getChannel().sendMessage(m.getProfs());
					}
					if(m.getProfs().length()>=0) {
						event.getChannel().sendMessage(m.getProfs());
					}
					if(m.getV().length()>=0) {
						event.getChannel().sendMessage(m.getV());
					}
					if(m.getR().length()>=0) {
						event.getChannel().sendMessage(m.getR());
					}
					if(m.getI().length()>=0) {
						event.getChannel().sendMessage(m.getI());
					}
					if(m.getCI().length()>=0) {
						event.getChannel().sendMessage(m.getCI());
					}
					event.getChannel().sendMessage(m.getSenses());
					event.getChannel().sendMessage(m.getLangs());
					event.getChannel().sendMessage(m.getCR());
					event.getChannel().sendMessage("**----------------**");
					event.getChannel().sendMessage(m.getAbilities());
					event.getChannel().sendMessage("**Actions**");
					event.getChannel().sendMessage("----------------");
					System.out.println(m.getActions());
					event.getChannel().sendMessage(m.getActions());
					if(m.getLActions().length() >=0) {
						event.getChannel().sendMessage("**Legendary Actions**");
						event.getChannel().sendMessage("----------------");
						event.getChannel().sendMessage("The "+m.getSmallName()+" can take 3 legendary actions, choosing from the options below. Only one legendary action option can be used at a time and only at the end of another creature's turn. The sphinx regains spent legendary actions at the start of its turn.");
						event.getChannel().sendMessage(m.getLActions());
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
			
}
