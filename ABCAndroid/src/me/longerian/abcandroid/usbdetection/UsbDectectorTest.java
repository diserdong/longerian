package me.longerian.abcandroid.usbdetection;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class UsbDectectorTest extends Activity {

	private UsbDetector mUsbDector = new UsbDetector();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView hello = (TextView) findViewById(R.id.hello);
        hello.setText(Build.MODEL);
        Log.d("UsbDectector", Build.MODEL);
        
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
      	  WifiInfo wifiInfo = wifi.getConnectionInfo();
      	  if(wifiInfo != null) {
      		  String ssid = wifiInfo.getBSSID();
      		  int ip = wifiInfo.getIpAddress(); 
      		  String mac = wifiInfo.getMacAddress();
      		  Log.d("UsbDectector", ssid);
      		  Log.d("UsbDectector", ip + "");
      		  Log.d("UsbDectector", mac);
      	  }
      	  
	}

	@Override
	protected void onStart() {
		super.onStart();
		registerReceiver(mUsbDector, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(mUsbDector);
	}
	
	
	
}
