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

public class IndexActivity extends BaseActivity {

	private TitleBar titleBar;
	private TabPageView tabPageView;
	private AppListView selectedAppListView;
	private AppListView recommendedAppListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		initTitleBar();
		initTabPageView();
		requestApp(0);
	}
	
	private void initTitleBar() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle(R.string.title_index);
	}
	
	private void initTabPageView() {
		tabPageView = (TabPageView) findViewById(R.id.page_view);
		selectedAppListView = new AppListView(getApplicationContext());
		tabPageView.addPage(getString(R.string.app_list_selected), selectedAppListView);
		recommendedAppListView = new AppListView(getApplicationContext());
		tabPageView.addPage(getString(R.string.app_list_recommended), recommendedAppListView);
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
			selectedAppListView.requestCommonApp(AppListType.SELECTED, AppOrGame.ANY);
		} else if(position == 1) {
			recommendedAppListView.requestCommonApp(AppListType.RECOMMENDEDED, AppOrGame.ANY);
		}
	}
	
}
