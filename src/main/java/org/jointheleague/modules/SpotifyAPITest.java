package org.jointheleague.modules;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.javacord.api.event.message.MessageCreateEvent;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.requests.data.albums.GetAlbumRequest;

import net.aksingh.owmjapis.api.APIException;

public class SpotifyAPITest extends CustomMessageCreateListener {

	private static final String accessToken = "";
	private static final String id = " 5zT1JLIj9E57p3e1rFm9Uq";

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
	private static final GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(id).build();

	private static final String spotifyCmnd = "!spotify";

	public SpotifyAPITest(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event){
		
		// TODO Auto-generated method stub
		if (event.getMessageContent().equals(spotifyCmnd)) {
			event.getChannel().sendMessage("Enter a album name");
			getAlbum_Sync();
			getAlbum_Async();
		
		}

	}

	public static void getAlbum_Sync() {
		try {
			final CompletableFuture<Album> albumFuture = getAlbumRequest.executeAsync();

			// Thread free to do other tasks...
		
			// Example Only. Never block in production code.
			final Album album = albumFuture.join();
			
			System.out.println("Name: " + album.getName());
		} catch (CompletionException e) {
			System.out.println("Error: " + e.getCause().getMessage());
		} catch (CancellationException e) {
			System.out.println("Async operation cancelled.");
		}
	}

	public static void getAlbum_Async() {
		try {
			final CompletableFuture<Album> albumFuture = getAlbumRequest.executeAsync();

			// Thread free to do other tasks...

			// Example Only. Never block in production code.
			final Album album = albumFuture.join();

			System.out.println("Name: " + album.getName());
		} catch (CompletionException e) {
			System.out.println("Error: " + e.getCause().getMessage());
		} catch (CancellationException e) {
			System.out.println("Async operation cancelled.");
		}

	}
}
