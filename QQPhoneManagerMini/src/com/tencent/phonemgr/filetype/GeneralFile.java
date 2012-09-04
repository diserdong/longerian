package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.phonemgr.R;

public class GeneralFile implements FileItem {

	private boolean isDir;
	private File file;
	
	public GeneralFile(File file) {
		this.file = file;
		this.isDir = false;
	}
	
	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public Bitmap getBitmapLogo(Context context) {
		return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_file);
	}
	
	@Override
	public void open(Activity context) {

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

}
