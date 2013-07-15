package me.longerian.abcandroid.usbdetection;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class UsbDectectorTest extends Activity {

	private UsbDetector mUsbDector = new UsbDetector();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
