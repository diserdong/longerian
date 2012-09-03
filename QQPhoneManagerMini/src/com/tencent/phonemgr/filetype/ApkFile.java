package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.tencent.phonemgr.R;

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
		return context.getResources().getDrawable(R.drawable.ic_launcher);
	}

	@Override
	public void open(Activity context) {
		// TODO Auto-generated method stub
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
