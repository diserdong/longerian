package me.longerian.abcandroid.listsidebar;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ListSidebarActivity extends Activity {

	private String TAG = "QQ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sidebar);
		RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, qqlist));
		list.setOnItemClickListener(itemClickListener);
		setContainerHeight(container, list);
		
	}
	
	public static void setContainerHeight(RelativeLayout container, ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
		    // pre-condition
		    return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
		    View listItem = listAdapter.getView(i, null, listView);
		    listItem.measure(0, 0);
		    totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams parmas= container.getLayoutParams();
		parmas.height = (totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))) / listAdapter.getCount() * 3;
		container.setLayoutParams(parmas);
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
		    // pre-condition
		    return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
		    View listItem = listAdapter.getView(i, null, listView);
		    listItem.measure(0, 0);
		    totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = (totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))) / listAdapter.getCount() * 3;
		listView.setLayoutParams(params);
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
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, int position,
				long id) {
			Toast.makeText(getApplicationContext(), (CharSequence) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
		}
	};
	
}
