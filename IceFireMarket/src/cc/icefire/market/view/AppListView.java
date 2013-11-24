package cc.icefire.market.view;

import android.content.Context;
import android.util.AttributeSet;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;

public class AppListView extends NetworkListView {

	private AppListAdapter adapter;
	
	public AppListView(Context context) {
		super(context);
	}
	
	public AppListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public AppListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
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

}
