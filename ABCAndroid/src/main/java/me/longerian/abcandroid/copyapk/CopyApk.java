package me.longerian.abcandroid.copyapk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class CopyApk extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PackageManager pm = getPackageManager();
		List<ApplicationInfo> apps = pm.getInstalledApplications(0);
		for(ApplicationInfo ai : apps) {
			Log.d(this.getClass().getName(), ai.packageName);
			Log.d(this.getClass().getName(), ai.sourceDir);
			if((ai.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				File in = new File(ai.sourceDir);
				File out = new File("/mnt/sdcard/" + ai.packageName + ".apk");
				try {
					out.createNewFile();
					FileInputStream fis = new FileInputStream(in);
					FileOutputStream fos = new FileOutputStream(out);
					int count;
					byte[] buffer = new byte[256 * 1024];
					while ((count = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, count);
					}
					fis.close();
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
		try {
			Log.d(this.getClass().getName(), Environment.getExternalStorageDirectory().getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
