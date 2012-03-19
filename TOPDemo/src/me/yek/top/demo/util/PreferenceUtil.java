package me.yek.top.demo.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * @author Longer
 *12-29 17:47:49.905: D/longer(25680): session = 610111951a72fc226160feb7b961261e80f7dcee4423498136333852
12-29 17:47:49.905: D/longer(25680): expires_in = 86400/1天
12-29 17:47:49.905: D/longer(25680): accurate_expires_time = 1325238469
12-29 17:47:49.905: D/longer(25680): refresh_token = 
12-29 17:47:49.905: D/longer(25680): re_expires_in = 15552000/180天
12-29 17:47:49.905: D/longer(25680): accurate_re_expires_time = 1340704069
 */
public class PreferenceUtil {

	public static final String PREF = "top";
	private static final String USER = "user";
	private static final String SESSION = "session";
	private static final String EXPIRES_IN = "expires_in";
	/**
	 * session key理论上的过期时间
	 */
	private static final String ACCURATE_EXPIRES_TIME = "accurate_expires_time";
	/**
	 * session key保守一点的过期时间，它比理论上的过期时间早10分钟
	 */
	private static final String SAFTY_EXPIRES_TIME = "safty_expires_time";
	
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String RE_EXPIRES_IN = "re_expires_in";
	
	private static final String ACCURATE_RE_EXPIRES_TIME = "accurate_re_expires_time"; 
	/**
	 * refresh token保守一点的过期时间，它比理论上的过期时间早10分钟
	 */
	private static final String SAFTY_RE_EXPIRES_TIME = "safty_re_expires_time";
	
	 /**
     * 初始化sharedpreference
     * @param preferences
     */
    public static void setupPreferences(SharedPreferences preferences) {
    	Editor editor = preferences.edit();
        if (!preferences.contains(USER)) {
        	editor.putString(USER, "");
        }
        if (!preferences.contains(SESSION)) {
        	editor.putString(SESSION, "");
        }
        if (!preferences.contains(EXPIRES_IN)) {
            editor.putLong(EXPIRES_IN, 0);
        }
        if (!preferences.contains(ACCURATE_EXPIRES_TIME)) {
            editor.putLong(ACCURATE_EXPIRES_TIME, 0);
        }
        if(!preferences.contains(SAFTY_EXPIRES_TIME)) {
        	editor.putLong(SAFTY_EXPIRES_TIME, 0);
        }
        if(!preferences.contains(REFRESH_TOKEN)) {
        	editor.putString(REFRESH_TOKEN, "");
        }
        if(!preferences.contains(RE_EXPIRES_IN)) {
        	editor.putLong(RE_EXPIRES_IN, 0);
        }
        if(!preferences.contains(ACCURATE_RE_EXPIRES_TIME)) {
        	editor.putLong(ACCURATE_RE_EXPIRES_TIME, 0);
        }
        if(!preferences.contains(SAFTY_RE_EXPIRES_TIME)) {
        	editor.putLong(SAFTY_RE_EXPIRES_TIME, 0);
        }
        editor.commit();
    }
	
	public static boolean setSession(SharedPreferences pref, String session) {
		Editor editor = pref.edit();
		editor.putString(SESSION, session);
		return editor.commit();
	}
	
	public static String getSession(SharedPreferences pref) {
		return pref.getString(SESSION, "");
	}
	
	public static boolean setExpiresIn(SharedPreferences pref, long expiresIn) {
		Editor editor = pref.edit();
		editor.putLong(EXPIRES_IN, expiresIn);
		updateAccurateExpiresTime(pref, expiresIn);
		updateSaftyExpiresTime(pref, expiresIn);
		return editor.commit();
	}
	
	public static long getExpiresIn(SharedPreferences pref) {
		return pref.getLong(EXPIRES_IN, 0);
	}
	
	/**
	 * 根据session key的过期时长计算过期时间点并存储
	 * @param pref
	 * @param expiresIn
	 * @return
	 */
	private static boolean updateAccurateExpiresTime(SharedPreferences pref, long expiresIn) {
		long now = System.currentTimeMillis() / 1000;
		Editor editor = pref.edit();
		editor.putLong(ACCURATE_EXPIRES_TIME, now + expiresIn);
		return editor.commit();
	}
	
	/**
	 * @param pref
	 * @return session key 过期的时间点
	 */
	public static long getAccurateExpiresTime(SharedPreferences pref) {
		return pref.getLong(ACCURATE_EXPIRES_TIME, 0);
	}
	
	private static boolean updateSaftyExpiresTime(SharedPreferences pref, long expiresIn) {
		long accurateExpiresTime = getAccurateExpiresTime(pref);
		Editor editor = pref.edit();
		editor.putLong(SAFTY_EXPIRES_TIME, accurateExpiresTime - 10 * 60);
		return editor.commit();
	}
	
	/**
	 * @param pref
	 * @return session key 过期的安全时间点
	 */
	public static long getSaftyExpiresTime(SharedPreferences pref) {
		return pref.getLong(SAFTY_EXPIRES_TIME, 0);
	}
	
	public static boolean setRefreshToken(SharedPreferences pref, String refreshToken) {
		Editor editor = pref.edit();
		editor.putString(REFRESH_TOKEN, refreshToken);
		return editor.commit();
	}
	
	public static String getRefreshToken(SharedPreferences pref) {
		return pref.getString(REFRESH_TOKEN, "");	
	}
	
	public static boolean setReExpiresIn(SharedPreferences pref, long reExpiresIn) {
		Editor editor = pref.edit();
		editor.putLong(RE_EXPIRES_IN, reExpiresIn);
		updateAccurateReExpiresTime(pref, reExpiresIn);
		updateSaftyReExpiresTime(pref, reExpiresIn);
		return editor.commit();
	}
	
	public static long getReExpiresIn(SharedPreferences pref) {
		return pref.getLong(RE_EXPIRES_IN, 0);	
	}

	private static boolean updateAccurateReExpiresTime(SharedPreferences pref,
			long reExpiresIn) {
			long now = System.currentTimeMillis() / 1000;
			Editor editor = pref.edit();
			editor.putLong(ACCURATE_RE_EXPIRES_TIME, now + reExpiresIn);
			return editor.commit();
	}
	
	public static long getAccurateReExpiresTime(SharedPreferences pref) {
		return pref.getLong(ACCURATE_RE_EXPIRES_TIME, 0);
	}
	
	private static boolean updateSaftyReExpiresTime(SharedPreferences pref,
			long reExpiresIn) {
		long accurateReExpiresTime = getAccurateReExpiresTime(pref);
		Editor editor = pref.edit();
		editor.putLong(SAFTY_RE_EXPIRES_TIME, accurateReExpiresTime - 10 * 60);
		return editor.commit();
	}
	
	public static long getSaftyReExpiresTime(SharedPreferences pref) {
		return pref.getLong(SAFTY_RE_EXPIRES_TIME, 0);
	}
	
	public static void printSharedPreference(SharedPreferences pref) {
		Log.d("longer", SESSION + " = " + getSession(pref) + "\n"
				+ EXPIRES_IN + " = " + getExpiresIn(pref) + "\n"
				+ ACCURATE_EXPIRES_TIME + " = " + getAccurateExpiresTime(pref) + "\n"
				+ REFRESH_TOKEN + " = " + getRefreshToken(pref) + "\n"
				+ RE_EXPIRES_IN + " = " + getReExpiresIn(pref) + "\n"
				+ ACCURATE_RE_EXPIRES_TIME + " = " + getAccurateReExpiresTime(pref) + "\n"
		);
	}
	
	public static boolean setUserName(SharedPreferences pref, String userName) {
		Editor editor = pref.edit();
		editor.putString(USER, userName);
		return editor.commit();
	}
	
	public static String getUserName(SharedPreferences pref) {
		return pref.getString(USER, "");
	}
	
}
