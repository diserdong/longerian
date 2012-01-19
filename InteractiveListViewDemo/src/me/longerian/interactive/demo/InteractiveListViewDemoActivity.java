package me.longerian.interactive.demo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class InteractiveListViewDemoActivity extends ListActivity {
	
	private static final String[] items = {
		"Android0", "iPhone0", "WindowPhone0", "Blackberry0", "Symbian0", "WebOS0", "MeeGo0", "Bada0",
		"Android1", "iPhone1", "WindowPhone1", "Blackberry1", "Symbian1", "WebOS1", "MeeGo1", "Bada1"
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<RowModel> rows = new ArrayList<RowModel>();
        for(String s : items) {
        	rows.add(new RowModel(s));
        }
        setListAdapter(new InteractiveAdapter(getApplicationContext(), rows));
    }

}