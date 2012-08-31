package com.tencent.phonemgr;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class ShortcutActivity extends SherlockActivity {

	private GridView shortcuts;
	private ProgressBar progress;
	private AppLoaderTask loaderTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shortcut_grid);
		shortcuts = (GridView) findViewById(R.id.shortcuts);
		progress = (ProgressBar) findViewById(R.id.progress);
		loaderTask = new AppLoaderTask();
		loaderTask.execute();
	}
	
	
	
	@Override
	protected void onStop() {
		super.onStop();
		if(loaderTask != null) {
			loaderTask.cancel(true);
		}
	}



	private class AppLoaderTask extends AsyncTask<Void, Void, List<ResolveInfo>> {

		private List<ResolveInfo> appPackages;
		
		@Override
		protected void onPreExecute() {
			progress.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected List<ResolveInfo> doInBackground(Void... params) {
			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
	        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	        appPackages = getPackageManager().queryIntentActivities(mainIntent, 0);
			return appPackages;
		}

		@Override
		protected void onPostExecute(List<ResolveInfo> result) {
			progress.setVisibility(View.INVISIBLE);
			shortcuts.setAdapter(new AppsAdapter(getApplicationContext(), R.layout.app_item_in_grid, result));
			shortcuts.setOnItemClickListener(mOnIconClickListener);
		}
		
		private OnItemClickListener mOnIconClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View parent, int position,
					long id) {
					createShortcut(appPackages.get(position).activityInfo.packageName,
							appPackages.get(position).activityInfo.name,
							appPackages.get(position).activityInfo.applicationInfo.labelRes, 
							appPackages.get(position).activityInfo.applicationInfo.icon);
			}

		};
		
	}
	
	private class AppsAdapter extends ArrayAdapter<ResolveInfo> {
		
		private List<ResolveInfo> nonSystemPackages;
		
        public AppsAdapter(Context context, int resource,
				List<ResolveInfo> resolveInfo) {
        	super(context, resource, resolveInfo);
        	this.nonSystemPackages = resolveInfo;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	ViewHolder viewHolder;
            if (convertView == null) {
            	convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.app_item_in_grid, null);
            	viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            ResolveInfo info = nonSystemPackages.get(position);
            viewHolder.getLogo().setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
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
	
	public void createShortcut(String packageName, String clsName, int appNameId, int appLogoId){
	    Context pkgContext = null;
	    try {
			pkgContext = getApplicationContext().createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY  
			        | Context.CONTEXT_INCLUDE_CODE);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "app not found", Toast.LENGTH_SHORT).show();
			return ;
		}
	    
	    Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	    shortcutintent.putExtra("duplicate", false);
	    shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, pkgContext.getString(appNameId));
	    Parcelable icon = Intent.ShortcutIconResource.fromContext(pkgContext, appLogoId);
	    shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
	    
	    ComponentName comp = new ComponentName(packageName, clsName);
	    Intent appIntent = new Intent(new Intent(Intent.ACTION_MAIN).setComponent(comp));
	    appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	    
	    shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, appIntent);
	    sendBroadcast(shortcutintent);
	}
	
}
