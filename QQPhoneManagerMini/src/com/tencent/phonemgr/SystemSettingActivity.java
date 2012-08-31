package com.tencent.phonemgr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

public class SystemSettingActivity extends SherlockPreferenceActivity {
	
	private String keySilent;
	private String keyAutoRotate;
	private CheckBoxPreference mSilentPreference;
	private CheckBoxPreference mAutoRotatePreference;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		keySilent = getString(R.string.key_silent);
		keyAutoRotate = getString(R.string.key_auto_rotate);
		
		mSilentPreference = (CheckBoxPreference) findPreference(keySilent);
		AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mSilentPreference.setChecked(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT);
		
		mAutoRotatePreference = (CheckBoxPreference) findPreference(keyAutoRotate);
		mAutoRotatePreference.setChecked(Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
	}

	@Override
	protected void onStart() {
		super.onStart();
		IntentFilter filter = new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION);
		registerReceiver(mAudioModeListener, filter);
	}
	
	private BroadcastReceiver mAudioModeListener = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			mSilentPreference.setChecked(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT);
		}
		
	};
	
	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(mAudioModeListener);
	}

	@Override
	@Deprecated
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		String key = preference.getKey();
		if(key.equals(keySilent)) {
			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			switch(am.getRingerMode()) {
			case AudioManager.RINGER_MODE_SILENT:
		        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		        break;
		    default:
		    	am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		    	break;
			}
		} else if(key.equals(keyAutoRotate)) {
			int flag = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
			Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, flag == 1 ? 0 : 1);
		}
		
		return true;
	}

}
