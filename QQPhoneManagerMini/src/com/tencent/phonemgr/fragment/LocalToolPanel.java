package com.tencent.phonemgr.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.tencent.phonemgr.R;

public class LocalToolPanel extends SherlockFragment {

	public static LocalToolPanel newInstance() {
		LocalToolPanel f = new LocalToolPanel();
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_local_tool, null);
	}

	
	
	
}
