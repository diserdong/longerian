package me.yek.oom.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.AdapterView.OnItemSelectedListener;

public class OomDemoActivity extends Activity {
	
	private Gallery mGallery;
	private ArrayAdapter<String> mAdapter;
	private static String[] mResPath = {
		"/sdcard/cache/a.jpg",		"/sdcard/cache/b.jpg",		"/sdcard/cache/c.jpg",		"/sdcard/cache/d.jpg",
		"/sdcard/cache/e.jpg",		"/sdcard/cache/f.jpg",		"/sdcard/cache/g.jpg",		"/sdcard/cache/h.jpg",
		"/sdcard/cache/i.jpg",		"/sdcard/cache/j.jpg",		"/sdcard/cache/k.jpg",		"/sdcard/cache/l.jpg",
		"/sdcard/cache/m.jpg",		"/sdcard/cache/n.jpg",		"/sdcard/cache/o.jpg",		"/sdcard/cache/p.jpg",
		"/sdcard/cache/q.jpg",		"/sdcard/cache/r.jpg"
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mGallery = (MyGallery) findViewById(R.id.gallery_demo);
        mAdapter = new GalleryAdapter(getApplicationContext(), R.layout.content, mResPath, mGallery);
        mGallery.setAdapter(mAdapter);
        mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				((GalleryAdapter) parent.getAdapter()).releaseDrawable(mGallery);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		
        });
    }
    
}