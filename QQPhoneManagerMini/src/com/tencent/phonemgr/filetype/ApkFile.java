package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.tencent.phonemgr.utils.ApkUtils;

public class ApkFile implements FileItem {

	public static final String APK_FILE = ".apk";
	
	private File file;
	
	public ApkFile(File file) {
		this.file = file;
	}
	
	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public Drawable getLogo(Context context) {
		return ApkUtils.getApkIcon(context, file.getAbsolutePath());
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

}
