package cc.icefire.market.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.R;
import cc.icefire.market.view.DownloadListView;
import cc.icefire.market.view.TabPageView;
import cc.icefire.market.view.TabPageView.OnPageSelectedListener;
import cc.icefire.market.view.TitleBar;
import cc.icefire.market.view.UserAppListView;
import cc.icefire.providers.DownloadManager;
import cc.icefire.providers.DownloadManager.Request;

public class ManagementActivity extends BaseActivity {

	private TitleBar titleBar;
	private TabPageView tabPageView;
	// private AppListView systemAppListView;
	private UserAppListView userAppListView;
	private DownloadListView downloadingAppListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		initTitleBar();
		initTabPageView();
		registerListener();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		downloadingAppListView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		downloadingAppListView.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterListener();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		downloadingAppListView.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		downloadingAppListView.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return downloadingAppListView.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return downloadingAppListView.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return downloadingAppListView.onOptionsItemSelected(item);
	}

	private void initTitleBar() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle(R.string.title_management);
	}

	private void initTabPageView() {
		tabPageView = (TabPageView) findViewById(R.id.page_view);
		// systemAppListView = new AppListView(this);
		// tabPageView.addPage(getString(R.string.system_app_list),
		// systemAppListView);
		userAppListView = new UserAppListView(this);
		tabPageView.addPage(getString(R.string.user_app_list), userAppListView);
		downloadingAppListView = new DownloadListView(this);
		tabPageView.addPage(getString(R.string.downloading_app_list),
				downloadingAppListView);
		tabPageView.setOnPageSelectedListener(onPageSelected);
		tabPageView.setPage(0);
	}

	private OnPageSelectedListener onPageSelected = new OnPageSelectedListener() {

		@Override
		public void onPageSelected(int position) {
		}
	};

	private void registerListener() {
		userAppListView.onCreate();
	}
	
	private void unregisterListener() {
		userAppListView.onDestroy();
	}
	
}
