package me.longerian.abcandroid.macaddress;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

public class MacAddress extends Activity {

	private String mac;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getMacString(getApplicationContext());
		Log.d(this.getClass().getName(), mac);
	}
	
	private String getMacString(Context context) {
		if(mac == null) {
			mac = "unknownMac";
			if(context != null) {
				WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = wifi.getConnectionInfo();
				mac = info.getMacAddress();
				mac = mac.replace(":", "");
			}
		}
		return mac;
	}
}
