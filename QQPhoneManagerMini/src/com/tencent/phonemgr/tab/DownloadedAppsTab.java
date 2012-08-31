package com.tencent.phonemgr.tab;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.tencent.phonemgr.R;

public class DownloadedAppsTab implements TabFactory {

	private static DownloadedAppsTab mDownloadedAppTab;
	
	private Tab mTab;
	
	private DownloadedAppsTab() {
		
	}
	
	public static DownloadedAppsTab getInstance() {
		if(mDownloadedAppTab == null) {
			mDownloadedAppTab = new DownloadedAppsTab();
		}
		return mDownloadedAppTab;
	}
	
	@Override
	public Tab createTab(ActionBar bar, ActionBar.TabListener listener) {
		if(mTab == null) {
			mTab = bar.newTab();
			mTab.setText(R.string.tab_downloaded_apps);
			mTab.setTabListener(listener);
		}
        return mTab;
	}

}
