package so.mp3.app;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;

public class MusicPlayer {

	private MediaPlayer mp;
	
	public MusicPlayer() {
		mp = new MediaPlayer();
	}

	public void setOnCompletionListener(OnCompletionListener l) {
		mp.setOnCompletionListener(l);
	}
	
	public void setOnErrorListener(OnErrorListener l) {
		mp.setOnErrorListener(l);
	}
	
	public void setOnBufferingUpdateListener(OnBufferingUpdateListener l) {
		mp.setOnBufferingUpdateListener(l);
	}
	
	public void play(String url) {
		try {
			mp.reset();
			mp.setDataSource(url);
			mp.prepare();
			start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		if(mp != null && !mp.isPlaying()) {
			mp.start();
		}
	}
	
	public void pause() {
		if(mp != null && mp.isPlaying()) {
			mp.pause();
		}
	}
	
	public void stop() {
		if(mp != null) {
			mp.stop();
			mp.reset();
		}
	}
	
	public void release() {
		if(mp != null) {
			mp.reset();
			mp.release();
		}
	}
	
	public boolean isPlaying() {
		if(mp != null) {
			return mp.isPlaying();
		} else {
			return false;
		}
	}
	
	public int getDuration() {
		if(mp != null) {
			return mp.getDuration();
		} else {
			return -1;
		}
	}
	
	public int getCurrentPosition() {
		if(mp != null) {
			return mp.getCurrentPosition();
		} else {
			return -1;
		}
	}
	
}
