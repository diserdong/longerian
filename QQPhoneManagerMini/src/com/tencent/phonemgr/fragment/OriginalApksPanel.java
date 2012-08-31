package com.tencent.phonemgr.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.tencent.phonemgr.R;
import com.tencent.phonemgr.entry.AppManager;
import com.tencent.phonemgr.entry.Entry;
import com.tencent.phonemgr.entry.FileManager;
import com.tencent.phonemgr.entry.Installer;
import com.tencent.phonemgr.entry.QuickSetting;
import com.tencent.phonemgr.entry.ShortcutManager;
import com.tencent.phonemgr.entry.SystemSetting;

public class OriginalApksPanel extends SherlockFragment {

	public static OriginalApksPanel newInstance() {
		OriginalApksPanel f = new OriginalApksPanel();
		return f;
	}
	
	private GridView mLocalToolEntries;
	private List<Entry> mEntries = new ArrayList<Entry>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mEntries.add(new FileManager());
		mEntries.add(new AppManager());
		mEntries.add(new QuickSetting());
		mEntries.add(new Installer());
		mEntries.add(new ShortcutManager());
		mEntries.add(new SystemSetting());
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_local_tool, container, false);
		mLocalToolEntries = (GridView) view.findViewById(R.id.local_tool_entrys);
		mLocalToolEntries.setAdapter(new LocalToolEntryAdapter(getActivity(), R.layout.entry_item, R.id.label, mEntries));
		mLocalToolEntries.setOnItemClickListener(mOnEntryClickListener);
		return view;
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
	
	private OnItemClickListener mOnEntryClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View parent, int position,
				long id) {
			mEntries.get(position).doAction(getSherlockActivity());
		}
	};

	
	private class LocalToolEntryAdapter extends ArrayAdapter<Entry> {

		private List<Entry> entries;
		private int resource;
		
		public LocalToolEntryAdapter(Context context, int resource,
				int textViewResourceId, List<Entry> entries) {
			super(context, resource, textViewResourceId, entries);
			this.resource = resource;
			this.entries = entries;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(resource, null);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			}
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.getLabel().setText(entries.get(position).getLabelId());
			viewHolder.getLogo().setImageResource(entries.get(position).getLogoId());
			return convertView;
		}
		
		private class ViewHolder {
			
			private View base;
			private ImageView logo;
			private TextView label;
			
			public ViewHolder(View base) {
				super();
				this.base = base;
			}
			
			public TextView getLabel() {
				if(label == null) {
					label = (TextView) base.findViewById(R.id.label);
				}
				return label;
			}

			public ImageView getLogo() {
				if(logo == null) {
					logo = (ImageView) base.findViewById(R.id.logo);
				}
				return logo;
			}
			
		}
		
	}
	
	
}
