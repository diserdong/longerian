package com.tencent.phonemgr.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.tencent.phonemgr.utils.apk.ApkUtils;

public class DownloadedAppsPanel extends SherlockFragment {

	public static DownloadedAppsPanel newInstance() {
		DownloadedAppsPanel f = new DownloadedAppsPanel();
		return f;
	}
	
	private ListView appsList;
	private ProgressBar progress;
	private AppLoaderTask loaderTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_downloaded_apps, container, false);
		appsList = (ListView) view.findViewById(R.id.apps);
		progress = (ProgressBar) view.findViewById(R.id.progress);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loaderTask = new AppLoaderTask();
		loaderTask.execute();
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
	
	private class AppLoaderTask extends AsyncTask<Void, Void, List<PackageInfo>> {

		private List<PackageInfo> appPackages = new ArrayList<PackageInfo>();
		
		@Override
		protected void onPreExecute() {
			progress.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected List<PackageInfo> doInBackground(Void... params) {
			PackageManager pm = getActivity().getPackageManager();
			List<PackageInfo> packs = pm.getInstalledPackages(0);
			for (PackageInfo pi : packs) {
				if((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
					appPackages.add(pi);
				}
			}
			return appPackages;
		}

		@Override
		protected void onPostExecute(List<PackageInfo> result) {
			progress.setVisibility(View.INVISIBLE);
			appsList.setAdapter(new AppAdapter(getActivity(), R.layout.app_item_in_list, result));
			appsList.setOnItemClickListener(onAppClickListener);
		}
		
		private OnItemClickListener onAppClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View parent, int position,
					long id) {
				ApkUtils.uninstallApk(getActivity(), appPackages.get(position).packageName);
			}

		};
		
	}
	
	private class AppAdapter extends ArrayAdapter<PackageInfo> {

		private List<PackageInfo> items;
		private int resource;
		private PackageManager pm = getActivity().getPackageManager();
		
		public AppAdapter(Context context, int resource,
				List<PackageInfo> entries) {
			super(context, resource, entries);
			this.resource = resource;
			this.items = entries;
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
			viewHolder.getLabel().setText(items.get(position).applicationInfo.loadLabel(pm));
			viewHolder.getLogo().setImageDrawable(items.get(position).applicationInfo.loadIcon(pm));
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
