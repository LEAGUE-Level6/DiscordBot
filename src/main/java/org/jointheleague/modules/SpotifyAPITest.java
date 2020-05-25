package org.jointheleague.modules;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.requests.data.albums.GetAlbumRequest;

import net.aksingh.owmjapis.api.APIException;

import org.apache.hc.core5.http.ParseException;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SpotifyAPITest extends CustomMessageCreateListener {

	private static final String accessToken = "taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk";
	private static final String id = "23c080d3220244ba98f1d97067708ced";

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
	private static final GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(id).build();

	public SpotifyAPITest(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// For all requests an access token is needed

	}

	public static void getAlbum_Sync() {
		try {
			final Album album = getAlbumRequest.execute();

			System.out.println("Name: " + album.getName());
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error: " + e.getMessage());
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

	public static void main(String[] args) {
		getAlbum_Sync();
		getAlbum_Async();
	}
}
