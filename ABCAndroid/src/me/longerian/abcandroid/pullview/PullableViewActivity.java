package me.longerian.abcandroid.pullview;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PullableViewActivity extends Activity {

	String[] data = new String[] { "Android", "iPhone", "Blackberry",
			"WindowPhone", "WebOS", "PhoneGap", "FirefoxOS", "Megoo", "Tizen",
			"Bada", "Android", "iPhone", "Blackberry", "WindowPhone", "WebOS",
			"PhoneGap", "FirefoxOS", "Megoo", "Tizen", "Bada", "Android",
			"iPhone", "Blackberry", "WindowPhone", "WebOS", "PhoneGap",
			"FirefoxOS", "Megoo", "Tizen", "Bada" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pullableview);
		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, android.R.id.text1, data));
	}

}
