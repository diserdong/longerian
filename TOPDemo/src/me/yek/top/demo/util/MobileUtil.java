package me.yek.top.demo.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class MobileUtil {
	
	/**
	 * 读取手机串号
	 */
	public static String getDeviceId(Context con) {
		TelephonyManager telephonyManager = (TelephonyManager) con
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	public static String getSIMId(Context con) {
		TelephonyManager telephonyManager = (TelephonyManager) con
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getSimSerialNumber();
	}
	
}
