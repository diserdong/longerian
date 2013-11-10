package cc.icefire.market;

import android.app.Application;

public class IceFireApplication extends Application {
	
	private static IceFireApplication instance;
	
	public static IceFireApplication sharedInstance() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
	
	public void exit() {
		System.exit(0);
	}

}