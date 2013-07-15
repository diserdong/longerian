package me.longerian.abcandroid.usbdetection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class UsbDetector extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
			int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
			String powerState = "unknown";
			switch(plugged) {
			case 0:
				powerState = "on batery";
				break;
			case BatteryManager.BATTERY_PLUGGED_AC:
				powerState = "on ac";
				break;
			case BatteryManager.BATTERY_PLUGGED_USB:
				powerState = "on usb port";
				break;
			case BatteryManager.BATTERY_PLUGGED_WIRELESS:
				powerState = "wireless future style";
				break;
			}
			Toast.makeText(context, powerState, Toast.LENGTH_LONG).show();
		}
	}

}
