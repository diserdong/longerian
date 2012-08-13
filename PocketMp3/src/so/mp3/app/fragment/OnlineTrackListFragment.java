package so.mp3.app.fragment;

import java.util.ArrayList;
import java.util.List;

import so.mp3.app.Host;
import so.mp3.app.downloader.DownloadService;
import so.mp3.app.fragment.TrackOptionDialogFragment.OnOptionSelectedListener;
import so.mp3.app.player.BasicPlayerService.IndicatorListener;
import so.mp3.app.player.OnlineTrackPlayer;
import so.mp3.app.player.OnlineTrackPlayer.OnPreparePlayListener;
import so.mp3.app.player.OnlineTrackPlayer.OnlinePlayerBinder;
import so.mp3.app.widget.OnlineTrackAdapter;
import so.mp3.app.widget.OnlineTrackAdapter.OnOpenSongOptionListener;
import so.mp3.http.SougouClient;
import so.mp3.http.parser.DownloadLinkParser;
import so.mp3.http.parser.SearchParser;
import so.mp3.http.request.DownloadLinkRequest;
import so.mp3.http.request.SearchRequest;
import so.mp3.http.response.DownloadLinkResponse;
import so.mp3.http.response.SearchResponse;
import so.mp3.player.R;
import so.mp3.type.OnlineTrack;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class OnlineTrackListFragment extends SherlockFragment implements OnOpenSongOptionListener, 
	IndicatorListener, OnOptionSelectedListener, OnPreparePlayListener {

	private static final String TAG = "OnlineTrackListFragment";

	private enum PostDownlaodLinkAction {
		SHARE,
		DOWNLOAD,
		PLAY
	};
	
	private Host host;
	
	private SearchTask searchTask;
	private DownloadLinkTask downloadLinkTask;
	
	private EditText query;
	private ImageButton search;
	private ListView songList;
	private OnlineTrackAdapter mAdapter;
	
	private OnlineTrackPlayer playerService;
    private ServiceConnection playerServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder service) {
            OnlinePlayerBinder playerBinder = (OnlinePlayerBinder)service;
            playerService = playerBinder.getService();
            playerService.setIndicatorListener(OnlineTrackListFragment.this);
            playerService.setPreparePlayListener(OnlineTrackListFragment.this);
            if(playerService.isActive()) {
            	restoreUI(playerService.getCurrentTrackPosition());
            }
            Log.d(TAG, "service connected: " + arg0.toShortString() + "/" + playerService);
        }

		@Override
        public void onServiceDisconnected(ComponentName arg0) {
        	Log.d(TAG, "service disconnected: " + arg0.toShortString());
        }
    };
    
	public static OnlineTrackListFragment newInstance() {
		OnlineTrackListFragment f = new OnlineTrackListFragment();
        return f;
    }
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        	host = (Host) activity;
        } catch (ClassCastException e) {
        	throw new ClassCastException(activity.toString() + " must implement Host");
        }
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent playerServiceIntent = new Intent(getSherlockActivity(), OnlineTrackPlayer.class);
	    getSherlockActivity().getApplicationContext().bindService(playerServiceIntent, playerServiceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.online_songs_layout, null);
    	query = (EditText) view.findViewById(R.id.query);
    	search = (ImageButton) view.findViewById(R.id.search);
    	search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(query.getEditableText().toString())) {
					searchTask = new SearchTask();
					searchTask.execute(query.getEditableText().toString().trim());
				}
			}
		});
    	songList = (ListView) view.findViewById(R.id.songs);
    	songList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				play(position);
			}
		});
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(searchTask != null) {
			searchTask.cancel(true);
		}
		if(downloadLinkTask != null) {
			downloadLinkTask.cancel(true);
		}
	}
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		getSherlockActivity().getApplicationContext().unbindService(playerServiceConnection);
	}

	private void refreshPage(List<OnlineTrack> songs) {
		mAdapter = new OnlineTrackAdapter(getActivity(),
				R.layout.song_item,
				R.id.title, 
				songs,
				this);
		songList.setAdapter(mAdapter);
	}
	
	private class SearchTask extends AsyncTask<String, Void, SearchResponse> {

		@Override
		protected void onPreExecute() {
			host.showIndeterminateProgressBar();
		}

		@Override
		protected SearchResponse doInBackground(String... params) {
			SearchRequest sr = new SearchRequest();
			sr.setQuery(params[0]);
			SougouClient sc = new SougouClient();
			SearchResponse resp = (SearchResponse) sc.excute(sr, new SearchParser());
			return resp;
		}
		
		@Override
		protected void onPostExecute(SearchResponse result) {
			host.hideIndeterminateProgressBar();
			if(result.isNetworkException()) {
				Toast.makeText(getActivity(), R.string.network_wonky, Toast.LENGTH_LONG).show();
			} else {
				if(result.getMp3s().isEmpty()) {
					Toast.makeText(getActivity(), R.string.search_result_empty, Toast.LENGTH_LONG).show();
				} else {
					refreshPage(result.getMp3s());
					bindDataToPlayer(result.getMp3s());
				}
			}
		}

	}
	
	private void play(int position) {
		if(playerService != null) {
			playerService.playTrack(position);
		}
	}

	@Override
	public void onDownloadOptionSelected(OnlineTrack mp3) {
		if(TextUtils.isEmpty(mp3.getMp3Link())) {
			downloadLinkTask = new DownloadLinkTask(PostDownlaodLinkAction.DOWNLOAD, mp3);
			downloadLinkTask.execute(mp3.getPlayerLink());
		} else {
			download(mp3);
		}
	}
	
	private void download(OnlineTrack mp3) {
	    getActivity().startService(new Intent(getActivity(), DownloadService.class)
        	.putExtra(DownloadService.TARGET_URL, mp3.getMp3Link())
        	.putExtra(DownloadService.TARGET_NAME, mp3.getTitle() + "-" + mp3.getArtist() + ".mp3"));
	}

	@Override
	public void onShareOptionSelected(OnlineTrack mp3) {
		if(TextUtils.isEmpty(mp3.getMp3Link())) {
			downloadLinkTask = new DownloadLinkTask(PostDownlaodLinkAction.SHARE, mp3);
			downloadLinkTask.execute(mp3.getPlayerLink());
		} else {
			shareMp3(mp3);
		}
	}
	
	private void shareMp3(OnlineTrack mp3) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mp3.getTitle());
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mp3.getAlbum() + "\n" + mp3.getArtist() + "\n" + mp3.getMp3Link());
		startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
	}

	private class DownloadLinkTask extends AsyncTask<String, Void, DownloadLinkResponse> {

		private PostDownlaodLinkAction action;
		private OnlineTrack track;
		
		public DownloadLinkTask(PostDownlaodLinkAction action, OnlineTrack orgTrack) {
			this.action = action;
			this.track = orgTrack;
		}
		
		@Override
		protected void onPreExecute() {
			host.showIndeterminateProgressBar();
		}

		@Override
		protected DownloadLinkResponse doInBackground(String... params) {
			SougouClient sc = new SougouClient();
			DownloadLinkRequest dlr = new DownloadLinkRequest();
			dlr.setLink(params[0]);
			DownloadLinkResponse resp = (DownloadLinkResponse) sc.excute(dlr, new DownloadLinkParser());
			return resp;
		}
		
		@Override
		protected void onPostExecute(DownloadLinkResponse result) {
			host.hideIndeterminateProgressBar();
			if(result.isNetworkException()) {
				Toast.makeText(getActivity(), R.string.network_wonky, Toast.LENGTH_LONG).show();
			} else {
				if(TextUtils.isEmpty(result.getLink())) {
					Toast.makeText(getActivity(), R.string.can_not_find_the_song, Toast.LENGTH_LONG).show();
				} else {
					track.setMp3Link(result.getLink());
					switch (action) {
					case SHARE:
						shareMp3(track);
						break;
					case DOWNLOAD:
						download(track);
						break;
					default:
						break;
					}
				}
			}
		}
		
	}
	
	@Override
	public void onOpenOption(int position) {
		TrackOptionDialogFragment optionDialog = TrackOptionDialogFragment.newInstance((OnlineTrack) songList.getItemAtPosition(position));
		optionDialog.setOnOptionSelectedListener(this);
		optionDialog.show(getFragmentManager(), "option");
	}
	
	private void bindDataToPlayer(List<OnlineTrack> onlineTracks) {
		ArrayList<OnlineTrack> tracks = new ArrayList<OnlineTrack>();
		for(OnlineTrack ot : onlineTracks) {
			tracks.add(ot);
		}
		playerService.addTracks(tracks);
	}

	@Override
	public void onPlay(int position) {
		restoreUI(position);
	}

	private void restoreUI(int position) {
		mAdapter.updateIndicator(position);
	}

	@Override
	public void onStartPreparePlay() {
		host.showIndeterminateProgressBar();
	}

	@Override
	public void onFinishPreparePlay() {
		host.hideIndeterminateProgressBar();
	}
	
}
