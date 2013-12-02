package cc.icefire.market.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.localapk.DownloadingAppManager.OnDownloadingEventListener;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.util.ActivityUtil;
import cc.icefire.market.util.ILog;

public class AppListView extends NetworkListView implements OnDownloadingEventListener {

	private AppListAdapter adapter;
	
	public AppListView(Context context) {
		super(context);
		init(context);
	}
	
	public AppListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public AppListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		setOnItemClickListener(onAppClicked);
	}
	
	private OnItemClickListener onAppClicked = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ILog.d(VIEW_LOG_TAG, "context " + parent.getContext());
			BasicAppItem item = (BasicAppItem) parent.getItemAtPosition(position);
			ActivityUtil.gotoAppDetailActivity(getContext(), item);
		}
	};
	
	public void onCreate() {
		IceFireApplication.sharedInstance().getDownloadingAppManager().registerDwnlEventListener(this);
	}
	
	public void onDestroy() {
		IceFireApplication.sharedInstance().getDownloadingAppManager().unregisterDwnlEventListener(this);
	}
	
	public void requestCommonApp(AppListType type, AppOrGame appOrGame) {
		if(!hasLoadedData()) {
			adapter = new AppListAdapter(getContext(), type, appOrGame);
			setAdapter(adapter);
			adapter.setOnRecvNoDataListener(this);
			notifyHasLoadedData();
			adapter.requestCommonApp(1, type, appOrGame);
		}
	}
	
	public void requestSearchedApp(AppListType type, AppOrGame appOrGame, String query) {
		adapter = new AppListAdapter(getContext(), type, appOrGame, query);
		setAdapter(adapter);
		adapter.setOnRecvNoDataListener(this);
		adapter.requestSearchedApp(1, type, appOrGame, query);
	}
	
	public void requestCategoryApp(AppListType type, AppOrGame appOrGame, int categoryId) {
		if(!hasLoadedData()) {
			adapter = new AppListAdapter(getContext(), type, appOrGame, categoryId);
			setAdapter(adapter);
			adapter.setOnRecvNoDataListener(this);
			notifyHasLoadedData();
			adapter.requestCategoryApp(1, type, appOrGame, categoryId);
		}
	}

	@Override
	public void onDownloadingChange(String url) {
		if(adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

}
