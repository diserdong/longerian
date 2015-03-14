package me.longerian.abcandroid.tiledview;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class TiledLayoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tiled_view);
		TiledLayout tl = (TiledLayout) findViewById(R.id.container);
		
		TextView tv = new TextView(getApplicationContext());
		tv.setText("钛备份");
		tv.setBackgroundColor(Color.rgb(198, 201, 203));
		tv.setPadding(5, 5, 5, 5);
		tl.addView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		TextView tv1 = new TextView(getApplicationContext());
		tv1.setText("360安全卫士");
		tv1.setBackgroundColor(Color.rgb(198, 201, 203));
		tv1.setPadding(5, 5, 5, 5);
		tl.addView(tv1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		TextView tv2 = new TextView(getApplicationContext());
		tv2.setText("微信");
		tv2.setBackgroundColor(Color.rgb(198, 201, 203));
		tv2.setPadding(5, 5, 5, 5);
		tl.addView(tv2, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		TextView tv3 = new TextView(getApplicationContext());
		tv3.setText("大智慧");
		tv3.setBackgroundColor(Color.rgb(198, 201, 203));
		tv3.setPadding(5, 5, 5, 5);
		tl.addView(tv3, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
	}

}
