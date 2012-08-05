package so.mp3.app;

import java.util.ArrayList;

import so.mp3.app.fragment.LocalTrackControllerFragment;
import so.mp3.app.fragment.LocalTrackListFragment;
import so.mp3.app.fragment.OnlineTrackControllerFragment;
import so.mp3.app.fragment.OnlineTrackListFragment;
import so.mp3.app.player.LocalTrackPlayer;
import so.mp3.app.player.OnlineTrackPlayer;
import so.mp3.player.R;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

public class IndexActivity extends SherlockFragmentActivity implements
	ActionBar.TabListener, OnPageChangeListener, Host {

	private LocalTrackControllerFragment localController;
	private OnlineTrackControllerFragment onlineController;

	private final static int TAB_ONLINE = 0;
	private final static int TAB_LOCAL = 1;
	
	private ViewPager mViewPager;
	private PagerAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        startService(new Intent(this, LocalTrackPlayer.class));
        startService(new Intent(this, OnlineTrackPlayer.class));
		setContentView(R.layout.panel_layout);
		onlineController = (OnlineTrackControllerFragment) getSupportFragmentManager().findFragmentById(R.id.online_controller);
		localController = (LocalTrackControllerFragment) getSupportFragmentManager().findFragmentById(R.id.local_controller);
	    mViewPager = (ViewPager) findViewById(R.id.pager);
	    mAdapter = new PagerAdapter(getSupportFragmentManager());
    	mViewPager.setAdapter(mAdapter);
    	mViewPager.setOnPageChangeListener(this);
    	getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	getSupportActionBar().addTab(getOnlineTab());
    	getSupportActionBar().addTab(getLocalTab());
        hideIndeterminateProgressBar();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if(getIntent().getAction().equals(LocalTrackPlayer.ACTION_VIEW_LOCAL_PLAYER)) {
        	mViewPager.setCurrentItem(TAB_LOCAL);
        	showLocalControllerOnly();
        } else {
        	mViewPager.setCurrentItem(TAB_ONLINE);
        	showOnlineControllerOnly();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, R.id.action_set, 1, R.string.setting)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		menu.add(0, R.id.action_exit, 2, R.string.exit)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
        case R.id.action_set:
        	//TODO
        	break;
        case R.id.action_exit:
        	stopService(new Intent(this, LocalTrackPlayer.class));
        	stopService(new Intent(this, OnlineTrackPlayer.class));
        	finish();
        	break;
		default: 
			break;
		}
		return true;
	}
	
	@Override
	public void showIndeterminateProgressBar() {
		setSupportProgressBarIndeterminateVisibility(true);
	}

	@Override
	public void hideIndeterminateProgressBar() {
		setSupportProgressBarIndeterminateVisibility(false);
	}

	private class PagerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mPages = new ArrayList<Fragment>();
		
		public PagerAdapter(FragmentManager fm) {
			super(fm);
			mPages.add(OnlineTrackListFragment.newInstance());
			mPages.add(LocalTrackListFragment.newInstance());
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
			showOnlineControllerOnly();
			break;
		case TAB_LOCAL:
			mViewPager.setCurrentItem(TAB_LOCAL, true);
			showLocalControllerOnly();
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
	
	private void showLocalControllerOnly() {
		getSupportFragmentManager().beginTransaction().hide(onlineController).show(localController).commit();
	}
	
	private void showOnlineControllerOnly() {
		getSupportFragmentManager().beginTransaction().hide(localController).show(onlineController).commit();
	}
	
}
