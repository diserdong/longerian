package cc.icefire.market.activity;

import android.os.Bundle;
import cc.icefire.market.R;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.util.ILog;
import cc.icefire.market.view.AppListView;
import cc.icefire.market.view.TabPageView;
import cc.icefire.market.view.TabPageView.OnPageSelectedListener;
import cc.icefire.market.view.TitleBar;
import cc.icefire.market.view.UserAppListView;

public class ManagementActivity extends BaseActivity {

	private TitleBar titleBar;
	private TabPageView tabPageView;
//	private AppListView systemAppListView;
	private UserAppListView userAppListView;
	private UserAppListView downloadingAppListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		initTitleBar();
		initTabPageView();
		loadApp(0);
		userAppListView.onCreate();
		downloadingAppListView.onCreate();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		userAppListView.onDestroy();
		downloadingAppListView.onDestroy();
	}

	private void initTitleBar() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle(R.string.title_management);
	}
	
	private void initTabPageView() {
		tabPageView = (TabPageView) findViewById(R.id.page_view);
//		systemAppListView = new AppListView(this);
//		tabPageView.addPage(getString(R.string.system_app_list), systemAppListView);
		userAppListView = new UserAppListView(this);
		tabPageView.addPage(getString(R.string.user_app_list), userAppListView);
		downloadingAppListView = new UserAppListView(this);
		tabPageView.addPage(getString(R.string.downloading_app_list), downloadingAppListView);
		tabPageView.setOnPageSelectedListener(onPageSelected);
	}
	
	private OnPageSelectedListener onPageSelected = new OnPageSelectedListener() {
		
		@Override
		public void onPageSelected(int position) {
			ILog.d("App", "select " + position);
			loadApp(position);
		}
	};
	
	private void loadApp(int position) {
		if(position == 0) {
			userAppListView.loadApp();
		} else if(position == 1) {
			downloadingAppListView.loadApp();
		}
	}
	
}
