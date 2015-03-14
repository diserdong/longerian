package me.longerian.abcandroid.stickyheadgrid.sample;

import java.util.Arrays;
import java.util.List;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.stickyheadgrid.lib.StickyGridHeadersSimpleAdapter;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StickyGridHeaderAppsArrayAdapter extends BaseAdapter implements
		StickyGridHeadersSimpleAdapter {

	private int mHeaderResId;
    private LayoutInflater mInflater;
    private int mItemResId;
    private List<Apks> mItems;
    private SparseBooleanArray headerMap;

    public StickyGridHeaderAppsArrayAdapter(Context context, List<Apks> items) {
        init(context, items);
    }

    public StickyGridHeaderAppsArrayAdapter(Context context, Apks[] items) {
        init(context, Arrays.asList(items));
    }
    
    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }
    
    public int getHeaderCount() {
        return headerMap.size();
    }
    
    public int getRow() {
    	if(getCount() % 4 == 0) {
    		return getCount() / 4;
    	} else {
    		return getCount() / 4 + 1;
    	}
    	
    }
    
	@Override
	public Apks getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	@SuppressWarnings("unchecked")
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
	        if (convertView == null) {
	            convertView = mInflater.inflate(mItemResId, parent, false);
	            holder = new ViewHolder();
	            holder.textView = (TextView) convertView.findViewById(R.id.app_name);
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }

	        Apks item = getItem(position);
	        holder.textView.setText(item.getName());
	        return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		Apks item = getItem(position);
        return item.getType();
	}

	@Override
	@SuppressWarnings("unchecked")
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(mHeaderResId, parent, false);
            holder = new HeaderViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        Apks item = getItem(position);
        // set header text as first char in string
        holder.textView.setText(String.valueOf(item.getType()));

        return convertView;
	}

	private void init(Context context, List<Apks> items) {
        this.mItems = items;
        this.mHeaderResId = R.layout.item_grid_header;
        this.mItemResId = R.layout.item_grid_app;
        mInflater = LayoutInflater.from(context);
        this.headerMap = new SparseBooleanArray();
        for (int i = 0, size = getCount(); i < size; i++) {
            long headerId = getHeaderId(i);
            headerMap.put((int) headerId, true);
        }
    }

    protected class HeaderViewHolder {
        public TextView textView;
    }

    protected class ViewHolder {
        public TextView textView;
    }
    
}
