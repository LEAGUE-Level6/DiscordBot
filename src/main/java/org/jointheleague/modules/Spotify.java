package org.jointheleague.modules;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.javacord.api.event.message.MessageCreateEvent;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistRequest;

import net.aksingh.owmjapis.api.APIException;

public class Spotify extends CustomMessageCreateListener {

	public Spotify(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains("!spotify")) {
			AuthorizationCodeUriExample a = new AuthorizationCodeUriExample();
			a.authorizationCodeUri_Sync();
			/*String access = "7c82b6bf155b45ebb129c671f2dab2ef";
			String id = "2HitUvk40lssir4iFwElKb";
			SpotifyApi sa = new SpotifyApi.Builder().setAccessToken(access).build();
			GetArtistRequest gar = sa.getArtist(id).build();
			try {
				final Artist artist = gar.execute();

				System.out.println("Name: " + artist.getName());
			} catch (IOException | SpotifyWebApiException e) {
				e.printStackTrace();
			}*/
			/*
			 * try { String file = ""; URL url = new URL("https://api.spotify.com");
			 * HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			 * conn.setRequestMethod("GET"); conn.connect(); int response =
			 * conn.getResponseCode(); Scanner s = new Scanner(url.openStream()); while
			 * (s.hasNext()) { file += s.nextLine(); } System.out.println(file); } catch
			 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 */
		}

	}

}

class AuthorizationCodeUriExample {
	private static final String clientId = "7c82b6bf155b45ebb129c671f2dab2ef";
	private static final String clientSecret = "e8ad6d89f55542b48f5458ac5f641131";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
			.setClientSecret(clientSecret).setRedirectUri(redirectUri).build();
	private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//	          .state("x4xkmn9pu3j6ukrs8n")
//	          .scope("user-read-birthdate,user-read-email")
//	          .show_dialog(true)
			.build();

	public static void authorizationCodeUri_Sync() {
		final URI uri = authorizationCodeUriRequest.execute();

		System.out.println("URI: " + uri.toString());
	}

	public static void authorizationCodeUri_Async() {
		try {
			final CompletableFuture<URI> uriFuture = authorizationCodeUriRequest.executeAsync();

			// Thread free to do other tasks...

			// Example Only. Never block in production code.
			final URI uri = uriFuture.join();

			System.out.println("URI: " + uri.toString());
		} catch (CompletionException e) {
			System.out.println("Error: " + e.getCause().getMessage());
		} catch (CancellationException e) {
			System.out.println("Async operation cancelled.");
		}
	}
}
