package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.tencent.phonemgr.utils.apk.ApkUtils;

public class ApkFile implements FileItem {

	public static final String APK_FILE = ".apk";
	
	private boolean isDir;
	private File file;
	
	public ApkFile(File file) {
		this.file = file;
		this.isDir = false;
	}
	
	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public Bitmap getBitmapLogo(Context context) {
		return ApkUtils.getApkBitmapIcon(context, file.getAbsolutePath());
	}

	@Override
	public void open(Activity activity) {
		ApkUtils.installApk(activity, file);
	}

	@Override
	public void close() {
		
	}

	@Override
	public void setOnLoadListener(OnLoadListener listener) {
		
	}
	
	@Override
	public File getParentDir() {
		return file.getParentFile();
	}
	
	@Override
	public boolean isDir() {
		return isDir;
	}

	@Override
	public int compareTo(FileItem another) {
		if(another.isDir()) {
			return -1;
		} else {
			return this.getName().compareTo(another.getName());
		}
	}

	@Override
	public File getFile() {
		return file;
	}

}
