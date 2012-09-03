package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.tencent.phonemgr.R;

public class AudioFile implements FileItem {

	public static final String MP3_FILE = ".mp3";
	public static final String M4A_FILE = ".m4a";
	public static final String THREEGP_FILE = ".3gp";
	
	private File file;
	
	public AudioFile(File file) {
		this.file = file;
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
