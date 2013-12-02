package cc.icefire.market.localapk;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.util.ILog;

public class InstalledAppManager {

	private Context context;
	private PackageManager packageManager;
	private ConcurrentHashMap<String, BasicAppItem> userAppPool;
	private ConcurrentHashMap<String, BasicAppItem> sysAppPool;
	private List<OnAppInstallOrUninstallListener> listeners;

	public InstalledAppManager(Context context) {
		this.context = context;
		this.packageManager = context.getPackageManager();
		this.userAppPool = new ConcurrentHashMap<String, BasicAppItem>();
		this.sysAppPool = new ConcurrentHashMap<String, BasicAppItem>();
		this.listeners = new ArrayList<InstalledAppManager.OnAppInstallOrUninstallListener>();
	}

	public void loadAllInstalledApps() {
		long start = System.currentTimeMillis();
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
		for (int i = 0, size = packages.size(); i < size; i++) {
			PackageInfo packageInfo = packages.get(i);
			// Only display the non-system app info
			BasicAppItem item = buildFromPackageInfo(packageManager,
					packageInfo);
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				ILog.d("Apps", "user " + item.getPkgName());
				userAppPool.put(item.getPkgName(), item);
			} else {
				ILog.d("Apps", "sys " + item.getPkgName());
				sysAppPool.put(item.getPkgName(), item);
			}
		}
		long end = System.currentTimeMillis();
		ILog.d("Apps", "costs " + (end - start));
	}

	public void onNewAppInstalled(String pkgName) {
		String realPkg = pkgName.substring("package:".length());
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(realPkg, 0);
			BasicAppItem item = buildFromPackageInfo(packageManager,
					packageInfo);
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				userAppPool.put(item.getPkgName(), item);
			} else {
				sysAppPool.put(item.getPkgName(), item);
			}
			notifyAppInstalled(item);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void onAppUninstalled(String pkgName) {
		String realPkg = pkgName.substring("package:".length());
		BasicAppItem item = userAppPool.remove(realPkg);
		if(item != null) {
			notifyAppUninstalled(item);
		}
	}
	
	public void registerPkgEventListener(OnAppInstallOrUninstallListener l) {
		listeners.add(l);
	}
	
	public void unregisterPkgEventListener(OnAppInstallOrUninstallListener l) {
		listeners.remove(l);
	}
	
	private void notifyAppInstalled(BasicAppItem item) {
		BasicAppItem itemCopy = new BasicAppItem(item);
		for(OnAppInstallOrUninstallListener l : listeners) {
			l.onAppInstalled(itemCopy);
		}
	}
	
	private void notifyAppUninstalled(BasicAppItem item) {
		BasicAppItem itemCopy = new BasicAppItem(item);
		for(OnAppInstallOrUninstallListener l : listeners) {
			l.onAppUninstalled(itemCopy);
		}
	}
	
	private BasicAppItem buildFromPackageInfo(PackageManager packageManager,
			PackageInfo packageInfo) {
		BasicAppItem item = new BasicAppItem();
		item.setApkName(packageInfo.applicationInfo.loadLabel(packageManager)
				.toString());
		item.setPkgName(packageInfo.packageName);
		item.setVersionName(packageInfo.versionName);
		item.setVersionCode(packageInfo.versionCode);
		item.setSize(new File(packageInfo.applicationInfo.publicSourceDir).length());
		return item;
	}

	public List<BasicAppItem> getUserAppList() {
		List<BasicAppItem> userAppList = new ArrayList<BasicAppItem>();
		Iterator<Map.Entry<String, BasicAppItem>> iter = userAppPool.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, BasicAppItem> entry = (Map.Entry<String, BasicAppItem>) iter
					.next();
			userAppList.add(new BasicAppItem(entry.getValue()));
		}
		return userAppList;
	}
	
	public BasicAppItem ifInstalled(String pkgName) {
		BasicAppItem item = userAppPool.get(pkgName);
		if(item != null) {
			return new BasicAppItem(item);
		} else {
			return null;
		}
	}

	/**
	 * 在这里不直接更新userAppPool里的数据
	 * 
	 * @param path
	 */
	public void installApp(String path) {
		Uri uri = Uri.fromFile(new File(path));
		Intent installIntent = new Intent(Intent.ACTION_VIEW);
		installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
		installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(installIntent);
	}

	/**
	 * 在这里不直接更新userAppPool里的数据
	 * 
	 * @param pkgName
	 */
	public void uninstallApp(String pkgName) {
		Uri packageURI = Uri.parse("package:" + pkgName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(uninstallIntent);
	}
	
	public interface OnAppInstallOrUninstallListener {
		
		public void onAppInstalled(BasicAppItem item);
		public void onAppUninstalled(BasicAppItem item);
		
	}

}
