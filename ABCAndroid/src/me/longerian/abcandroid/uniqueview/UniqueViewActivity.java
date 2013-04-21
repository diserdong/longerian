package me.longerian.abcandroid.uniqueview;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class UniqueViewActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView image0 = (ImageView) findViewById(R.id.image);
        Log.d("longer", image0.toString());
        ImageView image1 = (ImageView) findViewById(R.id.image);
        Log.d("longer", image1.toString());
    }
	
}
