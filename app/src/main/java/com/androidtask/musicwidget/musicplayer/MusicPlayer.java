package com.androidtask.musicwidget.musicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import com.androidtask.musicwidget.model.Song;

import java.io.IOException;

/**
 * The type Music player.
 */
public class MusicPlayer implements OnCompletionListener {
	private MediaPlayer player;
	private final Context context;
	private MusicPlayerCompletionListener onMusicCompletionListener;
	private static final String TAG = "Music Player";


    /**
     * Instantiates a new Music player.
     *
     * @param context the context
     */
    public MusicPlayer(Context context){
		this.context = context;
	}


    /**
     * Is playing boolean.
     *
     * @return the boolean
     */
    public boolean isPlaying(){
		return player != null && player.isPlaying();
	}

    /**
     * Is paused boolean.
     *
     * @return the boolean
     */
    public boolean isPaused(){
		return player != null && !player.isPlaying();
	}

    /**
     * Is stopped boolean.
     *
     * @return the boolean
     */
    public boolean isStopped(){
		return player == null;
	}

    /**
     * Sets song.
     *
     * @param song the song
     * @throws IllegalArgumentException the illegal argument exception
     * @throws SecurityException        the security exception
     * @throws IllegalStateException    the illegal state exception
     * @throws IOException              the io exception
     */
    public void setSong(Song song) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		boolean wasPlaying = isPlaying();
		
		if (player == null) {
			player = new MediaPlayer();
			player.setOnCompletionListener(this);
		}else {
			player.reset();
		}
		
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		player.setDataSource(context, song.getURI());
		player.prepare();
		
		if (wasPlaying)
			player.start();
		
		Log.d(TAG, "Changed song to: " + song.getTitle());
	}

    /**
     * Play.
     */
    public void play(){
		if (player == null)
			throw new IllegalStateException("Must call setSong() before calling play()");
		
		player.start();
	}

    /**
     * Pause.
     */
    public void pause(){
		if(isPlaying()){
			player.pause();
			Log.d(TAG, "Music paused");
		}
	}

    /**
     * Stop.
     */
    public void stop(){
		if (player != null) {
			player.stop();
			player.release();
			player = null;
		}
		Log.d(TAG, "Music Stopped");
	}


    /**
     * Register a callback to be invoked when the end of a song has been reached during playback
     *
     * @param listener the listener
     */
    public void setOnCompletionListener(MusicPlayerCompletionListener listener){
		onMusicCompletionListener = listener;
	}
	
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.d(TAG, "Song finished playing");
		try {
			onMusicCompletionListener.onMusicCompletion();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
