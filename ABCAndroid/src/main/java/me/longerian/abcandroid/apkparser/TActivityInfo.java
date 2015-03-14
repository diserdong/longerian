package me.longerian.abcandroid.apkparser;

import java.util.ArrayList;
import java.util.List;

import android.content.IntentFilter;
import android.util.Log;

public class TActivityInfo {

	private String className;
	private List<IntentFilter> intentInfo;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<IntentFilter> getIntentInfo() {
		return intentInfo;
	}

	public void setIntentInfo(List<IntentFilter> intentInfo) {
		this.intentInfo = intentInfo;
	}

	public void addIntentFilter(IntentFilter intentFilter) {
		if(intentInfo == null) {
			intentInfo = new ArrayList<IntentFilter>();
		}
		intentInfo.add(intentFilter);
	}
	
	public void print() {
		if(className != null) {
			Log.d("Longer", className);
		}
		if(intentInfo != null) {
			for(IntentFilter filter : intentInfo) {
				for(int m = 0, count = filter.countActions(); m< count; m++) {
        			Log.d("Longer", filter.getAction(m));
        		}
        		for(int m = 0, count = filter.countCategories(); m < count; m++) {
        			Log.d("Longer", filter.getCategory(m));
        		}
			}
		}
	}

}
