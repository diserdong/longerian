package com.tencent.phonemgr.entry;

import android.content.Context;
import android.content.Intent;

import com.tencent.phonemgr.InstallerActivity;
import com.tencent.phonemgr.R;

public class Installer implements Entry {

	@Override
	public int getLabelId() {
		return R.string.entry_installation_and_uninstallation;
	}

	@Override
	public int getLogoId() {
		return R.drawable.ic_app_installer;
	}

	@Override
	public void doAction(Context context) {
		Intent intent = new Intent(context, InstallerActivity.class);
		context.startActivity(intent);
	}

}
