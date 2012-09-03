package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.tencent.phonemgr.R;

public class AudioFile implements FileItem {

	public static final String MP3_FILE = ".mp3";
	public static final String M4A_FILE = ".m4a";
	
	private boolean isDir;
	private File file;
	
	public AudioFile(File file) {
		this.file = file;
		this.isDir = false;
	}

	
	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public Drawable getLogo(Context context) {
		return context.getResources().getDrawable(R.drawable.ic_audio);
	}

	@Override
	public void open(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_VIEW);  
	    intent.setDataAndType(Uri.fromFile(file), "audio/*");
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
