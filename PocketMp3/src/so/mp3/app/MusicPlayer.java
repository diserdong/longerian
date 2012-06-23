package so.mp3.app;

import java.io.IOException;
import java.util.Observable;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;

public class MusicPlayer extends Observable implements OnErrorListener, OnCompletionListener, OnBufferingUpdateListener {

	private MediaPlayer mp;
	
	public void play(String url) {
		if(mp == null) {
			mp = new MediaPlayer();
			mp.setOnBufferingUpdateListener(this);
			mp.setOnCompletionListener(this);
			mp.setOnErrorListener(this);
		}
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
	
	@Override
	public void onCompletion(MediaPlayer player) {
		setChanged();
		Bundle b = new Bundle();
		b.putBoolean("completion", true);
		notifyObservers(b);
	}

	@Override
	public boolean onError(MediaPlayer player, int what, int extra) {
		handlerError();
		return true;
	}
	
	private void  handlerError() {
		setChanged();
		Bundle b = new Bundle();
		b.putBoolean("error", true);
		notifyObservers(b);
	}

	private long lastUpdate = 0;
	
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		if(mp.isPlaying()) {
			long now = System.currentTimeMillis();
			if(now - lastUpdate > 500) {
				setChanged();
				Bundle b = new Bundle();
				b.putInt("buffer", percent);
				b.putInt("current", mp.getCurrentPosition());
				b.putInt("duration", mp.getDuration());
				notifyObservers(b);
				lastUpdate = now;
			}
		}
	}

}
