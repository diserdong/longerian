package com.tencent.phonemgr.entry;

import com.tencent.phonemgr.R;

public class SystemSetting implements Entry {

	@Override
	public int getStringId() {
		return R.string.entry_system_setting;
	}

	@Override
	public int getLogoId() {
		return R.drawable.ic_launcher;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub

	}

}
