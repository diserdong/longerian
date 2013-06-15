package me.longerian.abcandroid.showcase;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShowcaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcase);
		Button showcase = (Button) findViewById(R.id.anchor);
		showcase.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShowcaseView sv = ShowcaseView.insertShowcaseView(v, ShowcaseActivity.this);
				sv.show();
			}
		});
	}
	
}
