package com.tencent.phonemgr.entry;

import android.content.Context;

public interface Entry {

	public int getLabelId();
	public int getLogoId();
	public void doAction(Context context);
	
}
