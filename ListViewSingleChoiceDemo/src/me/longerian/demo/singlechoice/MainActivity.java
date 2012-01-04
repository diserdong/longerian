package me.longerian.demo.singlechoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }
	
	public void clickEventHandler(View v) {
		int id = v.getId();
		Intent intent = new Intent();
		switch(id) {
		case R.id.btn_default_ui:
			intent.setClass(getApplicationContext(), DefaultUISingleChoiceDemo.class);
			startActivity(intent);
			break;
		case R.id.btn_custom_ui:
			intent.setClass(getApplicationContext(), CustomUISingleChoiceDemo.class);
			startActivity(intent);
		case R.id.btn_group_default_ui:
			intent.setClass(getApplicationContext(), GroupDefaultUISingleChoiceDemo.class);
			startActivity(intent);
			break;
		case R.id.btn_group_custom_ui:
			intent.setClass(getApplicationContext(), GroupCustomUISingleChoiceDemo.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
}
