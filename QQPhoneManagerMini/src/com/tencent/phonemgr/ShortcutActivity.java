package com.tencent.phonemgr;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;

public class ShortcutActivity extends SherlockActivity {

	private GridView shortcuts;
	private ProgressBar progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shortcut_grid);
		shortcuts = (GridView) findViewById(R.id.shortcuts);
		progress = (ProgressBar) findViewById(R.id.progress);
		new AppLoaderTask().execute();
	}
	
	private class AppLoaderTask extends AsyncTask<Void, Void, List<ResolveInfo>> {

		private List<ResolveInfo> nonSystemPackages;
		
		@Override
		protected void onPreExecute() {
			progress.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected List<ResolveInfo> doInBackground(Void... params) {
			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
	        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	        nonSystemPackages = getPackageManager().queryIntentActivities(mainIntent, 0);
			return nonSystemPackages;
		}

		@Override
		protected void onPostExecute(List<ResolveInfo> result) {
			progress.setVisibility(View.INVISIBLE);
			shortcuts.setAdapter(new AppsAdapter(getApplicationContext(), R.layout.app_item, result));
			shortcuts.setOnItemClickListener(mOnIconClickListener);
			for(ResolveInfo ri : result) {
				Log.d("longer", ri.activityInfo.applicationInfo.className + "||");
			}
		}
		
		private OnItemClickListener mOnIconClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View parent, int position,
					long id) {
				try {
					createShortCut(nonSystemPackages.get(position).activityInfo.labelRes, 
							nonSystemPackages.get(position).activityInfo.icon, 
							Class.forName(nonSystemPackages.get(position).activityInfo.packageName));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
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
            ImageView i;
            if (convertView == null) {
            	convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.app_item, null);
            }
            i = (ImageView) convertView.findViewById(R.id.app_logo);
            ResolveInfo info = nonSystemPackages.get(position);
            i.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
            return convertView;
        }

    }
	
	public void createShortCut(int appNameId, int appLogoId, Class<?> cls){
	    Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	    shortcutintent.putExtra("duplicate", false);
	    shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(appNameId));
	    Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), appLogoId);
	    shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
	    shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext() , cls));
	    sendBroadcast(shortcutintent);
	}
	
}
