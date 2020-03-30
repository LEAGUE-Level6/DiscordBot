package org.jointheleague.modules.spotifyExample;

import com.wrapper.spotify.requests.data.search.SearchItemRequest;

public class Drive {
	public static void main(String[] args) {
		ClientCredentialsExample example = new ClientCredentialsExample();
		String token = example.clientCredentials_Sync();
		System.out.println(token);
		SearchArtistsExample artist = new SearchArtistsExample("Imagine Dragons",  token);
		artist.searchArtists_Async();
		
		
	}
}
