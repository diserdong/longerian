package me.longerian.abcandroid.apkparser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ParserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PackageParser pp = new PackageParser();
		try {
			TApplicationInfo tai = pp.parseApplicationInfo("/sdcard/MobileAssistant.apk");
			tai.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] dbl = databaseList();
		for(String d : dbl) {
			Log.d("Longer", "get database list " + d);
		}
		Log.d("Longer", "get databases path " + getDatabasePath("tests"));
	}
	
}
