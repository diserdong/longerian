package me.longerian.abcandroid.notification;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NotificationTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView hello = (TextView) findViewById(R.id.hello);
		hello.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playNotification();
			}
		});
	}

	protected void playNotification() {
		Notification mNotification = null;
		mNotification = new Notification(R.drawable.circle, getResources()
				.getString(R.string.app_name), System.currentTimeMillis());

		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
				new Intent(getApplicationContext(),
						NotificationTest.class),
				PendingIntent.FLAG_UPDATE_CURRENT);

		mNotification.setLatestEventInfo(getApplicationContext(),
				getResources().getString(R.string.app_name), getResources()
						.getString(R.string.app_name), contentIntent);
		mNotification.defaults = Notification.DEFAULT_VIBRATE;
		
//		mNotification.sound = Uri.parse("file:///android_asset/beep.ogg");  //ko
		mNotification.sound = Uri.parse("file:///sdcard/Notifications/beep.ogg");  //ok 
//		mNotification.sound = Uri.parse("android.resource://me.longerian.abcandroid/raw/beep.ogg"); //ko
		
		NotificationManager mNotificationManager = (NotificationManager) getApplicationContext()
				.getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.notify(123456789, mNotification);
	}

}
