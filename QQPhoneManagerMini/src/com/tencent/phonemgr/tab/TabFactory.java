package com.tencent.phonemgr.tab;

import com.actionbarsherlock.app.ActionBar;

public interface TabFactory {

	public ActionBar.Tab createTab(ActionBar bar, ActionBar.TabListener listener);

}
