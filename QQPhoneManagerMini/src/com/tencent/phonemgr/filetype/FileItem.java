package com.tencent.phonemgr.filetype;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface FileItem {
	
	public String getName();
	public Drawable getLogo(Context context);
	public void open(Context context);
	
}
