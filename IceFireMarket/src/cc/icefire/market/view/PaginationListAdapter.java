package cc.icefire.market.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.foound.widget.AmazingAdapter;

abstract public class PaginationListAdapter<T> extends AmazingAdapter {

	private List<T> items;
	protected Context context;
	protected LayoutInflater inflater;
	private OnRecvNoDataListener listener;
	
	public PaginationListAdapter(Context context) {
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

	@Override
	protected void bindSectionHeader(View view, int position,
			boolean displaySectionHeader) {
	}

	@Override
	public void configurePinnedHeader(View header, int position, int alpha) {
	}

	@Override
	public int getPositionForSection(int section) {
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return null;
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
	
	public void setOnRecvNoDataListener(OnRecvNoDataListener listener) {
		this.listener = listener;
	}

	public void onRequestDataFail() {
		if(listener != null) {
			if(getCount() == 0) {
				listener.onRecvNoData();
			}
		}
	}
	
	public interface OnRecvNoDataListener {
		
		public void onRecvNoData();
		
	}

}
