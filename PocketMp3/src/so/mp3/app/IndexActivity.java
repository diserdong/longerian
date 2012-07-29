package so.mp3.app;

import so.mp3.app.downloader.DownloadService;
import so.mp3.app.fragment.ControllerFragment;
import so.mp3.app.fragment.Mp3ListFragment;
import so.mp3.app.fragment.Mp3ListFragment.OnSongSelectedListener;
import so.mp3.app.fragment.SongOptionDialogFragment.OnOptionSelectedListener;
import so.mp3.http.SougouClient;
import so.mp3.http.parser.DownloadLinkParser;
import so.mp3.http.request.DownloadLinkRequest;
import so.mp3.http.response.DownloadLinkResponse;
import so.mp3.player.R;
import so.mp3.type.Mp3;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;

public class IndexActivity extends SherlockFragmentActivity implements OnSongSelectedListener, OnOptionSelectedListener, Host{

	private DownloadLinkTask downloadLinkTask;
	private Mp3ListFragment songList;
	private ControllerFragment controller;
	private enum PostDownlaodLinkAction {
		SHARE,
		DOWNLOAD,
		PLAY
	};
	
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
	protected void onDestroy() {
		super.onDestroy();
		if(downloadLinkTask != null) {
			downloadLinkTask.cancel(true);
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

	@Override
	public void onSongSelected(Mp3 mp3) {
		if(TextUtils.isEmpty(mp3.getMp3Link())) {
			downloadLinkTask = new DownloadLinkTask(PostDownlaodLinkAction.PLAY, mp3);
			downloadLinkTask.execute(mp3.getPlayerLink());
		} else {
			play(mp3);
		}
	}
	
	private void play(Mp3 mp3) {
		controller.handleNewMp3(mp3);
	}

	@Override
	public void onDownloadOptionSelected(Mp3 mp3) {
		if(TextUtils.isEmpty(mp3.getMp3Link())) {
			downloadLinkTask = new DownloadLinkTask(PostDownlaodLinkAction.DOWNLOAD, mp3);
			downloadLinkTask.execute(mp3.getPlayerLink());
		} else {
			download(mp3);
		}
	}
	
	private void download(Mp3 mp3) {
	    startService(new Intent(getApplicationContext(), DownloadService.class)
        	.putExtra(DownloadService.TARGET_URL, mp3.getMp3Link())
        	.putExtra(DownloadService.TARGET_NAME, mp3.getTitle() + "-" + mp3.getSinger() + ".mp3"));
	}

	@Override
	public void onShareOptionSelected(Mp3 mp3) {
		if(TextUtils.isEmpty(mp3.getMp3Link())) {
			downloadLinkTask = new DownloadLinkTask(PostDownlaodLinkAction.SHARE, mp3);
			downloadLinkTask.execute(mp3.getPlayerLink());
		} else {
			shareMp3(mp3);
		}
	}
	
	private void shareMp3(Mp3 mp3) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mp3.getTitle());
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mp3.getAlbum() + "\n" + mp3.getSinger() + "\n" + mp3.getMp3Link());
		startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
	}

	private class DownloadLinkTask extends AsyncTask<String, Void, DownloadLinkResponse> {

		private PostDownlaodLinkAction action;
		private Mp3 mp3;
		
		public DownloadLinkTask(PostDownlaodLinkAction action, Mp3 orgMp3) {
			this.action = action;
			this.mp3 = orgMp3;
		}
		
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
					Toast.makeText(getApplicationContext(), R.string.can_not_find_the_song, Toast.LENGTH_LONG).show();
				} else {
					mp3.setMp3Link(result.getLink());
					switch (action) {
					case SHARE:
						shareMp3(mp3);
						break;
					case DOWNLOAD:
						download(mp3);
						break;
					case PLAY:
						play(mp3);
						break;
					default:
						break;
					}
				}
			}
		}
		
	}
	
}
