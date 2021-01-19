package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;
import org.jointheleague.modules.pojo.apiExample.Article;
import org.jointheleague.modules.pojo.movieRecs.com.example.Genre;
import org.jointheleague.modules.pojo.movieRecs.com.example.Movie;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class FilmRecommendations extends CustomMessageCreateListener {

	private final String apiKey = "fa92f432901a636644b602c85e08eb4a";
	private static final String COMMAND = "!moviesAPI";
	private final Gson gson = new Gson();
	
	public FilmRecommendations(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service");
	}
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().contains(COMMAND)) {

			//remove the command so we are only left with the search term
			String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

			if (msg.equals("")) {
				event.getChannel().sendMessage("Please put a word after the command");
			} else {
				String definition = getMovie(msg);
				event.getChannel().sendMessage(definition);
			}
			
		}
	}
	public String getMovie(String topic) {
		
		//create the request URL (can be found in the documentation)
//		String requestURL = "http://newsapi.org/v2/everything?" +
//		          "q="+topic+"&" +
//		          "sortBy=popularity&" +
//		          "apiKey="+apiKey;
		
		String requestURL = "https://api.themoviedb.org/3/movie/550?api_key=fa92f432901a636644b602c85e08eb4a";
		 
		try {
			
			//the following code will probably be the same for your feature
			URL url = new URL(requestURL);
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
		    Movie movie = gson.fromJson(userJSON.toString(), Movie.class);
		    

			//get the genre of movie (these are just java objects now)
			List<Genre> genreList = movie.getGenres();
			for (int i = 0; i < genreList.size(); i++) {
				Genre g = genreList.get(i);
				g.getName();
			}
			
			//get the title of the movie 
			String title = movie.getOriginalTitle();
			
			//get the overview of the movie 
			String overview = movie.getOverview();
			
			//create the message
			String message = "'" + title + "' is a " + genreList + ". Here is the overview: " + overview;
			
			//send the message 
			return message;
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "No information found for: " + topic;
	}
	
}