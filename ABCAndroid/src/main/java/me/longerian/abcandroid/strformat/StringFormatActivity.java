package me.longerian.abcandroid.strformat;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class StringFormatActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_format);
        TextView relative = (TextView) findViewById(R.id.relative);
        TextView absolute = (TextView) findViewById(R.id.absolute);
		String rawPercent = getString(R.string.progress_relative);
		Log.d("longer", rawPercent);
		String formattedPercent = String.format(rawPercent, 80);
		relative.setText(formattedPercent);
		String rawRatio = getString(R.string.progress_absolute);
		Log.d("longer", rawRatio);
		String formattedRatio = String.format(rawRatio, 3.2f, 4.0f);
        absolute.setText(formattedRatio);
        
	}
	
}
