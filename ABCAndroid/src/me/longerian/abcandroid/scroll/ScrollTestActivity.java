package me.longerian.abcandroid.scroll;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class ScrollTestActivity extends Activity {

	private PullableView pullableView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll);
		pullableView = (PullableView) findViewById(R.id.pullable_view);
		View title = LayoutInflater.from(getApplicationContext()).inflate(R.layout.content_title, null);
		View content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.content_connection_request, null);
		View bottom = LayoutInflater.from(getApplicationContext()).inflate(R.layout.content_bottom, null);
		pullableView.addScreen(title, 0);
//		pullableView.addScreen(content);
		pullableView.addScreen(bottom, 2);
	}

}
