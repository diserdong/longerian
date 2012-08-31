package com.tencent.phonemgr.tab;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.tencent.phonemgr.R;

public class OriginalApksTab implements TabFactory {

	private static OriginalApksTab mOriginalApksTab;
	
	private Tab mTab;
	
	private OriginalApksTab() {
		
	}
	
	public static OriginalApksTab getInstance() {
		if(mOriginalApksTab == null) {
			mOriginalApksTab = new OriginalApksTab();
		}
		return mOriginalApksTab;
	}
	
	@Override
	public Tab createTab(ActionBar bar, ActionBar.TabListener listener) {
		if(mTab == null) {
			mTab = bar.newTab();
			mTab.setText(R.string.tab_original_apks);
			mTab.setTabListener(listener);
		}
        return mTab;
	}

}
