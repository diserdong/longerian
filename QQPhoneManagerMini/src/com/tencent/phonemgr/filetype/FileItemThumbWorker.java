package com.tencent.phonemgr.filetype;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.tencent.phonemgr.BuildConfig;
import com.tencent.phonemgr.utils.bitmap.ImageResizer;

public class FileItemThumbWorker extends ImageResizer {

	private static final String TAG = "FileItemThumbWorker";
	
	private Context context;
	
	public FileItemThumbWorker(Context context, int imageWidth, int imageHeight) {
        super(context, imageWidth, imageHeight);
        this.context = context;
    }
	
	public FileItemThumbWorker(Context context, int imageSize) {
		super(context, imageSize);
		this.context = context;
	}

	@Override
	protected Bitmap processBitmap(Object data) {
		return processBitmap((FileItem) data);
	}
	
	private Bitmap processBitmap(FileItem data) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "processBitmap - " + data.getName());
        }
        return data.getBitmapLogo(context);
    }
	
	
}
