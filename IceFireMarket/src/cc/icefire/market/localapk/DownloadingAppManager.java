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
import cc.icefire.market.model.BasicDownloadInfo;
import cc.icefire.market.util.ILog;
import cc.icefire.providers.DownloadManager;
import cc.icefire.providers.downloads.Downloads;

public class DownloadingAppManager {

	private Context context;
	private ConcurrentHashMap<String, BasicDownloadInfo> downloadingAppPool;
	private List<OnDownloadingEventListener> listeners;
	private Cursor mCursor;

	public DownloadingAppManager(Context context) {
		this.context = context;
		this.downloadingAppPool = new ConcurrentHashMap<String, BasicDownloadInfo>();
		this.listeners = new ArrayList<OnDownloadingEventListener>();
	}
	
	public void onAppStart() {
		new Thread() {
			public void run() {
				long start = System.currentTimeMillis();
				DownloadManager.Query baseQuery = new DownloadManager.Query()
				.setOnlyIncludeVisibleInDownloadsUi(true);
				mCursor = IceFireApplication.sharedInstance().getDownloadManager().query(baseQuery);
				mCursor.registerContentObserver(downloadingObserver);
				fillDownloadingAppPool();
				long end = System.currentTimeMillis();
				ILog.d("Apps", "costs " + (end - start));
			};
		}.start();
	}
	
	public void onAppExit() {
		if(mCursor != null) {
			mCursor.unregisterContentObserver(downloadingObserver);
			mCursor.close();
		}
	}
	
	public BasicDownloadInfo isDownloading(String url) {
		BasicDownloadInfo item = downloadingAppPool.get(url);
		if(item != null) {
			return new BasicDownloadInfo(item);
		} else {
			return null;
		}
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
				BasicDownloadInfo info = new BasicDownloadInfo();
				info.setId(mCursor.getLong(mCursor.getColumnIndex(DownloadManager.COLUMN_ID)));
				info.setUri(mCursor.getString(mCursor.getColumnIndex(DownloadManager.COLUMN_URI)));
				info.setStatus(mCursor.getInt(mCursor.getColumnIndex(DownloadManager.COLUMN_STATUS)));
				info.setDestination(mCursor.getString(mCursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI)));
				downloadingAppPool.put(info.getUri(), info);
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
