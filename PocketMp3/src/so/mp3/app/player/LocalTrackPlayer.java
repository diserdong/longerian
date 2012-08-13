package so.mp3.app.player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import so.mp3.app.IndexActivity;
import so.mp3.player.R;
import so.mp3.type.LocalTrack;
import so.mp3.type.Tracker;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.widget.RemoteViews;

public class LocalTrackPlayer extends BasicPlayerService {

	public static final String ACTION_VIEW_LOCAL_PLAYER = "so.mp3.app.IndexActivity.local";
	
    public class LocalPlayerBinder extends Binder {

        public LocalTrackPlayer getService() {
            return LocalTrackPlayer.this;
        }
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        playerBinder = new LocalPlayerBinder();
        trackList = new ArrayList<LocalTrack>();
    }
    
    @Override
    public void addTracks(ArrayList<? extends Tracker> tracks) {
        trackList = tracks;
        untake();
    }

	@Override
	protected int getNotificationId() {
		return 2001;
	}

	@Override
	protected String getNotificationAction() {
		return ACTION_VIEW_LOCAL_PLAYER;
	}
    
	@Override
	public void playTrack(int pos) {
    	if(trackList.isEmpty()) {
    		return;
    	}
        if (status > STOPED) {
            stop();
        }
        try {
            mediaPlayer.setDataSource(getApplicationContext(), 
            		trackList.get(pos).getUri());
            mediaPlayer.prepare();
        } catch (FileNotFoundException e) {
        	if(playerListener != null) playerListener.onError();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
        	if(playerListener != null) playerListener.onError();
            e.printStackTrace();
        } catch (IllegalStateException e) {
        	if(playerListener != null) playerListener.onError();
            e.printStackTrace();
        } catch (IOException e) {
        	if(playerListener != null) playerListener.onError();
            e.printStackTrace();
        }
    	mediaPlayer.start();
    	currentTrackPosition = pos;
    	setStatus(PLAYING);
    	untake();
		if(notification == null) {
			notification = new Notification();
			notification.flags |= Notification.FLAG_ONGOING_EVENT;
			notification.icon = android.R.drawable.ic_media_play;
			Intent notificationIntent = new Intent(getNotificationAction());
			notificationIntent.setClass(getApplicationContext(), IndexActivity.class);
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
			notification.contentIntent = pendingIntent;
		}
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.player_status_niotification);
        views.setImageViewResource(R.id.icon, android.R.drawable.ic_media_play);
        views.setTextViewText(R.id.trackname, trackList.get(pos).getTitle());
        views.setTextViewText(R.id.artist, trackList.get(pos).getArtist());
		notification.tickerText = "Player for " + trackList.get(pos).getTitle() + " - " + trackList.get(pos).getArtist();
		notification.contentView = views;
		startForeground(getNotificationId(), notification);
		if(playerListener != null) playerListener.onPlay(pos, trackList.get(pos));
		if(indicatorListener != null) indicatorListener.onPlay(pos);
		mHandler.post(mRunProgress);
    }
	
}
