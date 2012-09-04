package com.tencent.phonemgr.entry;

import android.content.Context;
import android.content.Intent;

import com.tencent.phonemgr.AppManagerActivity;
import com.tencent.phonemgr.R;

public class AppManager implements Entry {

	@Override
	public int getLabelId() {
		return R.string.entry_app_manager;
	}

	@Override
	public int getLogoId() {
		return R.drawable.ic_app_manager;
	}

	@Override
	public void doAction(Context context) {
		Intent intent = new Intent(context, AppManagerActivity.class);
		context.startActivity(intent);
	}

}
