package so.mp3.app.player;

import java.util.ArrayList;

import so.mp3.type.LocalTrack;
import so.mp3.type.Tracker;
import android.os.Binder;

public class OnlineTrackPlayer extends BasicPlayerService {

	public static final String ACTION_VIEW_ONLINE_PLAYER = "so.mp3.app.IndexActivity.online";
	
	public class OnlinePlayerBinder extends Binder {

        public OnlineTrackPlayer getService() {
            return OnlineTrackPlayer.this;
        }
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        playerBinder = new OnlinePlayerBinder();
        trackList = new ArrayList<LocalTrack>();
    }
    
    @Override
    public void addTracks(ArrayList<? extends Tracker> tracks) {
        trackList = tracks;
        untake();
    }
    
	@Override
	protected int getNotificationId() {
		return 2002;
	}

	@Override
	protected String getNotificationAction() {
		return ACTION_VIEW_ONLINE_PLAYER;
	}

}
