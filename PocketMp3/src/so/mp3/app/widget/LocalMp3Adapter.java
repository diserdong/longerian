package so.mp3.app.widget;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import so.mp3.player.R;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LocalMp3Adapter extends SimpleCursorAdapter {

	private DecimalFormat decimalFormater = new DecimalFormat("0.00");
	private SimpleDateFormat dateFormater0 = new SimpleDateFormat("hh:mm:ss");
	private SimpleDateFormat dateFormater1 = new SimpleDateFormat("mm:ss");
	
	public LocalMp3Adapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView no = (TextView) view.findViewById(R.id.no);
        if(no != null) no.setVisibility(View.INVISIBLE);
        TextView title = (TextView) view.findViewById(R.id.title);
		if(title != null) title.setText(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
		TextView singer = (TextView) view.findViewById(R.id.singer);
		if(singer != null) singer.setText(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
		TextView size = (TextView) view.findViewById(R.id.size);
		if(size != null) {
			int sizeInByte = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
			if(sizeInByte > 1024 * 1024) {
				size.setText(decimalFormater.format(((double) sizeInByte / 1024 / 1024 )) + "MB" );
			} else if(sizeInByte > 1024) {
				size.setText(decimalFormater.format(((double) sizeInByte / 1024 )) + "KB" );
			} else {
				size.setText(sizeInByte + "B" );
			}
		}
		TextView album = (TextView) view.findViewById(R.id.album);
		if(album != null) album.setText(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
		TextView duration = (TextView) view.findViewById(R.id.duration);
		if(duration != null) {
			duration.setVisibility(View.VISIBLE);
			long durationInSecond = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
			if(durationInSecond > 60 * 60 * 1000) {
				duration.setText(dateFormater0.format(new Date(durationInSecond)));
			} else {
				duration.setText(dateFormater1.format(new Date(durationInSecond)));
			}
		}
		ImageView more = (ImageView) view.findViewById(R.id.more);
		if(more != null) more.setVisibility(View.GONE);
	}
	
}
