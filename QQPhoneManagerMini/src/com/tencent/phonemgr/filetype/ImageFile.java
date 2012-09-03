package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.tencent.phonemgr.R;

public class ImageFile implements FileItem {

	public static final String JPG_FILE = ".jpg";
	public static final String PNG_FILE = ".png";
	
	private File file;
	
	public ImageFile(File file) {
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
	public void open(Activity context) {
	     Intent intent = new Intent();
	     intent.setAction(android.content.Intent.ACTION_VIEW);
	     intent.setDataAndType(Uri.fromFile(file), "image/*");
	     context.startActivity(intent);     
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
