package com.tencent.phonemgr.utils.bitmap;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;


public class AppIconWorker extends ImageResizer {

private static final String TAG = "AppIconWorker";
	
	private Context context;
	
	public AppIconWorker(Context context, int imageWidth, int imageHeight) {
        super(context, imageWidth, imageHeight);
        this.context = context;
    }
	
	public AppIconWorker(Context context, int imageSize) {
		super(context, imageSize);
		this.context = context;
	}

	@Override
	protected Bitmap processBitmap(Object data) {
		ResolveInfo info = (ResolveInfo) data;
		BitmapDrawable icon = (BitmapDrawable) info.activityInfo.loadIcon(context.getPackageManager());
		return icon.getBitmap();
	}
	
}
