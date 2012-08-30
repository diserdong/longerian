package com.tencent.phonemgr.tab;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.tencent.phonemgr.R;

public class RemoteToolTab implements TabFactory {

	private static RemoteToolTab mRemoteToolTab;
	
	private Tab mTab;
	
	private RemoteToolTab() {
		
	}
	
	public static RemoteToolTab getInstance() {
		if(mRemoteToolTab == null) {
			mRemoteToolTab = new RemoteToolTab();
		}
		return mRemoteToolTab;
	}
	
	@Override
	public Tab createTab(ActionBar bar, ActionBar.TabListener listener) {
		if(mTab == null) {
			mTab = bar.newTab();
			mTab.setText(R.string.tab_remote_tool);
			mTab.setTabListener(listener);
		}
        return mTab;
	}

}
