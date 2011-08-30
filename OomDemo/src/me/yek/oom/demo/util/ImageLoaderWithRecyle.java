package me.yek.oom.demo.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ImageLoaderWithRecyle implements Loadable {

	private HashMap<String, SoftReference<Bitmap>> mImageCache;
	
	public ImageLoaderWithRecyle() {
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
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options); 
		if (options.mCancel || options.outWidth == -1
				|| options.outHeight == -1) {
			Log.d("OomDemo", "alert!!!" + String.valueOf(options.mCancel) + " " + options.outWidth + options.outHeight);
			return null;
		}
		options.inSampleSize = Util.computeSampleSize(options, 600, (int) (1 * 1024 * 1024));
		Log.d("OomDemo", "inSampleSize: " + options.inSampleSize);
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); 
		mImageCache.put(path, new SoftReference<Bitmap>(bitmap));
		return bitmap;
	}

	@Override
	public Drawable loadDrawableImage(String path) {
		return new BitmapDrawable(loadBitmapImage(path));
	}

	@Override
	public void releaseImage(String path) {
		if(mImageCache.containsKey(path)) {
			SoftReference<Bitmap> reference = mImageCache.get(path);
			Bitmap bitmap = reference.get();
			if(null != bitmap) {
				Log.d("OomDemo", "recyling " + path);
				bitmap.recycle();
			}
			mImageCache.remove(path);
		}
	}

}
