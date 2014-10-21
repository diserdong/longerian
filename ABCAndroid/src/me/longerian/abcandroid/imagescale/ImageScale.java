package me.longerian.abcandroid.imagescale;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ImageScale extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_scale);
		String url = "$this.data.link"; 
		Uri uri = Uri.parse(url);
		Log.d("Longer", "url = " + uri.toString());
		String longer = uri.getQueryParameter("longer");
		Log.d("Longer", "longer = " + longer);
		
	}
	
}
