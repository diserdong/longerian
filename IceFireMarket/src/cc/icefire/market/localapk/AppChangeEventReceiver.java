package cc.icefire.market.localapk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.util.ILog;

public class AppChangeEventReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent ) {
		if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            String packageName = intent.getDataString();
            ILog.d("AppChange", "install " + packageName);
            IceFireApplication.getInstance().getInstalledAppManager().onNewAppInstalled(packageName);
        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
        	String packageName = intent.getDataString();
        	ILog.d("AppChange", "uninstall " + packageName);
        	IceFireApplication.getInstance().getInstalledAppManager().onAppUninstalled(packageName);
        }  
	}
}
