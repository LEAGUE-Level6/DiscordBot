package org.jointheleague.modules;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.javacord.api.event.message.MessageCreateEvent;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import net.aksingh.owmjapis.api.APIException;

public class SpotifyAPICreds extends CustomMessageCreateListener {

	private static final String command = "!test";
	private static final String clientId = "23c080d3220244ba98f1d97067708ced";
	private static final String clientSecret = "02e970bef02c4c97a6161f59f8694875";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");
	private static final String code = "";

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
			.setClientSecret(clientSecret).setRedirectUri(redirectUri).build();
	private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

	public SpotifyAPICreds(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contentEquals(command)) {
			// TODO Auto-generated method stub
			try {
				authorizationCode_Sync();
			} catch (org.apache.hc.core5.http.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			authorizationCode_Async();
		}

	}

	public static void authorizationCode_Sync() throws org.apache.hc.core5.http.ParseException, ParseException {
		try {
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void authorizationCode_Async() {
		try {
			final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest
					.executeAsync();

			// Thread free to do other tasks...

			// Example Only. Never block in production code.
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		} catch (CompletionException e) {
			System.out.println("Error: " + e.getCause().getMessage());
		} catch (CancellationException e) {
			System.out.println("Async operation cancelled.");
		}
	}

}
