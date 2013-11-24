package cc.icefire.market.view;

import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;
import android.content.Context;
import android.util.AttributeSet;

public class SearchedAppListView extends NetworkListView {

	private AppListAdapter adapter;
	
	public SearchedAppListView(Context context) {
		super(context);
		init(context);
	}
	
	public SearchedAppListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public SearchedAppListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
	}
	
	public void requestSearchedApp(AppListType type, AppOrGame appOrGame, String query) {
		adapter = new AppListAdapter(getContext(), type, appOrGame, query);
		setAdapter(adapter);
		adapter.setOnRecvNoDataListener(this);
		adapter.requestSearchedApp(1, type, appOrGame, query);
	}
	
}
