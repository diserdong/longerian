package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

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
		// TODO Auto-generated method stub
		return context.getResources().getDrawable(R.drawable.ic_launcher);
	}

	@Override
	public void open(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		activity.startActivity(intent);
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
