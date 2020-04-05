package org.jointheleague.modules.spotifyExample;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysis;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import com.wrapper.spotify.requests.data.player.StartResumeUsersPlaybackRequest;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import com.wrapper.spotify.requests.data.tracks.GetAudioAnalysisForTrackRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchArtistsExample {
	private static String accessToken;
	private static String q;
	private static SearchArtistsRequest searchArtistsRequest;
	private static SpotifyApi spotifyApi;
	private static String id;
	private static GetArtistsAlbumsRequest getArtistsAlbumsRequest;
	private static SearchTracksRequest searchTracksRequest;
	private static GetAudioAnalysisForTrackRequest getAudioAnalysisForTrackRequest;
	private static StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest;

	public SearchArtistsExample(String artist, String token) {
		q = artist;
		accessToken = token;
		spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
		searchArtistsRequest = spotifyApi.searchArtists(q).build();
		searchTracksRequest = spotifyApi.searchTracks(q).build();
		getAudioAnalysisForTrackRequest = spotifyApi.getAudioAnalysisForTrack(q).build();
		startResumeUsersPlaybackRequest = spotifyApi.startResumeUsersPlayback().build();
		
	}

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

	public static String searchArtists_Async() {
		try {
			String result = "";

			final CompletableFuture<Paging<Artist>> pagingFuture_ARTISTS = searchArtistsRequest.executeAsync();
			final CompletableFuture<Paging<Track>> pagingFuture_TRACKS = searchTracksRequest.executeAsync();
			final CompletableFuture<AudioAnalysis> audioAnalysisFuture = getAudioAnalysisForTrackRequest.executeAsync();
			//final CompletablxeFuture<String> stringFuture = startResumeUsersPlaybackRequest.executeAsync();

			final Paging<Artist> artistPaging = pagingFuture_ARTISTS.join();
			final Paging<Track> trackPaging = pagingFuture_TRACKS.join();
			final AudioAnalysis audioAnalysis = audioAnalysisFuture.join();
			
			id = artistPaging.getItems()[0].getId();
			getArtistsAlbumsRequest = spotifyApi.getArtistsAlbums(id).build();

			//final Paging<AlbumSimplified> albumSimplifiedPaging = getArtistsAlbumsRequest.execute();

			result += "Artist: " + q + "\nSong: " + trackPaging.getItems()[0].getName();
			//System.out.println(audioAnalysis.getTrack().builder());
			

			/*
			 * System.out.println("album: " +
			 * albumSimplifiedPaging.getItems()[0].getName());
			 * System.out.println("artist id is " + id); System.out.println("track: " +
			 * trackPaging.getItems()[0].getName()); for (Artist a :
			 * artistPaging.getItems()) { System.out.println(a.getName()); }
			 * System.out.println("Popularity " +
			 * artistPaging.getItems()[0].getPopularity());
			 * 
			 * for (String genre : artistPaging.getItems()[0].getGenres())
			 * System.out.println(genre);
			 * 
			 * System.out.println("Genres " + artistPaging.getItems()[0].getGenres());
			 * System.out.println("Total: " + artistPaging.getTotal());
			 */
			return result;
		} catch (CompletionException e) {
			System.out.println("Error: " + e.getCause().getMessage());
		} catch (CancellationException e) {
			System.out.println("Async operation cancelled.");
		} /*catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return null;
	}

}