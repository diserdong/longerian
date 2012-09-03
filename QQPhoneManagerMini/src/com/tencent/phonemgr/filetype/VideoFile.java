package com.tencent.phonemgr.filetype;

import java.io.File;

import com.tencent.phonemgr.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

public class VideoFile implements FileItem {

	public static final String THREEGP_FILE = ".3gp";
	public static final String MP4_FILE = ".mp4";
	public static final String FLV_FILE = ".flv";
	
	private File file;
	
	public VideoFile(File file) {
		this.file = file;
	}
	
	@Override
	public File getParentDir() {
		return file.getParentFile();
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public Drawable getLogo(Context context) {
		// TODO Auto-generated method stub
		return context.getResources().getDrawable(R.drawable.ic_video);
	}

	@Override
	public void open(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_VIEW);  
	    intent.setDataAndType(Uri.fromFile(file), "video/*");
	    activity.startActivity(intent);
	}

	@Override
	public void close() {

	}

	@Override
	public void setOnLoadListener(OnLoadListener listener) {

	}

}
