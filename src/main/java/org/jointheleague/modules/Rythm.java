package org.jointheleague.modules;

import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.aksingh.owmjapis.api.APIException;

public class Rythm extends CustomMessageCreateListener {

	ServerVoiceChannel channel;
	private static final String COMMAND = "!play";
	DiscordApi api;
	AudioPlayerManager playerManager;
	AudioPlayer player;
	static AudioConnection audioConnection;

	public Rythm(String channelName, DiscordApi api) {
		super(channelName);
		this.api = api;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		playerManager = new DefaultAudioPlayerManager();
		playerManager.registerSourceManager(new YoutubeAudioSourceManager());
		player = playerManager.createPlayer();

		// Create an audio source and add it to the audio connection's queue
		LavaplayerAudioSource lpas = new LavaplayerAudioSource(api, player);
		AudioSource source = new LavaplayerAudioSource(lpas.getApi(), player);
		audioConnection.setAudioSource(source);

		// You can now use the AudioPlayer like you would normally do with Lavaplayer,
		// e.g.,
		if (event.getMessageContent().contains(COMMAND)) {
			channel = event.getMessageAuthor().getConnectedVoiceChannel().get();
			channel.connect().thenAccept(audioConnection -> {
				// Do stuff
				event.getChannel().sendMessage("Ello");
				bigMethod(event);
			}).exceptionally(e -> {
				// Failed to connect to voice channel (no permissions?)
				e.printStackTrace();
				return null;
			});

		}

	}

		void bigMethod(MessageCreateEvent event) {
			playerManager.loadItem("https://www.youtube.com/watch?v=NvS351QKFV4", new AudioLoadResultHandler() {
			    @Override
			    public void trackLoaded(AudioTrack track) {
			        player.playTrack(track);
			    }

			    @Override
			    public void playlistLoaded(AudioPlaylist playlist) {
			        for (AudioTrack track : playlist.getTracks()) {
			            player.playTrack(track);
			        }
			    }

			    @Override
			    public void noMatches() {
			        // Notify the user that we've got nothing
			    	event.getChannel().sendMessage("No Match");
			    }
			    

			    @Override
			    public void loadFailed(FriendlyException throwable) {
			        // Notify the user that everything exploded
			    	event.getChannel().sendMessage("everything exploded");
			    }});
		}		
}
