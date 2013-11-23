package cc.icefire.market.view;

import cc.icefire.market.model.AppOrGame;
import android.content.Context;
import android.util.AttributeSet;

public class CategoryListView extends PaginationListView {

	private CategoryListAdapter adapter;
	
	public CategoryListView(Context context) {
		super(context);
	}
	
	public CategoryListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public CategoryListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void startRequest(AppOrGame type) {
		if(!hasLoadedData()) {
			adapter = new CategoryListAdapter(getContext(), type);
			setAdapter(adapter);
			adapter.setOnRecvNoDataListener(this);
			notifyHasLoadedData();
			adapter.requestCategory(1, type);
		}
	}
	
}
