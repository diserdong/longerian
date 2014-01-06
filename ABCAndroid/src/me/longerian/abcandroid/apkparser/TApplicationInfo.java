package me.longerian.abcandroid.apkparser;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class TApplicationInfo {

	private List<TActivityInfo> activities;

	public List<TActivityInfo> getActivities() {
		return activities;
	}

	public void setActivities(List<TActivityInfo> activities) {
		this.activities = activities;
	}
	
	public void addActivity(TActivityInfo ai) {
		if(activities == null) {
			activities = new ArrayList<TActivityInfo>();
		}
		activities.add(ai);
	}

	public void print() {
		if(activities != null) {
			for(TActivityInfo a : activities) {
				a.print();
			}
		} else {
			Log.d("Longer", "activities null");
		}
	}
	
}
