package com.tencent.phonemgr;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

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

public class IndexActivity extends SherlockFragmentActivity implements TabListener {

	private LocalToolPanel mLocalToolPanel;
	private ToolBoxPanel mToolBoxPanel;
	private RemoteToolPanel mRemoteToolPanel;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	getSupportActionBar().addTab(LocalToolTab.getInstance().createTab(getSupportActionBar(), this));
    	getSupportActionBar().addTab(ToolboxTab.getInstance().createTab(getSupportActionBar(), this));
    	getSupportActionBar().addTab(RemoteToolTab.getInstance().createTab(getSupportActionBar(), this));
    	initPanels();
//    	getSupportActionBar().setSelectedNavigationItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_index, menu);
        return true;
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		switch(tab.getPosition()) {
		case 0:
			showLocalToolPanel();
			break;
		case 1:
			showToolBoxPanel();
			break;
		case 2:
			showRemoteToolPanel();
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
	
	private void initPanels() {
		mLocalToolPanel = LocalToolPanel.newInstance();
    	mToolBoxPanel = ToolBoxPanel.newInstance();
    	mRemoteToolPanel = RemoteToolPanel.newInstance();
    	getSupportFragmentManager().beginTransaction()
    		.add(R.id.panel, mLocalToolPanel)
    		.add(R.id.panel, mToolBoxPanel)
    		.add(R.id.panel, mRemoteToolPanel)
    		.commit();
	}
	
	private void showLocalToolPanel() {
		getSupportFragmentManager().beginTransaction()
			.show(mLocalToolPanel)
			.hide(mToolBoxPanel)
			.hide(mRemoteToolPanel)
			.commit();
	}
	
	private void showToolBoxPanel() {
		getSupportFragmentManager().beginTransaction()
		.show(mToolBoxPanel)
		.hide(mLocalToolPanel)
		.hide(mRemoteToolPanel)
		.commit();
	}
	
	private void showRemoteToolPanel() {
		getSupportFragmentManager().beginTransaction()
		.show(mRemoteToolPanel)
		.hide(mToolBoxPanel)
		.hide(mLocalToolPanel)
		.commit();
	}
	
}
