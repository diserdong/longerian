package com.tencent.phonemgr.entry;

import android.content.Context;

import com.tencent.phonemgr.R;

public class SystemSetting implements Entry {

	@Override
	public int getLabelId() {
		return R.string.entry_system_setting;
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
