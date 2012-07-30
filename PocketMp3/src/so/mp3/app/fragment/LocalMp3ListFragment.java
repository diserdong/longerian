package so.mp3.app.fragment;

import so.mp3.app.widget.LocalMp3Adapter;
import so.mp3.player.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class LocalMp3ListFragment extends SherlockFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private ListView songList;
	private SimpleCursorAdapter mAdapter;
	public static final String[] MUSIC_SUMMARY_PROJECTION = new String[] {
		MediaStore.Audio.Media._ID,
		MediaStore.Audio.Media.TITLE,
		MediaStore.Audio.Media.ALBUM,
		MediaStore.Audio.Media.ARTIST,
		MediaStore.Audio.Media.SIZE,
		MediaStore.Audio.Media.DURATION,
    };
	
	public static LocalMp3ListFragment newInstance() {
		LocalMp3ListFragment f = new LocalMp3ListFragment();
        return f;
    }
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.local_songs_layout, null);
    	songList = (ListView) view.findViewById(R.id.songs);
    	songList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//		        listener.onSongSelected((Mp3) songList.getItemAtPosition(position));
			}
		});
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new LocalMp3Adapter(getActivity(),
                R.layout.song_item, null,
                MUSIC_SUMMARY_PROJECTION,
                new int[] {R.id.no, R.id.title, R.id.album, R.id.singer, R.id.size, R.id.duration}, 0);
        songList.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
	}

    @Override
	public void onDestroyView() {
		super.onDestroyView();
	}
    
    @Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        String select = MediaStore.Audio.Media.DATA + " LIKE '" + "%PocketMp3/Downloads/%" + "'";
        return new CursorLoader(getActivity(), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        		MUSIC_SUMMARY_PROJECTION, select, null,
                null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
	
}
