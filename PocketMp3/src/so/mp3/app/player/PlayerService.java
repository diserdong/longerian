package so.mp3.app.player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import so.mp3.app.IndexActivity;
import so.mp3.player.R;
import so.mp3.type.LocalMp3;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.RemoteViews;

public class PlayerService extends Service {
	
	public static final String TAG = "PlayerService";
	public static final String ACTION_VIEW_PLAYER = "so.mp3.app.IndexActivity.player";
    public static final int STOPED = -1, PAUSED = 0, PLAYING = 1;
    private MediaPlayer mediaPlayer;
    private ArrayList<LocalMp3> localMp3List;
    private int status, currentTrackPosition;
    private boolean taken;
    private IBinder playerBinder;
    private Notification notification;
    private int notificationId = 2001;
    private PlayerListener listener;

    public interface PlayerListener {
    	public void onPlay(int position, LocalMp3 mp3);
    	public void onPause(int position);
    	public void onStop(int position);
    	public void onProgress(int max, int current);
    	public void onError();
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        localMp3List = new ArrayList<LocalMp3>();
        currentTrackPosition = -1;
        setStatus(STOPED);
        playerBinder = new PlayerBinder();
        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer arg0) {
                if (currentTrackPosition == localMp3List.size()-1) {
                    stop();
                } else {
                    nextTrack();
                }
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return playerBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
    	Log.d(TAG, "service destroyed");
        return super.onUnbind(intent);
    }
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "service destroyed");
	}

	public void setPlayerListener(PlayerListener listener) {
		this.listener = listener;
	}

	public void take() {
        taken = true;
    }

    private void untake() {
        synchronized (this) {
            taken = false;
            notifyAll();
        }
    }

    public boolean isTaken() {
        return taken;
    }

    private void setStatus(int s) {
        status = s;
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<LocalMp3> getTracklist() {
        return localMp3List;
    }

    public LocalMp3 getTrack(int pos) {
        return localMp3List.get(pos);
    }

    public LocalMp3 getCurrentTrack() {
        if (currentTrackPosition < 0) {
            return null;
        } else {
            return localMp3List.get(currentTrackPosition);
        }
    }

    public int getCurrentTrackPosition() {
        return currentTrackPosition;
    }

    public void addTrack(LocalMp3 track) {
        localMp3List.add(track);
        untake();
    }

    public void removeTrack(int pos) {
        if (pos == currentTrackPosition) {
            stop();
        }
        if (pos < currentTrackPosition) {
            currentTrackPosition--;
        }
        localMp3List.remove(pos);
        untake();
    }

    public void clearTracklist() {
        if (status > STOPED) {
            stop();
        }
        localMp3List.clear();
        untake();
    }

    public void playTrack(int pos) {
    	if(localMp3List.isEmpty()) {
    		return;
    	}
        if (status > STOPED) {
            stop();
        }
        boolean isSuccess = true;
        try {
            mediaPlayer.setDataSource(getApplicationContext(), 
            		Uri.withAppendedPath(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
                        "/"+String.valueOf(localMp3List.get(pos).getId())
                        ));
            mediaPlayer.prepare();
        } catch (FileNotFoundException e) {
        	isSuccess = false;
        	if(listener != null) listener.onError();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
        	isSuccess = false;
        	if(listener != null) listener.onError();
            e.printStackTrace();
        } catch (IllegalStateException e) {
        	isSuccess = false;
        	if(listener != null) listener.onError();
            e.printStackTrace();
        } catch (IOException e) {
        	isSuccess = false;
        	if(listener != null) listener.onError();
            e.printStackTrace();
        }
        if(isSuccess) {
        	mediaPlayer.start();
        	currentTrackPosition = pos;
        	setStatus(PLAYING);
        	untake();
    		if(notification == null) {
    			notification = new Notification();
    			notification.flags |= Notification.FLAG_ONGOING_EVENT;
    			notification.icon = android.R.drawable.ic_media_play;
    			Intent notificationIntent = new Intent(ACTION_VIEW_PLAYER);
    			notificationIntent.setClass(getApplicationContext(), IndexActivity.class);
    			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    			notification.contentIntent = pendingIntent;
    		}
    		RemoteViews views = new RemoteViews(getPackageName(), R.layout.player_status_niotification);
            views.setImageViewResource(R.id.icon, android.R.drawable.ic_media_play);
            views.setTextViewText(R.id.trackname, localMp3List.get(pos).getTitle());
            views.setTextViewText(R.id.artist, localMp3List.get(pos).getArtist());
    		notification.tickerText = "Player for " + localMp3List.get(pos).getTitle() + " - " + localMp3List.get(pos).getArtist();
    		notification.contentView = views;
    		startForeground(notificationId, notification);
    		if(listener != null) listener.onPlay(pos, localMp3List.get(pos));
    		mHandler.post(mRunProgress);
        }
    }

    public void play() {
        switch (status) {
        case STOPED:
            if (!localMp3List.isEmpty()) {
                playTrack(0);
            }
        break;
        case PLAYING:
            pause();
        break;
        case PAUSED:
            start();
        break;
        }
        untake();
    }
    
    public void start() {
    	mediaPlayer.start();
        setStatus(PLAYING);
        startForeground(notificationId, notification);
		if(listener != null) listener.onPlay(currentTrackPosition, localMp3List.get(currentTrackPosition));
		mHandler.post(mRunProgress);
		untake();
    }
    
    public void pause() {
        mediaPlayer.pause();
        setStatus(PAUSED);
        stopForeground(true);
        if(listener != null) listener.onPause(currentTrackPosition);
        mHandler.removeCallbacks(mRunProgress);
        untake();
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        currentTrackPosition = -1;
        setStatus(STOPED);
        stopForeground(true);
        if(listener != null) listener.onStop(currentTrackPosition);
        mHandler.removeCallbacks(mRunProgress);
        untake();
    }
    
    public boolean isActive() {
    	return status > STOPED;
    }

    public void nextTrack() {
        if (currentTrackPosition < localMp3List.size()-1) {
            playTrack(currentTrackPosition+1);
        }
    }

    public void prevTrack() {
        if (currentTrackPosition > 0) {
            playTrack(currentTrackPosition-1);
        }
    }

    public int getCurrentTrackProgress() {
        if (status > STOPED) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public int getCurrentTrackDuration() {
        if (status > STOPED) {
            return localMp3List.get(currentTrackPosition).getDuration();
        } else {
            return 0;
        }
    }

    public void seekTrack(int p) {
        if (status > STOPED) {
            mediaPlayer.seekTo(p);
            untake();
        }
    }

    public class PlayerBinder extends Binder {

        public PlayerService getService() {
            return PlayerService.this;
        }
    }
    
    private Runnable mRunProgress = new Runnable() {
		
		@Override
		public void run() {
			if(listener != null && mediaPlayer.isPlaying()) {
				listener.onProgress(mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition());
			}
			mHandler.postDelayed(mRunProgress, 1000);
		}
	};
    
    private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
		}
    	
    };

}
