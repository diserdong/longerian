package me.longerian.abcandroid;

import org.w3c.dom.Text;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.GridView;
import android.widget.TextView;

public class VideoViewTestActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

	private TextView emptyView;
	private GridView videoGridview;
	private VideoListAdapter videoListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_list);
		videoGridview = (GridView) findViewById(R.id.video_gridview);
		emptyView = (TextView) findViewById(R.id.empty);
		videoGridview.setEmptyView(emptyView);
		getSupportLoaderManager().initLoader(0, null, this);
		
//		String[] projection = new String[] {
//				MediaStore.Video.Media._ID,
//				MediaStore.Video.Media.DATA,
//				MediaStore.Video.Media.DISPLAY_NAME
//		};
//		Cursor data = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, 
//				MediaStore.Video.Media.DATE_ADDED +" DESC");
//		videoListAdapter = new VideoListAdapter(getApplicationContext(), data);
//		videoGridview.setAdapter(videoListAdapter);
//		startManagingCursor(data);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		String[] projection = new String[] {
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DATA,
				MediaStore.Video.Media.DISPLAY_NAME
		};
		CursorLoader loader = new CursorLoader(this.getApplicationContext(), 
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 
				projection, null, null, 
				MediaStore.Video.Media.DATE_ADDED +" DESC");
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if(videoListAdapter == null) {
			videoListAdapter = new VideoListAdapter(getApplicationContext(), data);
			if(data != null && data.getCount() == 0) {
				
			}
			videoGridview.setAdapter(videoListAdapter);
		} else {
			if(data != null && !data.isClosed()) {
				videoListAdapter.swapCursor(data);
			} else {
				getSupportLoaderManager().restartLoader(0, null, this);
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> data) {
		if(videoListAdapter != null) {
			videoListAdapter.swapCursor(null);
		}
	}

}
