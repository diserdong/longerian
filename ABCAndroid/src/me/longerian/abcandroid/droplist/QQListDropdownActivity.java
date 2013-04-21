package me.longerian.abcandroid.droplist;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class QQListDropdownActivity extends Activity {

	private Button btn;
	private QQListPopupWindow qqpp;
	private String TAG = "QQ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dropdown);
		btn = (Button) findViewById(R.id.trigger);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPopup();
			}
		});
		String response = "requestId:1347935330656\r\n/sms?act=csc_list&begin_counter=0&count=15";
		
		String[] tmp =response.split("\\r\\n");
		Log.d(TAG, tmp[0]);
		Log.d(TAG, tmp[1]);
		String[] idKeyValue = tmp[0].split(":");
		Log.d(TAG, idKeyValue[0]);
		Log.d(TAG, idKeyValue[1]);
	}
	
	private static String[] qqlist = new String[] {
		"QWERTYUIO",
		"JDKAFPJEKLR",
		"QWEJOLASK",
		"VMKOOEDKR",
		"WFNKSDOPV",
	};
	
	private void showPopup() {
		if(qqpp == null) {
			qqpp = new QQListPopupWindow(getApplicationContext());
			qqpp.setAnchorView(btn);
			qqpp.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, qqlist));
			qqpp.setOnItemClickListener(itemClickListener);
		}
		if(!qqpp.isShowing()) {
			qqpp.show();
		}
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, int position,
				long id) {
			Toast.makeText(getApplicationContext(), (CharSequence) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
		}
	};
	
}
