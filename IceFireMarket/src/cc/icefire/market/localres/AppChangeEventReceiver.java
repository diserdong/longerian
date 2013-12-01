package cc.icefire.market.localres;

import cc.icefire.market.IceFireApplication;
import cc.icefire.market.util.ILog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppChangeEventReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent ) {
		if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            String packageName = intent.getDataString();
            ILog.d("AppChange", "install " + packageName);
            IceFireApplication.sharedInstance().getInstalledAppManager().onNewAppInstalled(packageName);
        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
        	String packageName = intent.getDataString();
        	ILog.d("AppChange", "uninstall " + packageName);
        	IceFireApplication.sharedInstance().getInstalledAppManager().onAppUninstalled(packageName);
        }  
	}
}
