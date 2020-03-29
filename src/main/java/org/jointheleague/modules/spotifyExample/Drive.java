package org.jointheleague.modules.spotifyExample;

public class Drive {
	public static void main(String[] args) {
//		ClientCredentialsExample example = new ClientCredentialsExample();
//		String token = example.clientCredentials_Sync();
//		System.out.println(token);
		
		SearchArtistsExample artist = new SearchArtistsExample();
		SearchArtistsExample.setSearch("Imagine Dragons");
		artist.searchArtists_Async();
		
		
	}
}
