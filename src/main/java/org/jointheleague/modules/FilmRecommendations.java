package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;
import org.jointheleague.modules.pojo.apiExample.Article;
import org.jointheleague.modules.pojo.movieRecs.com.example.Movie2;
import org.jointheleague.modules.pojo.movieRecs.com.example.Result;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class FilmRecommendations extends CustomMessageCreateListener {

	private final String apiKey = "fa92f432901a636644b602c85e08eb4a";
	private static final String COMMAND = "!moviesAPI";
	private final Gson gson = new Gson();
	HashMap<Integer, String> genreMap = new HashMap<Integer, String>();
	
	public FilmRecommendations(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Please type '!moviesAPI' and the name of a movie you want information for.");
		genreMap.put(28, "Action");
		genreMap.put(12, "Adventure");
		genreMap.put(16, "Animation");
		genreMap.put(35, "Comedy");
		genreMap.put(80, "Crime");
		genreMap.put(99, "Documentary");
		genreMap.put(18, "Drama");
		genreMap.put(10751, "Family");
		genreMap.put(14, "Fantasy");
		genreMap.put(36, "History");
		genreMap.put(27, "Horror");
		genreMap.put(10402, "Music");
		genreMap.put(9648, "Mystery");
		genreMap.put(10749, "Romance");
		genreMap.put(878, "Science Fiction");
		genreMap.put(10770, "TV Movie");
		genreMap.put(53, "Thriller");
		genreMap.put(10752, "War");
		genreMap.put(37, "Western");
	}
	
	
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
		if(event.getMessageContent().contains(COMMAND)) {
			//remove the command so we are only left with the search term
			//String msg = event.getMessageContent();

			String [] msg = event.getMessageContent().split(" ");
			if (msg[0].equals("!moviesAPI")) {
				//event.getChannel().sendMessage("Please give me a movie");
				String userOutput = "";
				
				for(int i = 1; i < msg.length; i++) {
					userOutput += msg[i];
					
					if(i < msg.length-1) {
						userOutput += "%20";
					}
				}
				
				System.out.println(userOutput);
				
				
				String movieInfo = getMovie(userOutput);
				System.out.println(movieInfo);
				event.getChannel().sendMessage(movieInfo);
			
				//event.getChannel().sendMessage("Please put a word after the command");
			} 
			else {
				event.getChannel().sendMessage("Please enter a movie.");
				String userOutput = "";
				for(int i = 1; i < msg.length; i++) {
					userOutput += msg[i];
					
					if(i < msg.length-1) {
						userOutput += "%20";
					}
				}
			}
		}
	}
	public String getMovie(String movieName) {
		
		//create the request URL (can be found in the documentation)
//		String requestURL = "http://newsapi.org/v2/everything?" +
//		          "q="+topic+"&" +
//		          "sortBy=popularity&" +
//		          "apiKey="+apiKey;
		
		//String requestURL = "https://api.themoviedb.org/3/movie/550?api_key=fa92f432901a636644b602c85e08eb4a";
		String requestURL = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&language=en-US&query=" + movieName + "&page=1&include_adult=false";
		 
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
		    Movie2 movie = gson.fromJson(userJSON.toString(), Movie2.class);
		    
		    String getTitleStr = "";
		    String getOverviewStr = "";
		    List<Integer> getGenreStr = null;
		    Double getVoteNum = null;
		    List<Result> resultList = movie.getResults();
		    for(int i = 0; i < resultList.size(); i++) {
		    	Result r = resultList.get(i);
		    	getTitleStr = r.getTitle();
		    	getOverviewStr = r.getOverview();
		    	getGenreStr = r.getGenreIds();
		    	getVoteNum = r.getVoteAverage();
		    }

			//get the genre of movie (these are just java objects now)
//		    String getGenreName = "";
//		    List<Genre> genreList = movie.getGenres();
//			for (int i = 0; i < genreList.size(); i++) {
//				Genre g = genreList.get(i);
//				getGenreName = g.getName();
//			}
//			
//			//get the title of the movie 
//			String title = movie.getTitle();
//			
//			//get the overview of the movie 
//			String overview = movie.getOverview();
			
			//create the message
			String message = "'" + getTitleStr + "' is in the genre " + genreMap.get(getGenreStr.get(0)) + ". "
					+ "Here is an overview of the movie: " + getOverviewStr + " " + " This movie got an average of " + getVoteNum + " votes.";
			
			//send the message 
			return message;
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//fix catch statement
		return "No information found for: " + movieName;
	}
	
}