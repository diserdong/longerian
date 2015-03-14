package me.longerian.abcandroid.audioplay;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.audioplay.PlayIndicator.OnStatusChangedListener;
import me.longerian.abcandroid.audioplay.PlayIndicator.STATUS;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class PlayIndicatorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_indicator);
		PlayIndicator pi = (PlayIndicator) findViewById(R.id.indicator);
		pi.setOnStatusChangedListener(new OnStatusChangedListener() {
			
			@Override
			public void onStatusChanged(STATUS status) {
				switch (status) {
				case IDLE:
					Log.d("PlayIndicatorActivity", "is idle now");
					break;
				case PLAYING:
					Log.d("PlayIndicatorActivity", "is playing now");
					break;

				default:
					break;
				}
			}
		});
		pi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("PlayIndicatorActivity", "override default listener");
			}
		});
	}

	
	
}
