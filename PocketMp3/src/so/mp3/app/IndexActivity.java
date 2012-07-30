package so.mp3.app;

import java.util.ArrayList;

import so.mp3.app.downloader.DownloadService;
import so.mp3.app.fragment.ControllerFragment;
import so.mp3.app.fragment.LocalMp3ListFragment;
import so.mp3.app.fragment.OnlineMp3ListFragment;
import so.mp3.app.fragment.OnlineMp3ListFragment.OnSongSelectedListener;
import so.mp3.app.fragment.SongOptionDialogFragment.OnOptionSelectedListener;
import so.mp3.http.SougouClient;
import so.mp3.http.parser.DownloadLinkParser;
import so.mp3.http.request.DownloadLinkRequest;
import so.mp3.http.response.DownloadLinkResponse;
import so.mp3.player.R;
import so.mp3.type.OnlineMp3;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;

public class IndexActivity extends SherlockFragmentActivity implements OnSongSelectedListener, OnOptionSelectedListener, 
	ActionBar.TabListener, OnPageChangeListener, Host{

	private DownloadLinkTask downloadLinkTask;
	private ControllerFragment controller;
	private enum PostDownlaodLinkAction {
		SHARE,
		DOWNLOAD,
		PLAY
	};
	private final static int TAB_ONLINE = 0;
	private final static int TAB_LOCAL = 1;
	
	private ViewPager  mViewPager;
	private PagerAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.panel_layout);
	    mViewPager = (ViewPager) findViewById(R.id.pager);
	    mAdapter = new PagerAdapter(getSupportFragmentManager());
    	mViewPager.setAdapter(mAdapter);
    	mViewPager.setCurrentItem(TAB_ONLINE);
    	mViewPager.setOnPageChangeListener(this);
    	getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	getSupportActionBar().addTab(getOnlineTab());
    	getSupportActionBar().addTab(getLocalTab());
		controller = ControllerFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
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
	public void onSongSelected(OnlineMp3 mp3) {
		if(TextUtils.isEmpty(mp3.getMp3Link())) {
			downloadLinkTask = new DownloadLinkTask(PostDownlaodLinkAction.PLAY, mp3);
			downloadLinkTask.execute(mp3.getPlayerLink());
		} else {
			play(mp3);
		}
	}
	
	private void play(OnlineMp3 mp3) {
		controller.handleNewMp3(mp3);
	}

	@Override
	public void onDownloadOptionSelected(OnlineMp3 mp3) {
		if(TextUtils.isEmpty(mp3.getMp3Link())) {
			downloadLinkTask = new DownloadLinkTask(PostDownlaodLinkAction.DOWNLOAD, mp3);
			downloadLinkTask.execute(mp3.getPlayerLink());
		} else {
			download(mp3);
		}
	}
	
	private void download(OnlineMp3 mp3) {
	    startService(new Intent(getApplicationContext(), DownloadService.class)
        	.putExtra(DownloadService.TARGET_URL, mp3.getMp3Link())
        	.putExtra(DownloadService.TARGET_NAME, mp3.getTitle() + "-" + mp3.getSinger() + ".mp3"));
	}

	@Override
	public void onShareOptionSelected(OnlineMp3 mp3) {
		if(TextUtils.isEmpty(mp3.getMp3Link())) {
			downloadLinkTask = new DownloadLinkTask(PostDownlaodLinkAction.SHARE, mp3);
			downloadLinkTask.execute(mp3.getPlayerLink());
		} else {
			shareMp3(mp3);
		}
	}
	
	private void shareMp3(OnlineMp3 mp3) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mp3.getTitle());
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mp3.getAlbum() + "\n" + mp3.getSinger() + "\n" + mp3.getMp3Link());
		startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
	}

	private class DownloadLinkTask extends AsyncTask<String, Void, DownloadLinkResponse> {

		private PostDownlaodLinkAction action;
		private OnlineMp3 mp3;
		
		public DownloadLinkTask(PostDownlaodLinkAction action, OnlineMp3 orgMp3) {
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

	private class PagerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mPages = new ArrayList<Fragment>();
		
		public PagerAdapter(FragmentManager fm) {
			super(fm);
			mPages.add(OnlineMp3ListFragment.newInstance());
			mPages.add(LocalMp3ListFragment.newInstance());
		}

		@Override
		public Fragment getItem(int position) {
			return mPages.get(position);
		}

		@Override
		public int getCount() {
			return mPages.size();
		}

    }
	
	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		getSupportActionBar().setSelectedNavigationItem(position);
	}

	private ActionBar.Tab getOnlineTab() {
		ActionBar.Tab tab = getSupportActionBar().newTab();
        tab.setText(R.string.tab_online);
        tab.setTag(getString(R.string.tab_online));
        tab.setTabListener(this);
        return tab;
	}
	
	private ActionBar.Tab getLocalTab() {
		ActionBar.Tab tab = getSupportActionBar().newTab();
        tab.setText(R.string.tab_local);
        tab.setTag(getString(R.string.tab_local));
        tab.setTabListener(this);
        return tab;
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		switch(tab.getPosition()) {
		case TAB_ONLINE:
			mViewPager.setCurrentItem(TAB_ONLINE, true);
			break;
		case TAB_LOCAL:
			mViewPager.setCurrentItem(TAB_LOCAL, true);
			break;
		default:
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
	
}
