package me.longerian.abcandroid.cputype;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CpuTypeGetter extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView hello = (TextView) findViewById(R.id.hello);
		StringBuilder sb = new StringBuilder();
		sb.append(android.os.Build.CPU_ABI);
//		sb.append("\n");
//		sb.append(android.os.Build.CPU_ABI2);
//		sb.append("\n");
//		sb.append(android.os.Build.HARDWARE);
		hello.setText(sb.toString());
	}
	
	

}
