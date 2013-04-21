package me.longerian.abcandroid;

import java.util.Locale;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

public class ABCApplication extends Application {

	private Locale locale = null;

	
	@Override
	public void onCreate() {
		super.onCreate();

//		SharedPreferences settings = PreferenceManager
//				.getDefaultSharedPreferences(this);
//
//		Configuration config = getBaseContext().getResources()
//				.getConfiguration();
//
//		String lang = settings.getString(getString(R.string.pref_locale), "");
//		if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
//			locale = new Locale(lang);
//			Locale.setDefault(locale);
//			config.locale = locale;
//			getBaseContext().getResources().updateConfiguration(config,
//					getBaseContext().getResources().getDisplayMetrics());
//		}
	}

}
