package cc.icefire.market.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView t = new TextView(getApplicationContext());
		String label = getIntent().getStringExtra("label");
		t.setTag("Label");
		t.setTextColor(Color.BLACK);
		setContentView(t);
		
	}

}
