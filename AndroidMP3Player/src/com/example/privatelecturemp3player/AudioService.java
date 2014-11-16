package com.example.privatelecturemp3player;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 
 * @author Hani Fakhouri
 *
 */

public class AudioService extends Service {

    private final IBinder mBinder = new LocalBinder();
	
	private MediaPlayer player;
	private boolean isPaused;
	
	private static final String[] tracks = new String[]
			{
				"/sdcard/android/audio/0/17/105.mp3",
				"/sdcard/android/audio/0/17/106.mp3",
				"/sdcard/android/audio/0/17/107.mp3",
				"/sdcard/android/audio/0/17/108.mp3"
			};
	
	private int trackNr;
	
	@Override
	public IBinder onBind(Intent intent) {
		isPaused = false;
		trackNr = 0;
		return mBinder;
	}
	
    public class LocalBinder extends Binder {
    	AudioService getService() {
            return AudioService.this;
        }
    }
	
    private void getTrack(String tackSDcardPath) {
    	player = new MediaPlayer();
    	Uri myUri = Uri.fromFile(new File(tackSDcardPath));
    	player.setAudioStreamType(AudioManager.STREAM_MUSIC);
    	try {
    		player.setDataSource(getApplicationContext(), myUri);
    	} catch (Exception e) {
    	}
    }
    
	public void play() {
		Log.d("hani", "PLAY");
		try {
			if (player == null)
				getTrack(tracks[trackNr]);
			if (!player.isPlaying()) {
				if (isPaused) {
					isPaused = false;
					player.start();
				} else {
					player.prepare();
					player.start();
				}
			}
		} catch (Exception e) {
		}
	}
	
	public String getPlayingTrackName() {
		return (new File(tracks[trackNr])).getName();
	}
	
	public void previous() {
		stop();
		trackNr--;
		if (trackNr < 0)
			trackNr = tracks.length - 1;
		play();
	}
	
	public void next() {
		stop();
		trackNr++;
		if (trackNr >= tracks.length)
			trackNr = 0;
		play();
	}
	
	public void pause() {
		if (player != null) {
			isPaused = true;
			player.pause();
		}
	}
	
	public void stop() {
		Log.d("hani", "STOP");
		try {
			isPaused = false;
			if (player != null) {
				player.stop();
				player.release();
				player = null;
			}
		} catch (Exception e) {
		}
	}

}
