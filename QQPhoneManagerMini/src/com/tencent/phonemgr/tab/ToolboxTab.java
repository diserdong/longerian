package com.tencent.phonemgr.tab;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.tencent.phonemgr.R;

public class ToolboxTab implements TabFactory {

	private static ToolboxTab mToolboxTab;
	
	private Tab mTab;
	
	private ToolboxTab() {
		
	}
	
	public static ToolboxTab getInstance() {
		if(mToolboxTab == null) {
			mToolboxTab = new ToolboxTab();
		}
		return mToolboxTab;
	}
	
	@Override
	public Tab createTab(ActionBar bar, ActionBar.TabListener listener) {
		if(mTab == null) {
			mTab = bar.newTab();
			mTab.setText(R.string.tab_toolbox);
			mTab.setTabListener(listener);
		}
        return mTab;
	}

}
