package com.tencent.phonemgr.filetype;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

public interface FileItem {
	
	public interface OnLoadListener {
		
		public void onStartLoading();
		public void onFinishLoading(List<FileItem> fileItems);
		
	}
	
	public File getParentDir();
	public String getName();
	public Drawable getLogo(Context context);
	public void open(Activity activity);
	public void close();
	public void setOnLoadListener(OnLoadListener listener);
	
}
