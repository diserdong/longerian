package com.tencent.phonemgr;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

public class SystemSettingActivity extends SherlockPreferenceActivity {
	
	private String keyAirplaneMode;
	private String keyWifi;
	private String keyBluetooth; 
	private String keyMobileNetwork;
	private String keySilent;
	private String keyAutoRotate;
	
	private CheckBoxPreference mAirPlaneModePreference;
	private CheckBoxPreference mWifiPreference;
	private CheckBoxPreference mBluetoothPreference;
	private Preference mMobileNetworkPreference;
	private CheckBoxPreference mSilentPreference;
	private CheckBoxPreference mAutoRotatePreference;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		keyAirplaneMode = getString(R.string.key_airplane_mode);
		keyWifi = getString(R.string.key_wifi);
		keyBluetooth = getString(R.string.key_bluetooth);
		keyMobileNetwork = getString(R.string.key_mobile_network);
		keySilent = getString(R.string.key_silent);
		keyAutoRotate = getString(R.string.key_auto_rotate);
		
		mAirPlaneModePreference = (CheckBoxPreference) findPreference(keyAirplaneMode);
		mAirPlaneModePreference.setChecked(Settings.System.getInt(getContentResolver(),	Settings.System.AIRPLANE_MODE_ON, 0) == 1);
		
		mWifiPreference = (CheckBoxPreference) findPreference(keyWifi);
		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		mWifiPreference.setChecked(wm.isWifiEnabled());
		
		//TODO listen the bluetooth state
		mBluetoothPreference = (CheckBoxPreference) findPreference(keyBluetooth);
		mBluetoothPreference.setChecked(BluetoothAdapter.getDefaultAdapter().isEnabled());
		
		mMobileNetworkPreference = findPreference(keyMobileNetwork);
		
		mSilentPreference = (CheckBoxPreference) findPreference(keySilent);
		AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mSilentPreference.setChecked(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT);
		
		mAutoRotatePreference = (CheckBoxPreference) findPreference(keyAutoRotate);
		mAutoRotatePreference.setChecked(Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
	}

	@Override
	protected void onStart() {
		super.onStart();
		registerReceiver(mAudioModeChangedReceiver, new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION));
		registerReceiver(mWifiStateChangedReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
	}
	
	private BroadcastReceiver mAudioModeChangedReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			mSilentPreference.setChecked(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT);
		}
		
	};
	
	private BroadcastReceiver mWifiStateChangedReceiver = new BroadcastReceiver() {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	        mWifiPreference.setChecked(wm.isWifiEnabled());
	    }
	};
	
	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(mAudioModeChangedReceiver);
		unregisterReceiver(mWifiStateChangedReceiver);
	}

	@Override
	@Deprecated
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		String key = preference.getKey();
		if(key.equals(keyAirplaneMode)) {
			toggleAirplaneMode();
		} else if(key.equals(keyWifi)) {
			toggleWifi();
		} else if(key.equals(keyBluetooth)) {
			toggleBluetooth();
		} else if(key.equals(keyMobileNetwork)) {
			toggleMobileNetwork();
		} else if(key.equals(keySilent)) {
			toggleRingerMode();
		} else if(key.equals(keyAutoRotate)) {
			toggleAutoRotate();
		}
		return true;
	}
	
	private void toggleAirplaneMode() {
		boolean isEnabled = Settings.System.getInt(
				getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1;

		Settings.System.putInt(
		      getContentResolver(),
		      Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);

		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", !isEnabled);
		sendBroadcast(intent);
	}
	
	private void toggleWifi() {
		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wm.setWifiEnabled(!wm.isWifiEnabled());
	}
	
	private void toggleBluetooth() {
		if(BluetoothAdapter.getDefaultAdapter().isEnabled()) {
			BluetoothAdapter.getDefaultAdapter().disable();
		} else {
			BluetoothAdapter.getDefaultAdapter().enable();
		}
	}
	
	private void toggleMobileNetwork() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setClassName("com.android.phone", "com.android.phone.Settings");
		startActivity(intent);
	}
	
	private void toggleRingerMode() {
		AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		switch(am.getRingerMode()) {
		case AudioManager.RINGER_MODE_SILENT:
	        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	        break;
	    default:
	    	am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	    	break;
		}
	}
	
	private void toggleAutoRotate() {
		int flag = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
		Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, flag == 1 ? 0 : 1);
	}
	
}
