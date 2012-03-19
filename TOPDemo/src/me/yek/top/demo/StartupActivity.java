package me.yek.top.demo;

import me.yek.top.demo.util.PreferenceUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartupActivity extends Activity {

	private SharedPreferences mPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_up_layout);
		mPref = getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE);
		PreferenceUtil.setupPreferences(mPref);
		if(hasSetup()) {
			go2Demo();
		} else {
			initUI();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == UserAuthorizationActivity.REQUEST_CODE) {
			switch(resultCode) {
			case RESULT_CANCELED:	
			case RESULT_OK:
				initGo2Demo();
				break;
			}
		}
	}

	private void initUI() {
		initUserNameEdit();
		initGo2Authorization();
		initGo2Demo();
	}
	
	private void initUserNameEdit() {
		final EditText et = (EditText) findViewById(R.id.buyer_nick);
		et.setText(PreferenceUtil.getUserName(mPref));
		Button b = (Button) findViewById(R.id.save);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(PreferenceUtil.setUserName(mPref, et.getText().toString().trim())) {
					Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
					initGo2Demo();
				}
			}
		});
	}
	
	private void initGo2Authorization() {
		Button b = (Button) findViewById(R.id.go_to_authorization);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), UserAuthorizationActivity.class);
				startActivityForResult(i, UserAuthorizationActivity.REQUEST_CODE);
			}
		});
	}
	
	private boolean hasSetup() {
		return !PreferenceUtil.getUserName(mPref).equals("") 
			&& !PreferenceUtil.getSession(mPref).equals("");
	}
	
	private void initGo2Demo() {
		Button b = (Button) findViewById(R.id.go_to_demo);
		if(hasSetup()) {
			b.setEnabled(true);
		} else {
			b.setEnabled(false);
		}
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				go2Demo();
			}
		});
	}
	
	private void go2Demo() {
		Intent i = new Intent(getApplicationContext(), RocawearClientActivity.class);
		startActivity(i);
		finish();
	}
	
}
