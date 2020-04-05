package org.jointheleague.modules.spotifyExample;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.CustomMessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class SpotifyExampleListener extends CustomMessageCreateListener {

	public SpotifyExampleListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith("!spotify")) {
			//event.getChannel().sendMessage("connected");
			String a = event.getMessageContent().replaceFirst(" ", "").replace("!spotify","");
			ClientCredentialsExample example = new ClientCredentialsExample();
			String token = example.clientCredentials_Sync();
			System.out.println(token);
			SearchArtistsExample artist = new SearchArtistsExample(a, token);
			event.getChannel().sendMessage(artist.searchArtists_Async());
		}
	}

}
