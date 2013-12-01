package cc.icefire.market.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.icefire.market.R;
import cc.icefire.market.view.PaginationListAdapter.OnApiCallbackListener;

public class NetworkListView extends RelativeLayout implements
		OnApiCallbackListener {

	private PaginationListView list;
	private ProgressBar progress;
	private TextView empty;

	public NetworkListView(Context context) {
		super(context);
		init(context);
	}

	public NetworkListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public NetworkListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.widget_network_list, this);
		list = (PaginationListView) findViewById(R.id.list);
		progress = (ProgressBar) findViewById(R.id.progress);
		empty = (TextView) findViewById(R.id.empty);
		list.setEmptyView(empty);
	}

	public void setAdapter(ListAdapter adapter) {
		if(list != null) {
			list.setAdapter(adapter);
		}
	}

	public boolean hasLoadedData() {
		return list != null ? list.hasLoadedData() : false;
	}

	public void notifyHasLoadedData() {
		if(list != null) {
			list.notifyHasLoadedData();
		}
	}

	public void setEmptyViewText(int resId) {
		if(empty != null) {
			empty.setText(resId);
		}
	}
	
	public void setEmptyViewText(String resStr) {
		if(empty != null) {
			empty.setText(resStr);
		}
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		if(list != null) {
			list.setOnItemClickListener(listener);
		}
	}
	
	@Override
	public void onRecvNoData() {
		if(progress != null) {
			progress.setVisibility(View.INVISIBLE);
		}
		if(empty != null) {
			empty.setVisibility(View.VISIBLE);
		}
		if(list != null) {
			list.clearHasLoadedData();
		}
	}

	@Override
	public void onRequestFirstPage() {
		if(progress != null) {
			progress.setVisibility(View.VISIBLE);
		}
		if(empty != null) {
			empty.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onRecvDataSuccess() {
		if(progress != null) {
			progress.setVisibility(View.INVISIBLE);
		}
		if(empty != null) {
			empty.setVisibility(View.VISIBLE);
		}
	}

}
