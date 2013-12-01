package cc.icefire.market.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.icefire.market.R;

/**
 * 加载本地数据的Listview， 不需要网络交互
 */
public class LocalListView extends RelativeLayout {

	private ListView list;
	private TextView empty;

	public LocalListView(Context context) {
		super(context);
		init(context);
	}

	public LocalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LocalListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.widget_local_list, this);
		list = (ListView) findViewById(R.id.list);
		empty = (TextView) findViewById(R.id.empty);
		list.setEmptyView(empty);
	}
	
	public void setAdapter(ListAdapter adapter) {
		if(list != null) {
			list.setAdapter(adapter);
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
	
}
