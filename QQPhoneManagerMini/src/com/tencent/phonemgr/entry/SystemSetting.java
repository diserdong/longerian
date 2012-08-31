package com.tencent.phonemgr.entry;

import android.content.Context;
import android.content.Intent;

import com.tencent.phonemgr.R;
import com.tencent.phonemgr.ShortcutActivity;

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
		Intent intent = new Intent(context, ShortcutActivity.class);
		context.startActivity(intent);
	}

}
