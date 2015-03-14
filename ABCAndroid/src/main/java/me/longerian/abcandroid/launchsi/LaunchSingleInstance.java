package me.longerian.abcandroid.launchsi;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LaunchSingleInstance extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView hello = (TextView) findViewById(R.id.hello);
		hello.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PackageManager pm = getPackageManager();
				Intent it = pm.getLaunchIntentForPackage("com.qq.AppService");
				it.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				it.putExtra("dbc_pc_connect", true);
				it.putExtra("from", "your uri");
				it.putExtra("uri", "http://www.molohui.com/tencent/QQPhoneManager.apk?pcid={13ABD4ED-33FC-4F44-9A5F-DD7D43024F00}&cTime=1364469871694");
				startActivity(it);
			}
		});
	}

}
