package so.mp3.app.widget;

import java.util.List;

import so.mp3.player.R;
import so.mp3.type.OnlineTrack;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OnlineTrackAdapter extends ArrayAdapter<OnlineTrack> {

	public interface OnOpenSongOptionListener {
		public void onOpenOption(int position);
	}
	
	private OnOpenSongOptionListener listener;
	protected List<OnlineTrack> items;
	protected Context ctx;
	protected int resource;
	private int currentPosition = -1;
	
	public OnlineTrackAdapter(Context context, int resource,
			int textViewResourceId, List<OnlineTrack> items, OnOpenSongOptionListener listener) {
		super(context, resource, textViewResourceId, items);
		this.ctx = context;
		this.resource = resource;
		this.items = items;
		this.listener = listener;
	}
	
	public List<OnlineTrack> getItems() {
		return items;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(ctx).inflate(resource, null);
			viewHolder.no = (TextView) convertView.findViewById(R.id.no);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.singer = (TextView) convertView.findViewById(R.id.singer);
			viewHolder.size = (TextView) convertView.findViewById(R.id.size);
			viewHolder.album = (TextView) convertView.findViewById(R.id.album);
			viewHolder.more = (ImageView) convertView.findViewById(R.id.more);
			viewHolder.indicator = (ImageView) convertView.findViewById(R.id.indicator);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		OnlineTrack m = items.get(position);
		viewHolder.no.setText(position + 1 + "");
		viewHolder.title.setText(m.getTitle());
		viewHolder.singer.setText(m.getArtist());
		viewHolder.size.setText(m.getSize());
		viewHolder.album.setText(m.getAlbum());
		viewHolder.more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onOpenOption(position);
			}
		});
		viewHolder.indicator.setVisibility((position == currentPosition) ? View.VISIBLE : View.GONE);
		return convertView;
	}
	
	private class ViewHolder {
		
		public TextView no;
		public TextView title;
		public TextView singer;
		public TextView size;
		public TextView album;
		public ImageView more;
		public ImageView indicator;
		
	}

	public void updateIndicator(int position) {
		currentPosition = position;
		notifyDataSetChanged();
	}
	
}
