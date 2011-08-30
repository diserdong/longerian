package me.yek.oom.demo.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

//为了一步一步演示不同ImageLoader的功能，实现此接口的类代码有重复。
public interface Loadable {

	public Bitmap loadBitmapImage(String path);
	public Drawable loadDrawableImage(String path);
	public void releaseImage(String path);
	
}
