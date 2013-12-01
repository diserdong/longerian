package cc.icefire.market.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

abstract public class LocalAppListAdapter<T> extends BaseAdapter {

	private List<T> items;
	protected Context context;
	protected LayoutInflater inflater;
	
	public LocalAppListAdapter(Context context) {
		this.context = context;
		this.items = new ArrayList<T>();
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return items != null ? items.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return items != null ? items.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addItem(T item) {
		if(items != null) {
			items.add(item);
			notifyDataSetChanged();
		}
	}
	
	public void addItems(List<T> newItems) {
		if(items != null) {
			items.addAll(newItems);
			notifyDataSetChanged();
		}
	}
	
	public void removeItem(T item) {
		if(items != null) {
			items.remove(item);
			notifyDataSetChanged();
		}
	}
	
}
