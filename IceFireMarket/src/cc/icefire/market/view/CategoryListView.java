package cc.icefire.market.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.model.Category;
import cc.icefire.market.util.ActivityUtil;
import cc.icefire.market.util.ILog;

public class CategoryListView extends NetworkListView {

	private CategoryListAdapter adapter;
	
	public CategoryListView(Context context) {
		super(context);
		init(context);
	}
	
	public CategoryListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public CategoryListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		setOnItemClickListener(onCategoryClicked);
	}
	
	public void requestCategory(AppOrGame type) {
		if(!hasLoadedData()) {
			adapter = new CategoryListAdapter(getContext(), type);
			setAdapter(adapter);
			adapter.setOnRecvNoDataListener(this);
			notifyHasLoadedData();
			adapter.requestCategory(1, type);
		}
	}
	
	private OnItemClickListener onCategoryClicked = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ILog.d(VIEW_LOG_TAG, "context " + parent.getContext());
			Category item = (Category) parent.getItemAtPosition(position);
			ActivityUtil.gotoCategoryAppListActivity(getContext(), item);
		}
	};
	
}
