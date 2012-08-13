package so.mp3.app.player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import so.mp3.app.IndexActivity;
import so.mp3.http.SougouClient;
import so.mp3.http.parser.DownloadLinkParser;
import so.mp3.http.request.DownloadLinkRequest;
import so.mp3.http.response.DownloadLinkResponse;
import so.mp3.player.R;
import so.mp3.type.LocalTrack;
import so.mp3.type.OnlineTrack;
import so.mp3.type.Tracker;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

public class OnlineTrackPlayer extends BasicPlayerService {

	public static final String ACTION_VIEW_ONLINE_PLAYER = "so.mp3.app.IndexActivity.online";
	
	public interface OnPreparePlayListener {
    	public void onStartPreparePlay();
    	public void onFinishPreparePlay();
    }
	
	private OnPreparePlayListener preparePlayListener;
	private DownloadLinkAndPlayTask playTask;
	
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

	public void setPreparePlayListener(OnPreparePlayListener preparePlayListener) {
		this.preparePlayListener = preparePlayListener;
	}

	@Override
	public void playTrack(int pos) {
    	if(trackList.isEmpty()) {
    		return;
    	}
        if (status > STOPED) {
            stop();
        }
        if(playTask != null) {
        	playTask.cancel(true);
        }
        playTask = new DownloadLinkAndPlayTask((OnlineTrack) trackList.get(pos), pos);
        playTask.execute();
    }
	
	private Handler mErrorToastHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what) {
			case R.string.network_wonky:
				Toast.makeText(getApplicationContext(), R.string.network_wonky, Toast.LENGTH_LONG).show();
				break;
			case R.string.can_not_find_the_song:
				Toast.makeText(getApplicationContext(), R.string.can_not_find_the_song, Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
		
	};
	
	private class DownloadLinkAndPlayTask extends AsyncTask<Void, Void, Boolean> {

		private OnlineTrack track;
		private int position;
		
		public DownloadLinkAndPlayTask(OnlineTrack orgTrack, int position) {
			this.track = orgTrack;
			this.position = position;
		}
		
		@Override
		protected void onPreExecute() {
			if(preparePlayListener != null) {
				preparePlayListener.onStartPreparePlay();
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			currentTrackPosition = position;
			if(TextUtils.isEmpty(track.getMp3Link())) {
				SougouClient sc = new SougouClient();
				DownloadLinkRequest dlr = new DownloadLinkRequest();
				dlr.setLink(track.getPlayerLink());
				DownloadLinkResponse resp = (DownloadLinkResponse) sc.excute(dlr, new DownloadLinkParser());
				if(resp.isNetworkException()) {
					mErrorToastHandler.sendEmptyMessage(R.string.network_wonky);
					setStatus(STOPED);
					return false;
				} else {
					if(TextUtils.isEmpty(resp.getLink())) {
						mErrorToastHandler.sendEmptyMessage(R.string.can_not_find_the_song);
						setStatus(STOPED);
						return false;
					} else {
						track.setMp3Link(resp.getLink());
					}
				}
			}
	        try {
	            mediaPlayer.setDataSource(getApplicationContext(), 
	            		track.getUri());
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
        	setStatus(PLAYING);
        	untake();
			return isActive();
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(preparePlayListener != null) {
				preparePlayListener.onFinishPreparePlay();
			}
			if(result) {
	    		if(notification == null) {
	    			notification = new Notification();
	    			notification.flags |= Notification.FLAG_ONGOING_EVENT;
	    			notification.icon = android.R.drawable.ic_media_play;
	    			Intent notificationIntent = new Intent(getNotificationAction());
	    			notificationIntent.setClass(getApplicationContext(), IndexActivity.class);
	    			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    			PendingIntent pendingIntent = PendingIntent.getActivity(OnlineTrackPlayer.this, 0, notificationIntent, 0);
	    			notification.contentIntent = pendingIntent;
	    		}
	    		RemoteViews views = new RemoteViews(getPackageName(), R.layout.player_status_niotification);
	            views.setImageViewResource(R.id.icon, android.R.drawable.ic_media_play);
	            views.setTextViewText(R.id.trackname, track.getTitle());
	            views.setTextViewText(R.id.artist, track.getArtist());
	    		notification.tickerText = "Player for " + track.getTitle() + " - " + track.getArtist();
	    		notification.contentView = views;
	    		startForeground(getNotificationId(), notification);
	    		if(playerListener != null) playerListener.onPlay(position, track);
	    		if(indicatorListener != null) indicatorListener.onPlay(position);
	    		mHandler.post(mRunProgress);
			} else {
				nextTrack();
			}
		}
		
	}
	
}
