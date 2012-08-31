package com.tencent.phonemgr;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.tencent.phonemgr.fragment.DownloadedAppsPanel;
import com.tencent.phonemgr.fragment.OriginalApksPanel;
import com.tencent.phonemgr.tab.DownloadedAppsTab;
import com.tencent.phonemgr.tab.OriginalApksTab;

public class InstallerActivity extends SherlockFragmentActivity implements TabListener, OnPageChangeListener {

	private ViewPager mViewPager;
	private PagerAdapter mAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installer);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	getSupportActionBar().addTab(DownloadedAppsTab.getInstance().createTab(getSupportActionBar(), this));
    	getSupportActionBar().addTab(OriginalApksTab.getInstance().createTab(getSupportActionBar(), this));
    	getSupportActionBar().setSelectedNavigationItem(0);
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition(), true);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
	
	private class PagerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mPages = new ArrayList<Fragment>();
		
		public PagerAdapter(FragmentManager fm) {
			super(fm);
			mPages.add(DownloadedAppsPanel.newInstance());
			mPages.add(OriginalApksPanel.newInstance());
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
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		getSupportActionBar().setSelectedNavigationItem(position);
	}
	
}
