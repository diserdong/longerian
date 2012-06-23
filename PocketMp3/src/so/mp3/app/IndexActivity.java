package so.mp3.app;

import java.util.List;

import so.mp3.app.fragment.Mp3ListFragment;
import so.mp3.app.fragment.Mp3ListFragment.OnSongSelectedListener;
import so.mp3.type.Mp3;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;

public class IndexActivity extends SherlockFragmentActivity implements OnSongSelectedListener, Host{

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		hideIndeterminateProgressBar();
		Mp3ListFragment content = Mp3ListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(
        android.R.id.content, content).commit();
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
//		Intent i = new Intent(getApplicationContext(), Mp3playerActivity.class);
//		i.putExtra(Mp3playerActivity.PLAYER_LINK, songs.get(position).getPlayerLink());
//		startActivity(i);
		//do nonthing
	}

}
