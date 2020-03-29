package org.jointheleague.modules.spotifyExample;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchArtistsExample {
  private static final String accessToken = "BQC-059r05aa1KzgBvRWpKLgOf-oyBSM-nZZneHua5n4q9c5SgvjG00WDL2zM3yGgy-5m88czidxpMNHky8";
  private static String q = "Imagine Dragons";

  private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
          .setAccessToken(accessToken)
          .build();
  private static final SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(q)
//          .market(CountryCode.SE)
//          .limit(10)
//          .offset(0)
          .build();

  public static void setSearch(String name) {
	  q = name;
	  
  }
  public static void searchArtists_Sync() {
    try {
      final Paging<Artist> artistPaging = searchArtistsRequest.execute();

      System.out.println("Total: " + artistPaging.getTotal());
    } catch (IOException | SpotifyWebApiException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void searchArtists_Async() {
    try {
      final CompletableFuture<Paging<Artist>> pagingFuture = searchArtistsRequest.executeAsync();

      // Thread free to do other tasks...

      // Example Only. Never block in production code.
      final Paging<Artist> artistPaging = pagingFuture.join();
      System.out.println("artist id is "+artistPaging.getItems()[0].getId());
      for(Artist a : artistPaging.getItems()) {
    	  System.out.println(a.getName());
      }
      //System.out.println("This is "+ artistPaging.getItems());
      System.out.println("Popularity "+artistPaging.getItems()[0].getPopularity());
      
      for(String genre: artistPaging.getItems()[0].getGenres())
    	 System.out.println(genre); 
    	  
    	  
      System.out.println("Genres "+artistPaging.getItems()[0].getGenres());
      System.out.println("Total: " + artistPaging.getTotal());
    } catch (CompletionException e) {
      System.out.println("Error: " + e.getCause().getMessage());
    } catch (CancellationException e) {
      System.out.println("Async operation cancelled.");
    }
  }
}