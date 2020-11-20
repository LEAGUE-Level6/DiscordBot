package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class OpusByComposer extends CustomMessageCreateListener{
	SearchData searchdata;

	public OpusByComposer(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}
	
	public SearchData getData(String request) {
		Gson gson = new Gson();
		try {
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String data = "";
			String line = br.readLine();
			while (line != null) {
				data += line;
				line = br.readLine();
			}
			br.close();
			
			searchdata = gson.fromJson(data, SearchData.class);
				
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return searchdata;
	}
	
	public SearchData getByComposer(String composerName) {
		String str = "https://api.openopus.org/composer/list/search/" + composerName + ".json";
		System.out.println("Working 1.0");
		return getData(str);
	}
	
	public SearchData getWorkById(String id) {
		String str = "https://api.openopus.org/work/list/composer/" + id + "/genre/all.json";
		System.out.println(str + " Working 2.0");
		return getData(str);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!opusbycomposer")) {
			String loc = event.getMessageContent().replaceAll(" ", "").replace("!opusbycomposer","");
			
			if (loc.equals("")) {
				event.getChannel().sendMessage("Please input the last name of the composer whose works you are looking for. Specifics include ");
			}
			
			else {
				searchdata = getByComposer(loc);	//repetitive
				System.out.println(searchdata.toString());
				System.out.println(searchdata.composers.length);
				if (searchdata.composers.length == 1) {
					System.out.println("Received");
					searchdata = getWorkById(searchdata.composers[0].id);
					
					ArrayList<String> completeworks = searchdata.toStringWorks();

					for (int i = 0; i < completeworks.size(); i++) {
						event.getChannel().sendMessage(completeworks.get(i));
					}
				}
				
			}
		}
	}
	
}
