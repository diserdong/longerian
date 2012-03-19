package me.yek.top.demo.util;

import android.util.Log;

public class LogUtil {

	private static final boolean DEBUG = true;
	private static final String TAG = "LogUtil";
	
	public static int d(String tag, String msg) {
		if(tag == null || msg == null) {
			Log.d(TAG, "your tag is <" + tag + "> and msg is <" + msg + ">");
			return 0;
		}
		if(DEBUG) {
			return Log.d(tag, msg);
		} else {
			return 0;
		}
	}
	
}
