package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class CovidCaseGetter extends CustomMessageCreateListener {

	private static final String COMMAND = "!covidcases";
	
	public CovidCaseGetter(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Responds with live number of COVID-19 positive cases from the United States. Uses information & data from Johns Hopkins University Center for Systems Science and Engineering(JHU CSSE). Supported by Johns Hopkins University Applied Physics Lab. Data is updated every 10 minutes.");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String args = event.getMessageContent().replaceAll(" ", "").replace("!covidcases","");

			if(args.equals("")) {
			String result = gatherMostRecentData();
			//event.getChannel().sendMessage(result);
			//System.out.println(result);
			
			
			int startInt = result.indexOf("positive");
			int negativeInt = result.indexOf("negative");
			
			String output = result.substring(startInt, negativeInt);
			
			String answer = "";
			for (int i = 0; i < output.length(); i++) {
				if(output.charAt(i) <= '9' && output.charAt(i) >= '0') {
				answer += output.charAt(i);

				}
			}
			event.getChannel().sendMessage("USA Positive Cases: " + answer);
			
			

			//String positiveCases = result.getKey("positive");
			
			} else {
				String result = gatherSpecificDate(args);
				//event.getChannel().sendMessage(result);
				//System.out.println(result);
				
				
				int startInt = result.indexOf("positive");
				int negativeInt = result.indexOf("negative");
				
				String output = result.substring(startInt, negativeInt);
				
				String answer = "";
				for (int i = 0; i < output.length(); i++) {
					if(output.charAt(i) <= '9' && output.charAt(i) >= '0') {
					answer += output.charAt(i);

					}
				}
				event.getChannel().sendMessage("USA Positive Cases for " + args + ": " +  answer);
			}
			
		}
	}
	public String gatherMostRecentData() {
		try {
		URL url = new URL("https://api.covidtracking.com/v1/us/current.json");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		 BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();

	        //System.out.println(response.toString());
	        return response.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "s";
	}
	
	public String gatherSpecificDate(String date) {
		try {
		URL url = new URL("https://api.covidtracking.com/v1/us/daily.json");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		 BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        
	        for (int i = 0; i < response.length(); i++) {
				//System.out.println(response.charAt(i));
			}

	        //System.out.println(response.toString());
	        return response.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "s";
	}
}
