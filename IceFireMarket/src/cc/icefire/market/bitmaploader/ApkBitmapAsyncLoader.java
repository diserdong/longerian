package cc.icefire.market.bitmaploader;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import cc.icefire.market.IceFireApplication;
import crow.cache.Cache;
import crow.loader.AsyncLoader;
import crow.loader.AsyncLoaderEngine;

public class ApkBitmapAsyncLoader extends AsyncLoader<String, Bitmap, ImageView> {

	public ApkBitmapAsyncLoader(AsyncLoaderEngine engine) {
		super(engine);
	}

	/**
	 * 将pkg 对应的图片加载到本地缓存。
	 */
	@Override
	protected boolean downloadToCache(Cache cache, String pkg) {
		//always return true
		return true;
	}
	/**
	 * 如果对图片需按采样率生成，请写此方法
	 */
	@Override
	protected Bitmap loadFromCache(Cache cache, String pkg) {
		PackageManager pm = IceFireApplication.sharedInstance().getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(pkg, 0);
			Drawable d = packageInfo.applicationInfo.loadIcon(pm);
			return ((BitmapDrawable) d).getBitmap();
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 空实现，若加载失败需要设为特殊图片。可在此方法中完成。
	 */
	@Override
	protected void onLoadFail(String url, ImageView target) {
		
	}

	@Override
	protected void onLoaded(String url, Bitmap bm, ImageView target) {
		if (target != null){
			target.setImageBitmap(bm);
		}
	}

}
