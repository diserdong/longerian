package me.longerian.abcandroid.pinnedheaderlist;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class PinnedHeaderListActivity extends Activity {

	private TestAdapter adapter;
	private PinnedHeaderListView listView;
	private ViewGroup fixedHeader;
	private View pinnedHeader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main1);
		pinnedHeader = findViewById(R.id.pinned_header);
		
		adapter = new TestAdapter(getLayoutInflater());
		
		listView = (PinnedHeaderListView)this.findViewById(R.id.section_list_view);
		fixedHeader = (ViewGroup) getLayoutInflater().inflate(R.layout.complete_header, null, false);
		listView.setNestedHeaderView(fixedHeader, null, true);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(adapter);
		listView.setPinnedHeaderView(pinnedHeader);
	}
	
}
