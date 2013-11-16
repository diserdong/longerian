package cc.icefire.market.bitmaploader;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import crow.cache.Cache;
import crow.loader.AsyncLoaderEngine;
import crow.loader.BitmapAsyncLoader;

public class BaseBitmapAsyncLoader extends BitmapAsyncLoader {

	public BaseBitmapAsyncLoader(AsyncLoaderEngine engine) {
		super(engine);
	}

	@Override
	protected Bitmap loadFromCache(Cache cache, String url) {
		Bitmap bm = null;
		File file = cache.getCacheFile(url);
		if (file.exists()) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(file.getAbsolutePath(), options); 
			if (options.mCancel || options.outWidth == -1
					|| options.outHeight == -1) {
				return null;
			}
			options.inSampleSize = BitmapResizeUtil.computeSampleSize(options, 800, (int) (1 * 1024 * 1024));
			options.inJustDecodeBounds = false;
			options.inDither = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			bm = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
		}
		return bm;
	}
}
