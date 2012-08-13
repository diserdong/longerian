package so.mp3.app.fragment;

import java.util.ArrayList;

import so.mp3.app.player.BasicPlayerService.IndicatorListener;
import so.mp3.app.player.LocalTrackPlayer;
import so.mp3.app.player.LocalTrackPlayer.LocalPlayerBinder;
import so.mp3.app.widget.LocalTrackAdapter;
import so.mp3.player.R;
import so.mp3.type.LocalTrack;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class LocalTrackListFragment extends SherlockFragment implements LoaderManager.LoaderCallbacks<Cursor>, IndicatorListener {

	private static final String TAG = "LocalTrackListFragment";
	private ListView songList;
	private LocalTrackAdapter mAdapter;
	public static final String[] MUSIC_SUMMARY_PROJECTION = new String[] {
		MediaStore.Audio.Media._ID,
		MediaStore.Audio.Media.TITLE,
		MediaStore.Audio.Media.ALBUM,
		MediaStore.Audio.Media.ARTIST,
		MediaStore.Audio.Media.SIZE,
		MediaStore.Audio.Media.DURATION,
    };
	
	private LocalTrackPlayer playerService;
    private ServiceConnection playerServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder service) {
            LocalPlayerBinder playerBinder = (LocalPlayerBinder)service;
            playerService = playerBinder.getService();
            Log.d(TAG, "service connected: " + arg0.toShortString() + "/" + playerService);
            Cursor data = mAdapter.getCursor();
            bindCursorDataToPlayer(data);
            playerService.setIndicatorListener(LocalTrackListFragment.this);
            if(playerService.isActive()) {
            	restoreUI(playerService.getCurrentTrackPosition());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        	Log.d(TAG, "service disconnected: " + arg0.toShortString());
        }
        
        private void bindCursorDataToPlayer(Cursor data) {
        	if(data.getCount() > 0) {
        		ArrayList<LocalTrack> tracks = new ArrayList<LocalTrack>();
    			data.moveToFirst();
    			while(!data.isAfterLast()) {
    				tracks.add(buildLocalTrackList(data));
    				data.moveToNext();
    			}
    			playerService.addTracks(tracks);
    		}
    	}
    	
    	private LocalTrack buildLocalTrackList(Cursor data) {
    		LocalTrack mp3 = new LocalTrack();
    		mp3.setId(data.getInt(data.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
    		mp3.setTitle(data.getString(data.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
    		mp3.setAlbum(data.getString(data.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
    		mp3.setArtist(data.getString(data.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
    		mp3.setSize(data.getInt(data.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
    		mp3.setDuration(data.getInt(data.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
    		return mp3;
    	}
    };
	
	public static LocalTrackListFragment newInstance() {
		LocalTrackListFragment f = new LocalTrackListFragment();
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
				if(playerService != null) {
					playerService.playTrack(position);
				}
			}
		});
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new LocalTrackAdapter(getActivity(),
                R.layout.song_item, null,
                MUSIC_SUMMARY_PROJECTION,
                new int[] {R.id.no, R.id.title, R.id.album, R.id.singer, R.id.size, R.id.duration}, 0);
       songList.setAdapter(mAdapter);
       getLoaderManager().initLoader(0, null, LocalTrackListFragment.this);
	}

    @Override
	public void onDestroyView() {
		super.onDestroyView();
	}
    
    @Override
	public void onDestroy() {
		super.onDestroy();
        getSherlockActivity().getApplicationContext().unbindService(playerServiceConnection);
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
		Intent playerServiceIntent = new Intent(getSherlockActivity(), LocalTrackPlayer.class);
	    getSherlockActivity().getApplicationContext().bindService(playerServiceIntent, playerServiceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	private void restoreUI(int position) {
		mAdapter.updateIndicator(position);
	}

	@Override
	public void onPlay(int position) {
		restoreUI(position);
	}
}
