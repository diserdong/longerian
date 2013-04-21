package me.longerian.abcandroid.pinnedheaderlist;

import java.util.ArrayList;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.pinnedheaderlist.PinnedHeaderListView.PinnedHeaderAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestAdapter extends BaseAdapter implements PinnedHeaderAdapter,
		OnScrollListener {

	private LayoutInflater inflater;

	private ArrayList<Person> datas;
	private int lastItem = 0;

	public TestAdapter(final LayoutInflater inflater) {
		this.inflater = inflater;
		loadData();
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.section_list_item, null);
		}
		final Person person = datas.get(position);
		final TextView header = (TextView) view.findViewById(R.id.header);
		final TextView textView = (TextView) view
				.findViewById(R.id.example_text_view);
		textView.setText(person.getNumber());
		header.setText(person.getName());
		
		if (lastItem == position) {
			header.setVisibility(View.INVISIBLE);
		} else {
			header.setVisibility(View.VISIBLE);
		}
		return view;
	}

	@Override
	public int getPinnedHeaderState(int position) {
		if (position <= 0 || getCount() == 0) {
    		return PINNED_HEADER_GONE;
    	}
		return PINNED_HEADER_VISIBLE;
	}
	
	@Override
	public void configurePinnedHeader(View header, int position, int alpha) {
		if (lastItem != position) {
			notifyDataSetChanged();
		}
//		((TextView) header.findViewById(R.id.header_text)).setText(datas.get(
//				position).getName());
		lastItem = position;
		
	}

	@Override
	public void configureNestedHeader(ViewGroup nestedHeader, View pinnedHeader) {
		// TODO Auto-generated method stub
	}

	private void loadData() {
		datas = new ArrayList<Person>();
		for (int i = 0; i < 25; i++) {
			Person p = new Person();
			p.setName("name-" + i);
			p.setNumber("100" + i+"\nasd\nfas\ndfas\ndfasdf\nasd\nfas\ndf\n"+"\n");
			datas.add(p);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (view instanceof PinnedHeaderListView) {
			((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

}
