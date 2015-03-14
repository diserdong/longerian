package me.longerian.abcandroid;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class VideoListAdapter extends CursorAdapter {

	private LayoutInflater mInflater;
	
	public VideoListAdapter(Context context, Cursor c) {
		super(context, c, false);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		if (mInflater == null) {
			mInflater = LayoutInflater.from(context);
		}
		View view = this.mInflater.inflate(R.layout.item_video,
				parent, false);
		ViewHolder holder = new ViewHolder();
		holder.video = (ImageView) view.findViewById(R.id.screenshot);
		holder.video.setImageResource(R.drawable.tencent_q);
		view.setTag(holder);
		return view;
	}
	
	public class ViewHolder {

		ImageView video;

	}

	
}
