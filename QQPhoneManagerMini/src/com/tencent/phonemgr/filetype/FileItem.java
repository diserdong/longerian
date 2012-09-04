package com.tencent.phonemgr.filetype;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

public interface FileItem extends Comparable<FileItem> {
	
	public interface OnLoadListener {
		
		public void onStartLoading();
		public void onFinishLoading(List<FileItem> fileItems);
		
	}
	
	public boolean isDir();
	public File getParentDir();
	public String getName();
	public Bitmap getBitmapLogo(Context context);
	public void open(Activity activity);
	public void close();
	public void setOnLoadListener(OnLoadListener listener);
	
}
