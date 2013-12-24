package cc.icefire.market.activity;

import org.json.JSONObject;


import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import cc.icefire.market.R;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.util.ILog;
import cc.icefire.market.view.AppListView;
import cc.icefire.market.view.SmoothGallery;
import cc.icefire.market.view.TabPageView;
import cc.icefire.market.view.TabPageView.OnPageSelectedListener;
import cc.icefire.market.view.TitleBar;

public class AppListActivity extends BaseActivity {

	private TitleBar titleBar;
	private TabPageView tabPageView;
	private AppListView popularAppListView;
	private AppListView topChartsAppListView;
	private AppListView newReleaseAppListView;
	
	
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
		titleBar.setTitle(R.string.title_app);
	}

	private void initTabPageView() {
		tabPageView = (TabPageView) findViewById(R.id.page_view);
		popularAppListView = new AppListView(this);
		tabPageView.addPage(getString(R.string.app_list_popular), popularAppListView);
		topChartsAppListView = new AppListView(this);
		tabPageView.addPage(getString(R.string.app_list_top_charts), topChartsAppListView);
		newReleaseAppListView = new AppListView(this);
		tabPageView.addPage(getString(R.string.app_list_new_releases), newReleaseAppListView);
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
		if (position == 0) {
			popularAppListView.requestCommonApp(AppListType.POPULAR, AppOrGame.APP);
		} else if (position == 1) {
			topChartsAppListView.requestCommonApp(AppListType.TOP_CHARTS, AppOrGame.APP);
		} else if (position == 2) {
			newReleaseAppListView.requestCommonApp(AppListType.NEW_RELEASES, AppOrGame.APP);
		}
	}

	private void registerListener() {
		popularAppListView.onResume();
		topChartsAppListView.onResume();
		newReleaseAppListView.onResume();
	}

	private void unregisterListener() {
		popularAppListView.onPause();
		topChartsAppListView.onPause();
		newReleaseAppListView.onPause();
	}

}
