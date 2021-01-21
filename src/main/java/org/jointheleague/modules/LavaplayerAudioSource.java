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
    private AudioPlayerManager playerManager;
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
	
	
	// You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,
	
	  
}
