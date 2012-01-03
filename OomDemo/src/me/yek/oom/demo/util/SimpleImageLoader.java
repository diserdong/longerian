package me.yek.oom.demo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class SimpleImageLoader implements Loadable {

	@Override
	public Bitmap loadBitmapImage(String path) {
		return BitmapFactory.decodeFile(path);
	}

	@Override
	public Drawable loadDrawableImage(String path) {
		return new BitmapDrawable(path);
	}

	@Override
	public void releaseImage(String path) {
		
	}

}
