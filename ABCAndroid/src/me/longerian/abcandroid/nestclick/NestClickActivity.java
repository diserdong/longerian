package me.longerian.abcandroid.nestclick;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class NestClickActivity extends Activity {

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_image);
	        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	        container.setOnClickListener(listener);
	        ImageView image = (ImageView) findViewById(R.id.image);
//	        Uri thumbnail = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, 1);
	        Uri thumbnail = ContentUris.withAppendedId(Images.Thumbnails.EXTERNAL_CONTENT_URI, 11);
	        Log.d("longer", thumbnail.toString());
	        image.setImageURI(thumbnail);
	        image.setOnClickListener(listener);
	    }

	 private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.container:
				Log.d("longer", v.toString());
				break;
			case R.id.image:
				Log.d("longer", v.toString());
				break;
			}
		}
	};
	 
}
