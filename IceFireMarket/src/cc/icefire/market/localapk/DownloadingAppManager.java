package cc.icefire.market.localapk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.util.ILog;
import cc.icefire.providers.DownloadManager;
import cc.icefire.providers.downloads.Downloads;

public class DownloadingAppManager {

	private Context context;
	private ConcurrentHashMap<String, Boolean> downloadingAppPool;
	private List<OnDownloadingEventListener> listeners;
	private Cursor mCursor;

	public DownloadingAppManager(Context context) {
		this.context = context;
		this.downloadingAppPool = new ConcurrentHashMap<String, Boolean>();
		this.listeners = new ArrayList<OnDownloadingEventListener>();
	}
	
	public void onAppStart() {
		long start = System.currentTimeMillis();
		DownloadManager.Query baseQuery = new DownloadManager.Query()
		.setOnlyIncludeVisibleInDownloadsUi(true);
		mCursor = IceFireApplication.sharedInstance().getDownloadManager().query(baseQuery);
		mCursor.registerContentObserver(downloadingObserver);
		fillDownloadingAppPool();
		long end = System.currentTimeMillis();
		ILog.d("Apps", "costs " + (end - start));
	}
	
	public void onAppExit() {
		if(mCursor != null) {
			mCursor.unregisterContentObserver(downloadingObserver);
			mCursor.close();
		}
	}
	
	public boolean isDownloading(String url) {
		return downloadingAppPool.containsKey(url);
	}
	
	public void registerDwnlEventListener(OnDownloadingEventListener l) {
		listeners.add(l);
	}
	
	public void unregisterDwnlEventListener(OnDownloadingEventListener l) {
		listeners.remove(l);
	}
	
	private void notifyDownloadingEventChange(String url) {
		for(OnDownloadingEventListener l : listeners) {
			l.onDownloadingChange(url);
		}
	}
	
	private void fillDownloadingAppPool() {
		if(mCursor != null && !mCursor.isClosed()) {
			downloadingAppPool.clear();
			mCursor.moveToFirst();
			while(!mCursor.isAfterLast()) {
				downloadingAppPool.put(mCursor.getString(mCursor.getColumnIndex(Downloads.COLUMN_URI)), Boolean.TRUE);
				mCursor.moveToNext();
			}
		}
	}
	
	private ContentObserver downloadingObserver = new ContentObserver(new Handler()) {
		
		@Override
		public boolean deliverSelfNotifications() {
			return super.deliverSelfNotifications();
		}

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			super.onChange(selfChange, uri);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			mCursor.requery();
			fillDownloadingAppPool();
			notifyDownloadingEventChange(null);
		}
		
	};
	
	public interface OnDownloadingEventListener {
		
		public void onDownloadingChange(String url);
		
	}
	
}
