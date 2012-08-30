package com.tencent.phonemgr.entry;

import android.content.Context;

import com.tencent.phonemgr.R;

public class FileManager implements Entry {

	@Override
	public int getLabelId() {
		return R.string.entry_file_manager;
	}

	@Override
	public int getLogoId() {
		return R.drawable.ic_launcher;
	}

	@Override
	public void doAction(Context context) {
		// TODO Auto-generated method stub

	}

}
