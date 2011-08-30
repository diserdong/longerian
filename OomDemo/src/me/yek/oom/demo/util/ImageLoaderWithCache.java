package me.yek.oom.demo.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageLoaderWithCache implements Loadable {

	private HashMap<String, SoftReference<Bitmap>> mImageCache;
	
	public ImageLoaderWithCache() {
		mImageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	@Override
	public Bitmap loadBitmapImage(String path) {
		if(mImageCache.containsKey(path)) {
			SoftReference<Bitmap> softReference = mImageCache.get(path);
			Bitmap bitmap = softReference.get();
			if(null != bitmap)
				return bitmap;
		}
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		mImageCache.put(path, new SoftReference<Bitmap>(bitmap));
		return bitmap;
	}

	@Override
	public Drawable loadDrawableImage(String path) {
		return new BitmapDrawable(loadBitmapImage(path));
	}

	@Override
	public void releaseImage(String path) {
		// TODO Auto-generated method stub
		
	}

}
