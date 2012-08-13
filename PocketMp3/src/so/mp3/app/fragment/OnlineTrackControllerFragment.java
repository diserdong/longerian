package so.mp3.app.fragment;

import so.mp3.app.Host;
import so.mp3.app.player.BasicPlayerService.PlayerListener;
import so.mp3.app.player.OnlineTrackPlayer;
import so.mp3.app.player.OnlineTrackPlayer.OnlinePlayerBinder;
import so.mp3.player.R;
import so.mp3.type.OnlineTrack;
import so.mp3.type.Tracker;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class OnlineTrackControllerFragment extends SherlockFragment implements OnClickListener, OnSeekBarChangeListener, PlayerListener {

	private static final String TAG = "OnlineTrackControllerFragment";
	private Host host;
	
	private TextView trackName;
	private TextView artist;
	
	private SeekBar progress;
	private ImageButton playOrPause;
	private ImageButton previous;
	private ImageButton next;
	
    private AudioManager am; 
    
    private OnlineTrackPlayer playerService;
    private ServiceConnection playerServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder service) {
            OnlinePlayerBinder playerBinder = (OnlinePlayerBinder)service;
            playerService = playerBinder.getService();
            playerService.setPlayerListener(OnlineTrackControllerFragment.this);
            if(playerService.isActive()) {
            	restoreUI();
            }
            Log.d(TAG, "service connected: " + arg0.toShortString() + "/" + playerService);
        }

		@Override
        public void onServiceDisconnected(ComponentName arg0) {
        	Log.d(TAG, "service disconnected: " + arg0.toShortString());
        }
    };
    
    public static OnlineTrackControllerFragment newInstance() {
    	OnlineTrackControllerFragment f = new OnlineTrackControllerFragment();
        return f;
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        	host = (Host) activity;
        } catch (ClassCastException e) {
        	throw new ClassCastException(activity.toString() + " must implement Host");
        }
    }
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
	    Intent playerServiceIntent = new Intent(getSherlockActivity(), OnlineTrackPlayer.class);
	    getSherlockActivity().getApplicationContext().bindService(playerServiceIntent, playerServiceConnection, Context.BIND_AUTO_CREATE);
	}
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.controller_layout, null);
    	trackName = (TextView) view.findViewById(R.id.trackname);
    	artist = (TextView) view.findViewById(R.id.artist);
    	progress = (SeekBar) view.findViewById(R.id.progress);
    	playOrPause = (ImageButton) view.findViewById(R.id.btn_play_or_pause);
    	previous = (ImageButton) view.findViewById(R.id.btn_previous);
    	next = (ImageButton) view.findViewById(R.id.btn_next);
    	playOrPause.setOnClickListener(this);
    	previous.setOnClickListener(this);
    	next.setOnClickListener(this);
    	progress.setOnSeekBarChangeListener(this);
		return view;
	}
    
    @Override
	public void onDestroyView() {
		super.onDestroyView();
	}
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		getSherlockActivity().getApplicationContext().unbindService(playerServiceConnection);
//		am.abandonAudioFocus(afChangeListener);
	}
	
	private int requestAudioFocus() {
//		int result = am.requestAudioFocus(afChangeListener,
//		                                 AudioManager.STREAM_MUSIC,
//		                                 AudioManager.AUDIOFOCUS_GAIN);
//		return result;
		return 1;
	}
	
	private OnAudioFocusChangeListener afChangeListener = new OnAudioFocusChangeListener() {
	    public void onAudioFocusChange(int focusChange) {
//	        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
//	        	pause();
//	        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
//	        	start();
//	        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
//	            am.abandonAudioFocus(afChangeListener);
//	            stop();
//	        }
	    }
	};

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_previous:
			if(playerService != null) {
				playerService.prevTrack();
			}
			break;
		case R.id.btn_play_or_pause:
			if(playerService != null) {
				playerService.play();
			}
			break;
		case R.id.btn_next:
			if(playerService != null) {
				playerService.nextTrack();
			}
			break;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if(fromUser) {
			playerService.seekTrack(progress * playerService.getCurrentTrackDuration() / seekBar.getMax());
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onPlay(int position, Tracker mp3) {
		updatePlayPauseBtn(true);
		updateDisplay(mp3);
	}

	@Override
	public void onPause(int position) {
		updatePlayPauseBtn(false);
	}

	@Override
	public void onStop(int position) {
		progress.setProgress(0);
		updateDisplay(null);
	}

	@Override
	public void onProgress(int max, int current) {
		updateProgress(max, current);
	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub
		
	}
	
	private void restoreUI() {
		if(playerService != null) {
			updatePlayPauseBtn(playerService.isPlaying());
			updateDisplay(playerService.getCurrentTrack());
			updateProgress(playerService.getCurrentTrackDuration(), playerService.getCurrentTrackProgress());
		}
	}
	
	private void updatePlayPauseBtn(boolean isPlaying) {
		if(isPlaying) {
			playOrPause.setImageResource(R.drawable.av_pause);
		} else {
			playOrPause.setImageResource(R.drawable.av_play);
		}
	}
	
	private void updateDisplay(Tracker mp3) {
		if(mp3 != null) {
			trackName.setText(mp3.getTitle());
			artist.setText(mp3.getArtist());
		} else {
			trackName.setText("");
			artist.setText("");
		}
	}
	
	private void updateProgress(int duration, int current) {
        int currentPercent = (int) ((progress.getMax() * current * 1.0) / duration);
        progress.setProgress(currentPercent);
    }
	
}
