package so.mp3.app.widget;

import java.util.List;

import so.mp3.player.R;
import so.mp3.type.OnlineMp3;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OnlineMp3Adapter extends ArrayAdapter<OnlineMp3> {

	public interface OnOpenSongOptionListener {
		public void onOpenOption(int position);
	}
	
	private OnOpenSongOptionListener listener;
	protected List<OnlineMp3> items;
	protected Context ctx;
	protected int resource;
	
	public OnlineMp3Adapter(Context context, int resource,
			int textViewResourceId, List<OnlineMp3> items, OnOpenSongOptionListener listener) {
		super(context, resource, textViewResourceId, items);
		this.ctx = context;
		this.resource = resource;
		this.items = items;
		this.listener = listener;
	}
	
	public List<OnlineMp3> getItems() {
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
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		OnlineMp3 m = items.get(position);
		viewHolder.no.setText(position + 1 + "");
		viewHolder.title.setText(m.getTitle());
		viewHolder.singer.setText(m.getSinger());
		viewHolder.size.setText(m.getSize());
		viewHolder.album.setText(m.getAlbum());
		viewHolder.more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onOpenOption(position);
			}
		});
		return convertView;
	}
	
	private class ViewHolder {
		
		public TextView no;
		public TextView title;
		public TextView singer;
		public TextView size;
		public TextView album;
		public ImageView more;
		
	}
	
}
