package so.mp3.app.player;

import java.util.ArrayList;

import so.mp3.type.LocalTrack;
import so.mp3.type.Tracker;
import android.os.Binder;

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
    
}
