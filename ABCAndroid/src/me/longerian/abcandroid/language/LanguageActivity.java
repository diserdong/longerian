package me.longerian.abcandroid.language;

import java.util.Locale;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class LanguageActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView language = (TextView) findViewById(R.id.hello);
		language.setText(R.string.language);
	}

	private Locale locale = null;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d("LanguageActivity", newConfig.toString());
		if (locale != null) {
			newConfig.locale = locale;
			Locale.setDefault(locale);
			getBaseContext().getResources().updateConfiguration(newConfig,
					getBaseContext().getResources().getDisplayMetrics());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "简体中文");
		menu.add(0, 2, 2, "繁体中文");
		menu.add(0, 3, 3, "英文");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Configuration config = getBaseContext().getResources()
				.getConfiguration();
		String lang = "";
		switch (item.getItemId()) {
		case 1:
			lang = "zh_CN";
			break;
		case 2:
			lang = "zh_TW";
			break;
		case 3:
			lang = "en";
			break;
		}
		locale = new Locale(lang);
		Locale.setDefault(locale);
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
		return true;
	}

}
