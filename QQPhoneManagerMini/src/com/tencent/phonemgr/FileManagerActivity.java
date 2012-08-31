package com.tencent.phonemgr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
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
import com.tencent.phonemgr.filetype.FileItem;

public class FileManagerActivity extends SherlockActivity {

	private ListView fileList;
	private FileAdapter fileAdapter;
	private ProgressBar progress;
	private TextView unavailableSdcardNote;
	private FileLoaderTask loaderTask;
	private File currentDir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shortcut_grid);
		fileList = (ListView) findViewById(R.id.files);
		progress = (ProgressBar) findViewById(R.id.progress);
		unavailableSdcardNote = (TextView) findViewById(R.id.sdcard_unavailable);
		currentDir = Environment.getExternalStorageDirectory();
		loaderTask = new FileLoaderTask();
		loaderTask.execute(currentDir);
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
				android.os.Environment.getExternalStorageDirectory();
				hideUnavailableSDCardNote();
				loaderTask = new FileLoaderTask();
				loaderTask.execute();
			}else if(intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_SHARED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_BAD_REMOVAL)) {
				showUnavailableSDCardNote();
				if(loaderTask != null) {
					loaderTask.cancel(true);
				}
			}
		}
	};
	
	@Override
	public void onStop() {
		super.onStop();
		unregisterReceiver(sdcardStateReceiver);
		if(loaderTask != null) {
			loaderTask.cancel(true);
		}
	}
	
	private void showUnavailableSDCardNote() {
		unavailableSdcardNote.setVisibility(View.VISIBLE);
		fileList.setVisibility(View.INVISIBLE);
	}
	
	private void hideUnavailableSDCardNote() {
		unavailableSdcardNote.setVisibility(View.INVISIBLE);
		fileList.setVisibility(View.VISIBLE);
	}
	
	private class FileLoaderTask extends AsyncTask<File, Void, List<FileItem>> {

		private List<FileItem> fileItems = new ArrayList<FileItem>();
		
		@Override
		protected void onPreExecute() {
			progress.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected List<FileItem> doInBackground(File... params) {
			
			
			return fileItems;
		}

		@Override
		protected void onPostExecute(List<FileItem> result) {
			progress.setVisibility(View.INVISIBLE);
			fileList.setAdapter(new FileAdapter(getApplicationContext(), R.layout.app_item_in_grid, result));
			fileList.setOnItemClickListener(mOnIconClickListener);
		}
		
		private OnItemClickListener mOnIconClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View parent, int position,
					long id) {
				fileItems.get(position).open(getApplicationContext());
			}

		};
		
	}
	
	private class FileAdapter extends ArrayAdapter<FileItem> {
		
		private List<FileItem> files;
		private int resource;
		
        public FileAdapter(Context context, int resource,
				List<FileItem> files) {
        	super(context, resource, files);
        	this.resource = resource;
        	this.files = files;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	ViewHolder viewHolder;
            if (convertView == null) {
            	convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.app_item_in_grid, null);
            	viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
//            ResolveInfo info = files.get(position);
//            viewHolder.getLogo().setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
            return convertView;
        }

        private class ViewHolder {
			
			private View base;
			private ImageView logo;
			
			public ViewHolder(View base) {
				super();
				this.base = base;
			}
			
			public ImageView getLogo() {
				if(logo == null) {
					logo = (ImageView) base.findViewById(R.id.app_logo);
				}
				return logo;
			}
			
		}
    }
	
}
