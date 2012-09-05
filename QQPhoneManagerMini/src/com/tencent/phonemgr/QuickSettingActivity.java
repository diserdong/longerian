package com.tencent.phonemgr;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

public class QuickSettingActivity extends SherlockPreferenceActivity {
	
	private String keyDebugOverUsb;
	private String keyUnknownSource;
	private String keyUsbSetting; 
	
	private String keyDateAndTime;
	private String keyDisplay;
	private String keySound;
	
	private Preference mDebugOverUsbPreference;
	private Preference mUnknownSource;
	private Preference mUsbSettingPreference;
	
	private Preference mDateAndTimePreference;
	private Preference mDisplayPreference;
	private Preference mSoundPreference;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.quick_setting_preferences);
		
		keyDebugOverUsb = getString(R.string.key_debug_over_usb);
		keyUnknownSource = getString(R.string.key_unknown_source);
		keyUsbSetting = getString(R.string.key_usb_setting);
		
		keyDateAndTime = getString(R.string.key_date_and_time);
		keyDisplay = getString(R.string.key_display);
		keySound = getString(R.string.key_sound);

		mDateAndTimePreference = findPreference(keyDateAndTime);
		mDisplayPreference = findPreference(keyDisplay);
		mSoundPreference = findPreference(keySound);
		
		mDebugOverUsbPreference = findPreference(keyDebugOverUsb);
		mUnknownSource = findPreference(keyUnknownSource);
		mUsbSettingPreference = findPreference(keyUsbSetting);
	}
	
	@Override
	@Deprecated
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		String key = preference.getKey();
		if(key.equals(keyDebugOverUsb)) {
			openSystemSetting("com.android.settings", "com.android.settings.DevelopmentSettings");
		} else if(key.equals(keyUnknownSource)) {
			try {
				if(Class.forName("com.android.settings.ApplicationSettings") != null) {
					//FIXME crash on 4.1, use sdk version to check it
					openSystemSetting("com.android.settings", "com.android.settings.ApplicationSettings");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if(key.equals(keyUsbSetting)) {
			openSystemSetting("com.android.settings", "com.android.settings.DevelopmentSettings");
		} else if(key.equals(keyDateAndTime)) {
			try {
				if(Class.forName("com.android.settings.DateTimeSettings") != null) {
					//FIXME crash on 4.1, use sdk version to check it
					openSystemSetting("com.android.settings", "com.android.settings.DateTimeSettings");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if(key.equals(keyDisplay)) {
			openSystemSetting("com.android.settings", "com.android.settings.DisplaySettings");
		} else if(key.equals(keySound)) {
			openSystemSetting("com.android.settings", "com.android.settings.SoundSettings");
		}
		return true;
	}
	
	private void openSystemSetting(String packageName, String className) {
		Intent intent = new Intent();  
		ComponentName cm = new ComponentName(packageName, className);  
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");  
		startActivity(intent);
	}
	
	private void toggleUnknownSourceInstallation() {
		int flag = Settings.Secure.getInt(getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0);
		Settings.Secure.putInt(getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, flag == 1 ? 0 : 1);
	}
	
}
