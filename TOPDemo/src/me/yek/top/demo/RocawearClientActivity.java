package me.yek.top.demo;

import java.util.ArrayList;

import me.yek.top.demo.fragments.FavoritesFragment;
import me.yek.top.demo.fragments.ProfileFragment;
import me.yek.top.demo.fragments.ShoppingFragment;
import me.yek.top.demo.fragments.TradesFragment;
import me.yek.top.demo.util.LogUtil;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

public class RocawearClientActivity extends FragmentActivity {
	
	/**
	 * 每隔10小时refresh token
	 */
	private static final long SERVICE_INTERVAL = 1000 * 60 * 60 * 10;
	private FragmentPagerAdapter mAdapter;
	private ViewPager mPager;
	private ArrayList<Fragment> mPages = new ArrayList<Fragment>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(this.getClass().getName(), " in onCreate started");
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.index);
        
        mPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new PagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0);
        
        startRefreshService(getApplicationContext());
        LogUtil.d(this.getClass().getName(), " in onCreate finished");
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		LogUtil.d(this.getClass().getName(), " in onResume");
	}

	@Override
	protected void onStart() {
		super.onStart();
		LogUtil.d(this.getClass().getName(), " in onStart");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.d(this.getClass().getName(), " in onPause");
	}
	
	private void startRefreshService(Context context) {
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);    
		Intent intent = new Intent(context, RefreshService.class);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		long interval = SERVICE_INTERVAL;
		long firstWake = System.currentTimeMillis() + 10;
		am.setRepeating(AlarmManager.RTC, firstWake, interval, pendingIntent); 
	}

	public class PagerAdapter extends FragmentPagerAdapter {

		public PagerAdapter(FragmentManager fm) {
			super(fm);
			mPages.add(new ShoppingFragment());
			mPages.add(new ProfileFragment());
			mPages.add(new FavoritesFragment());
			mPages.add(new TradesFragment());
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
    
}