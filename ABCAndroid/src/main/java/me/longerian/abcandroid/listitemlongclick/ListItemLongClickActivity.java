package me.longerian.abcandroid.listitemlongclick;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ListItemLongClickActivity extends Activity {

	private String TAG = "QQ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sidebar);
		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, qqlist));
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "position" + position, Toast.LENGTH_LONG).show();
				return true;
			}
			
		});
	}
	
	private static String[] qqlist = new String[] {
		"QWERTYUIO",
		"JDKAFPJEKLR",
		"QWEJOLASK",
		"VMKOOEDKR",
		"WFNKSDOPV",
		"JFODAP903JF",
		"23JHFJ0OIDF",
	};
	
}
