package so.mp3.app;

import java.util.Observable;
import java.util.Observer;

import so.mp3.http.SougouClient;
import so.mp3.http.parser.DownloadLinkParser;
import so.mp3.http.request.DownloadLinkRequest;
import so.mp3.http.response.DownloadLinkResponse;
import so.mp3.player.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

public class Mp3playerActivity extends SherlockActivity implements Observer, Host {
	
	public static final String PLAYER_LINK = "playerLink";
	
	private MusicPlayer mp;
	private SeekBar progress;
	private String playerLink;
	private String mp3Link;
	
	private DownloadLinkTask task;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);
        playerLink = getIntent().getStringExtra(PLAYER_LINK);
        progress = (SeekBar) findViewById(R.id.progress);
        mp = new MusicPlayer();
        mp.addObserver(this);
        task = new DownloadLinkTask();
        task.execute(playerLink);
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mp.release();
	}

	public void clickHandler(View v) {
    	switch(v.getId()) {
    	case R.id.play:
    		if(!TextUtils.isEmpty(mp3Link)) {
    			mp.play(mp3Link);
    		}
    		break;
    	case R.id.stop:
    		mp.stop();
    		break;
    	case R.id.pause:
    		if(mp.isPlaying()) {
    			mp.pause();
    		} else {
    			mp.start();
    		}
    		break;
    	}
    }
    
    private void updateProgress(SeekBar progress, int current, int bufferPercent, int duration) {
    	progress.setSecondaryProgress(bufferPercent);
        int currentPercent = progress.getMax() * current / duration;
        progress.setProgress(currentPercent);
    }

	@Override
	public void update(Observable observable, Object data) {
		Bundle b = (Bundle) data;
		updateProgress(progress, b.getInt("current"), b.getInt("bufferPercent"), b.getInt("duration"));
	}
	 
	private class DownloadLinkTask extends AsyncTask<String, Void, DownloadLinkResponse> {

		@Override
		protected void onPreExecute() {
			showIndeterminateProgressBar();
		}

		@Override
		protected DownloadLinkResponse doInBackground(String... params) {
			SougouClient sc = new SougouClient();
			DownloadLinkRequest dlr = new DownloadLinkRequest();
			dlr.setLink(params[0]);
			DownloadLinkResponse resp = (DownloadLinkResponse) sc.excute(dlr, new DownloadLinkParser());
			return resp;
		}
		
		@Override
		protected void onPostExecute(DownloadLinkResponse result) {
			hideIndeterminateProgressBar();
			if(result.isNetworkException()) {
				Toast.makeText(getApplicationContext(), R.string.network_wonky, Toast.LENGTH_LONG).show();
			} else {
				if(TextUtils.isEmpty(result.getLink())) {
					Toast.makeText(getApplicationContext(), R.string.search_result_empty, Toast.LENGTH_LONG).show();
				} else {
					mp3Link = result.getLink();
					mp.play(mp3Link);
				}
			}
		}
		
	}
	

    @Override
	public void showIndeterminateProgressBar() {
		setSupportProgressBarIndeterminateVisibility(true);
	}

	@Override
	public void hideIndeterminateProgressBar() {
		setSupportProgressBarIndeterminateVisibility(false);
	}
    
}