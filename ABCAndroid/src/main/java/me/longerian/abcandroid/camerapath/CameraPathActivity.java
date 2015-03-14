package me.longerian.abcandroid.camerapath;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

public class CameraPathActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Environment.DIRECTORY_DCIM;
	}
}
