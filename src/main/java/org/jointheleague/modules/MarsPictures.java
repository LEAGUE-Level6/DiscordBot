package org.jointheleague.modules;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.MarsImage.MarsImageWrapper;
import org.jointheleague.modules.pojo.MarsRover.ManifestRover;
import org.jointheleague.modules.pojo.MarsRover.MarsRoverWrapper;

import com.google.gson.Gson;

public class MarsPictures extends CustomMessageCreateListener{

			private final String apiKey = "aY1trRHtbf5TdONEoOJcngI3SCjgeZbAPRPf7ccx";
			private static final String COMMAND = "!marsImage";
			private final Gson gson = new Gson();
		  
			public MarsPictures(String channelName) {
				super(channelName);
				helpEmbed = new HelpEmbed(COMMAND, "This command will return an image taken by a mars rover (either Curiosity, Opportunity, or Spirit). "
						+ "This command will require you to input the rover you want to get pictures from and the martian sol (day) that you want to get images from. "
						+ "You are also optionally able to input a specific camera to get images from. The parameters should be in the order <rover> <sol> <camera abbreviation>. "
						+ "Use \"!marsImage cameras\" to see what cameras are supported by what rovers. Use \"!marsImage manifest <rover>\" to see the mission manifest "
						+ "of the rover.");
			}

			@Override
			public void handle(MessageCreateEvent event) {
				if(!event.getMessageAuthor().isBotUser() && event.getMessageContent().startsWith(COMMAND)) {
					//remove the command so we are only left with the search term
					String msg = event.getMessageContent().replace(COMMAND + " ", "");
					String[] parameterList = msg.split(" ");
					
					for (int i = 0; i < parameterList.length; i++) {
						System.out.println(parameterList[i]);
					} 
					
					if(parameterList[0].equalsIgnoreCase(COMMAND) || parameterList[0].equalsIgnoreCase("help")) {
						event.getChannel().sendMessage(helpEmbed.getDescription());
					} else if(parameterList[0].equalsIgnoreCase("cameras")) {
						event.getChannel().sendMessage("```"
								+ "Abbreviation: | Camera:                                 | Rovers:\n"
								+ "FHAZ          | Front Hazard Avoidance Camera           | Curiosity, Opportunity, Spirit\n"
								+ "RHAZ          | Rear Hazard Avoidance Camera            | Curiosity, Opportunity, Spirit\n"
								+ "MAST          | Mast Camera                             | Curiosity\n"
								+ "CHEMCAM       | Chemistry and Camera Complex            | Curiosity\n"
								+ "MAHLI         | Mars Hand Lens Imager                   | Curiosity\n"
								+ "MARDI         | Mars descent Imager                     | Curiosity\n"
								+ "NAVCAM        | Navigation Camera                       | Curiosity, Opportunity, Spirit\n"
								+ "PANCAM        | Panoramic Camera                        | Opportunity, Spirit\n"
								+ "MINITES       | Miniature Thermal Emission Spectrometer | Opportunity, Spirit\n"
								+ "```");
					} else if(parameterList[0].equalsIgnoreCase("manifest") && parameterList.length > 1){
						event.getChannel().sendMessage(getMarsRoverManifest(parameterList[1]));
					} else {
						if (parameterList.length < 2) {
							event.getChannel().sendMessage("You didn't enter enough parameters. Use the \"help\" parameter for more information.");
						} else if (parameterList.length == 2){
							event.getChannel().sendMessage(getMarsImageURL(parameterList[0], parameterList[1]));
						} else {
							event.getChannel().sendMessage(getMarsImageURL(parameterList[0], parameterList[2], parameterList[1]));
						}
					}
				}
			}
			
			public String getMarsImageURL(String rover, String sol) {
				return getMarsImageURL(rover, "", sol);
			}
			
			public String getMarsImageURL(String rover, String camera, String sol) {
				
				String requestURL;
				
				if(camera.equals("")) {
					requestURL = "https://api.nasa.gov/mars-photos/api/v1/rovers/" + rover.toLowerCase() + "/photos?" + "sol=" + sol + "&api_key=" + apiKey;
				}else {
					requestURL = "https://api.nasa.gov/mars-photos/api/v1/rovers/" + rover.toLowerCase() + "/photos?" + "sol=" + sol + "&camera=" + camera.toUpperCase() + "&api_key=" + apiKey;
				}
				
				try {
					URL url = new URL(requestURL);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					JsonReader repoReader = Json.createReader(con.getInputStream());
				    JsonObject userJSON = ((JsonObject) repoReader.read());
				    con.disconnect();
				    
				    MarsImageWrapper marsImageWrapper = gson.fromJson(userJSON.toString(), MarsImageWrapper.class);
				    
				    return marsImageWrapper.getPhotos().get(0).getImgSrc();
				    
				} catch (Exception e) {
					return "A parameter was invalid or unavailable.";
				}
			}
			
			public String getMarsRoverManifest(String rover) {
				String requestURL =      "https://api.nasa.gov/mars-photos/api/v1/rovers/" + rover.toLowerCase() + "/?api_key=" + apiKey;
				
				try {
					URL url = new URL(requestURL);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					JsonReader repoReader = Json.createReader(con.getInputStream());
				    JsonObject userJSON = ((JsonObject) repoReader.read());
				    con.disconnect();
				    
				    MarsRoverWrapper manifestRover = gson.fromJson(userJSON.toString(), MarsRoverWrapper .class);
				    ManifestRover manifest = manifestRover.getRover();
				    
				    return "Name: " + manifest.getName() + ", Status: " + manifest.getStatus() + ", Launch Date: " + 
				    				  manifest.getLaunchDate() + ", Landing Date: " + manifest.getLandingDate() + ", Max Sol: " + manifest.getMaxSol();
				    
				} catch (Exception e) {
					return "A parameter was invalid.";
				}
			}
}
