package me.longerian.abcandroid.imageuri;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.widget.ImageView;

public class ImageActivity extends Activity {

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_image);
	        ImageView image = (ImageView) findViewById(R.id.image);
//	        Uri thumbnail = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, 1);
	        Uri thumbnail = ContentUris.withAppendedId(Images.Thumbnails.EXTERNAL_CONTENT_URI, 11);
	        Log.d("longer", thumbnail.toString());
	        image.setImageURI(thumbnail);
	        
	        
	    }
	 
}
