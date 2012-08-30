package com.tencent.phonemgr.tab;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.tencent.phonemgr.R;

public class LocalToolTab implements TabFactory {

	private static LocalToolTab mLocalToolTab;
	
	private Tab mTab;
	
	private LocalToolTab() {
		
	}
	
	public static LocalToolTab getInstance() {
		if(mLocalToolTab == null) {
			mLocalToolTab = new LocalToolTab();
		}
		return mLocalToolTab;
	}
	
	@Override
	public Tab createTab(ActionBar bar, ActionBar.TabListener listener) {
		if(mTab == null) {
			mTab = bar.newTab();
			mTab.setText(R.string.tab_local_tool);
			mTab.setTabListener(listener);
		}
        return mTab;
	}

}
