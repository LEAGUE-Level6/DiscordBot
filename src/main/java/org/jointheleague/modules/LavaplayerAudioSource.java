package org.jointheleague.modules;

import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.audio.AudioSourceBase;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

public class LavaplayerAudioSource extends AudioSourceBase {
	private final AudioPlayer audioPlayer;
	private AudioFrame lastFrame;

	public LavaplayerAudioSource(DiscordApi api, AudioPlayer audio) {
		super(api);
		this.audioPlayer = audio;
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] getNextFrame() {
		// TODO Auto-generated method stub
		if (lastFrame == null) {
			return null;
		}
		return applyTransformers(lastFrame.getData());
	}

	@Override
	public boolean hasFinished() {
		return false;
	}

	@Override
	public boolean hasNextFrame() {
		// TODO Auto-generated method stub
		lastFrame = audioPlayer.provide();
		return lastFrame != null;
	}


	@Override
	public AudioSource copy() {
		// TODO Auto-generated method stub
        return new LavaplayerAudioSource(getApi(), audioPlayer);
        
}
	AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
	playerManager.registerSourceManager(new YoutubeAudioSourceManager());
	AudioPlayer player = playerManager.createPlayer();

	// Create an audio source and add it to the audio connection's queue
	AudioSource source = new LavaplayerAudioSource(getApi(), player);
	//audioConnection.setAudioSource(source);
	
	// You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,
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
	    }

	    @Override
	    public void loadFailed(FriendlyException throwable) {
	        // Notify the user that everything exploded
	    }
}
}

