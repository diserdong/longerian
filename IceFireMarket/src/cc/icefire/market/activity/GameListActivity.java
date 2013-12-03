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

public class GameListActivity extends BaseActivity {

	private TitleBar titleBar;
	private TabPageView tabPageView;
	private AppListView popularGameListView;
	private AppListView topChartsGameListView;
	private AppListView newReleaseGameListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		initTitleBar();
		initTabPageView();
		requestApp(0);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerListener();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterListener();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void initTitleBar() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle(R.string.title_game);
	}
	
	private void initTabPageView() {
		tabPageView = (TabPageView) findViewById(R.id.page_view);
		popularGameListView = new AppListView(this);
		tabPageView.addPage(getString(R.string.app_list_popular), popularGameListView);
		topChartsGameListView = new AppListView(this);
		tabPageView.addPage(getString(R.string.app_list_top_charts), topChartsGameListView);
		newReleaseGameListView = new AppListView(this);
		tabPageView.addPage(getString(R.string.app_list_new_releases), newReleaseGameListView);
		tabPageView.setOnPageSelectedListener(onPageSelected);
	}
	
	private OnPageSelectedListener onPageSelected = new OnPageSelectedListener() {
		
		@Override
		public void onPageSelected(int position) {
			ILog.d("App", "select " + position);
			requestApp(position);
		}
	};
	
	private void requestApp(int position) {
		if(position == 0) {
			popularGameListView.requestCommonApp(AppListType.POPULAR, AppOrGame.GAME);
		} else if(position == 1) {
			topChartsGameListView.requestCommonApp(AppListType.TOP_CHARTS, AppOrGame.GAME);
		} else if(position == 2) {
			newReleaseGameListView.requestCommonApp(AppListType.NEW_RELEASES, AppOrGame.GAME);
		}
	}
	
	private void registerListener() {
		popularGameListView.onResume();
		topChartsGameListView.onResume();
		newReleaseGameListView.onResume();
	}
	
	private void unregisterListener() {
		popularGameListView.onPause();
		topChartsGameListView.onPause();
		newReleaseGameListView.onPause();
	}
	
}
