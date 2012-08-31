package com.tencent.phonemgr.fragment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

import com.actionbarsherlock.app.SherlockFragment;
import com.tencent.phonemgr.R;

public class OriginalApksPanel extends SherlockFragment {

	public static OriginalApksPanel newInstance() {
		OriginalApksPanel f = new OriginalApksPanel();
		return f;
	}
	
	private ListView apksList;
	private ProgressBar progress;
	private TextView unavailableSdcardNote;
	private ApkLoaderTask loaderTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_orginal_apks, container, false);
		apksList = (ListView) view.findViewById(R.id.apks);
		progress = (ProgressBar) view.findViewById(R.id.progress);
		unavailableSdcardNote = (TextView) view.findViewById(R.id.sdcard_unavailable);
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			hideUnavailableSDCardNote();
			loaderTask = new ApkLoaderTask();
			loaderTask.execute();
		} else {
			showUnavailableSDCardNote();
		}
		return view;
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
		getActivity().registerReceiver(sdcardStateReceiver, intentFilter);
	}
	
	private final BroadcastReceiver sdcardStateReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
				android.os.Environment.getExternalStorageDirectory();
				hideUnavailableSDCardNote();
				loaderTask = new ApkLoaderTask();
				loaderTask.execute();
			}else if(intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_SHARED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_BAD_REMOVAL)) {
				showUnavailableSDCardNote();
			}
		}
	};
	
	@Override
	public void onStop() {
		super.onStop();
		getActivity().unregisterReceiver(sdcardStateReceiver);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(loaderTask != null) {
			loaderTask.cancel(true);
		}
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
	
	private void showUnavailableSDCardNote() {
		unavailableSdcardNote.setVisibility(View.VISIBLE);
		apksList.setVisibility(View.INVISIBLE);
	}
	
	private void hideUnavailableSDCardNote() {
		unavailableSdcardNote.setVisibility(View.INVISIBLE);
		apksList.setVisibility(View.VISIBLE);
	}
	
	private class ApkLoaderTask extends AsyncTask<Void, Void, List<File>> {

		private List<File> apkFileList = new ArrayList<File>();
		
		@Override
		protected void onPreExecute() {
			progress.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected List<File> doInBackground(Void... params) {
			searchApks(apkFileList, Environment.getExternalStorageDirectory());
			return apkFileList;
		}

		@Override
		protected void onPostExecute(List<File> result) {
			progress.setVisibility(View.INVISIBLE);
			apksList.setAdapter(new ApkAdapter(getActivity(), R.layout.app_item_in_list, result));
			apksList.setOnItemClickListener(onAppClickListener);
		}
		
		private OnItemClickListener onAppClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View parent, int position,
					long id) {
				installApplication(apkFileList.get(position));
			}

		};
		
		private void installApplication(File apkFile) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
			startActivity(intent);
		}
		
	}
	
	//FIXME it costs memory
	private void searchApks(List<File> apkFileList, File dir) {
		if(dir.isDirectory()) {
			File[] apks = dir.listFiles(new ApkFilter());
			if (apks != null && apks.length > 0) {
			    for (File file : apks) {
			    	apkFileList.add(file);
			    }
			}
			File[] allFiles = dir.listFiles();
			if(allFiles != null && allFiles.length > 0) {
				for(File file : allFiles) {
					searchApks(apkFileList, file);
				}
			}
		}
	}
	
	class ApkFilter implements FilenameFilter {
	    
		public boolean accept(File dir, String name) {
	        return (name.endsWith(".apk"));
	    }
	}
	
//	08-31 19:28:40.647: W/PackageParser(12913): Unable to read AndroidManifest.xml of /mnt/sdcard/.medieval_software/.BlueFTP_temp/blueftp_view_960036825.apk
//	08-31 19:28:40.647: W/PackageParser(12913): java.io.FileNotFoundException: AndroidManifest.xml
	private Drawable getApkIcon(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
                Log.e("ApkIconLoader", e.toString());
            }
        }
        return null;
    }
	
	private class ApkAdapter extends ArrayAdapter<File> {

		private List<File> items;
		private int resource;
		
		public ApkAdapter(Context context, int resource,
				List<File> items) {
			super(context, resource, items);
			this.resource = resource;
			this.items = items;
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
			viewHolder.getLabel().setText(items.get(position).getName());
			Drawable icon = getApkIcon(getActivity(), items.get(position).getAbsolutePath());
			if(icon != null) {
				viewHolder.getLogo().setImageDrawable(icon);
			}
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
					label = (TextView) base.findViewById(R.id.app_name);
				}
				return label;
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
