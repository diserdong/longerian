package com.tencent.phonemgr.entry;

import android.content.Context;
import android.content.Intent;

import com.tencent.phonemgr.QuickSettingActivity;
import com.tencent.phonemgr.R;

public class QuickSetting implements Entry {

	@Override
	public int getLabelId() {
		return R.string.entry_quick_setting;
	}

	@Override
	public int getLogoId() {
		return R.drawable.ic_quick_setting;
	}

	@Override
	public void doAction(Context context) {
		Intent intent = new Intent(context, QuickSettingActivity.class);
		context.startActivity(intent);
	}

}
