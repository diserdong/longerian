package so.mp3.app;

import java.util.List;

import so.mp3.app.fragment.ControllerFragment;
import so.mp3.app.fragment.Mp3ListFragment;
import so.mp3.app.fragment.Mp3ListFragment.OnSongSelectedListener;
import so.mp3.player.R;
import so.mp3.type.Mp3;
import android.media.AudioManager;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;

public class IndexActivity extends SherlockFragmentActivity implements OnSongSelectedListener, Host{

	private Mp3ListFragment songList;
	private ControllerFragment controller;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.panel_layout);
		songList = Mp3ListFragment.newInstance();
		controller = ControllerFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
        	.add(R.id.song_list, songList)
        	.add(R.id.controller, controller)
        	.commit();
        hideIndeterminateProgressBar();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}

	@Override
	public void showIndeterminateProgressBar() {
		setSupportProgressBarIndeterminateVisibility(true);
	}

	@Override
	public void hideIndeterminateProgressBar() {
		setSupportProgressBarIndeterminateVisibility(false);
	}

	@Override
	public void onSongSelected(List<Mp3> songs, int position) {
		controller.handleNewMp3(songs.get(position));
	}

}
