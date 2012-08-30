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
import com.actionbarsherlock.view.Menu;
import com.tencent.phonemgr.fragment.LocalToolPanel;
import com.tencent.phonemgr.fragment.RemoteToolPanel;
import com.tencent.phonemgr.fragment.ToolBoxPanel;
import com.tencent.phonemgr.tab.LocalToolTab;
import com.tencent.phonemgr.tab.RemoteToolTab;
import com.tencent.phonemgr.tab.ToolboxTab;

public class IndexActivity extends SherlockFragmentActivity implements TabListener, OnPageChangeListener {

//	private LocalToolPanel mLocalToolPanel;
//	private ToolBoxPanel mToolBoxPanel;
//	private RemoteToolPanel mRemoteToolPanel;
	
	private ViewPager mViewPager;
	private PagerAdapter mAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	getSupportActionBar().addTab(LocalToolTab.getInstance().createTab(getSupportActionBar(), this));
    	getSupportActionBar().addTab(ToolboxTab.getInstance().createTab(getSupportActionBar(), this));
    	getSupportActionBar().addTab(RemoteToolTab.getInstance().createTab(getSupportActionBar(), this));
    	getSupportActionBar().setSelectedNavigationItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_index, menu);
        return true;
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
			mPages.add(LocalToolPanel.newInstance());
			mPages.add(ToolBoxPanel.newInstance());
			mPages.add(RemoteToolPanel.newInstance());
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
