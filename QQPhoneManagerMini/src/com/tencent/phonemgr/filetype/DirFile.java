package com.tencent.phonemgr.filetype;

import java.io.File;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class DirFile implements FileItem {

	private File file;
	
	public DirFile(File file) {
		this.file = file;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Drawable getLogo(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open(Context context) {
		// TODO Auto-generated method stub

	}

}
