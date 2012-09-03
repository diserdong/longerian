package com.tencent.phonemgr;

import java.io.File;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.tencent.phonemgr.filetype.DirFile;
import com.tencent.phonemgr.filetype.FileItem;
import com.tencent.phonemgr.filetype.FileItem.OnLoadListener;

public class FileManagerActivity extends SherlockActivity implements OnLoadListener {

	private ListView fileList;
	private FileAdapter fileAdapter;
	private ProgressBar progress;
	private TextView unavailableSdcardNote;
	private TextView fileListEmptyView;
	private FileItem currentFileItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_manager);
		fileListEmptyView = (TextView) findViewById(R.id.empty);
		fileList = (ListView) findViewById(R.id.files);
		fileAdapter = new FileAdapter(getApplicationContext(), R.layout.file_item_in_list);
		fileList.setEmptyView(fileListEmptyView);
		fileList.setAdapter(fileAdapter);
		fileList.setOnItemClickListener(mOnIconClickListener);
		progress = (ProgressBar) findViewById(R.id.progress);
		unavailableSdcardNote = (TextView) findViewById(R.id.sdcard_unavailable);
		switch2NewDirFile(new DirFile(Environment.getExternalStorageDirectory()));
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			openDir();
		} else {
			closeDir();
		}
	}
	
	private void switch2NewDirFile(DirFile dirFile) {
		if(currentFileItem != null) {
			currentFileItem.setOnLoadListener(null);
		}
		currentFileItem = dirFile;
		currentFileItem.setOnLoadListener(this);
	}
	
	private void openDir() {
		hideUnavailableSDCardNote();
		currentFileItem.open(this);
	}
	
	private void closeDir() {
		showUnavailableSDCardNote();
		currentFileItem.close();
	}
	
	private void showUnavailableSDCardNote() {
		unavailableSdcardNote.setVisibility(View.VISIBLE);
		fileList.setVisibility(View.INVISIBLE);
	}
	
	private void hideUnavailableSDCardNote() {
		unavailableSdcardNote.setVisibility(View.INVISIBLE);
		fileList.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SHARED);
		intentFilter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
		intentFilter.addDataScheme("file");
		registerReceiver(sdcardStateReceiver, intentFilter);
	}
	
	private final BroadcastReceiver sdcardStateReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
				openDir();
			}else if(intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_SHARED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_BAD_REMOVAL)) {
				closeDir();
			}
		}
	};
	
	@Override
	public void onStop() {
		super.onStop();
		unregisterReceiver(sdcardStateReceiver);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		currentFileItem.close();
	}

	private OnItemClickListener mOnIconClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View parent, int position,
				long id) {
			if(adapterView.getItemAtPosition(position) instanceof DirFile) {
				switch2NewDirFile((DirFile) adapterView.getItemAtPosition(position));
			}
			((FileItem) adapterView.getItemAtPosition(position)).open(FileManagerActivity.this); 
		}

	};
	
	private class FileAdapter extends ArrayAdapter<FileItem> {
		
		private int resource;
		
        public FileAdapter(Context context, int resource) {
        	super(context, resource);
        	this.resource = resource;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	ViewHolder viewHolder;
            if (convertView == null) {
            	convertView = LayoutInflater.from(getApplicationContext()).inflate(resource, null);
            	viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            //TODO get logo async
            viewHolder.getLogo().setImageDrawable(getItem(position).getLogo(getApplicationContext()));
            viewHolder.getName().setText(getItem(position).getName());
            return convertView;
        }

        private class ViewHolder {
			
			private View base;
			private ImageView logo;
			private TextView name;
			
			public ViewHolder(View base) {
				super();
				this.base = base;
			}
			
			public ImageView getLogo() {
				if(logo == null) {
					logo = (ImageView) base.findViewById(R.id.file_logo);
				}
				return logo;
			}
			
			public TextView getName() {
				if(name == null) {
					name = (TextView) base.findViewById(R.id.file_name);
				}
				return name;
			}
			
		}
    }

	@Override
	public void onStartLoading() {
		progress.setVisibility(View.VISIBLE);
	}

	@Override
	public void onFinishLoading(List<FileItem> fileItems) {
		progress.setVisibility(View.INVISIBLE);
		if(fileItems != null) {
			int count = fileAdapter.getCount();
			for(int i = 0; i < count; i++) {
				//release resource such as bitmap
				fileAdapter.getItem(i).close();
			}
			fileAdapter.clear();
			for(FileItem f : fileItems) {
				fileAdapter.add(f);
			}
		}
	}

	@Override
	public void onBackPressed() {
		if(currentFileItem.getName().equals(Environment.getExternalStorageDirectory().getName())) {
			super.onBackPressed();
		} else {
			switch2NewDirFile(new DirFile(currentFileItem.getParentDir()));
			openDir();
		}
	}
	
}
