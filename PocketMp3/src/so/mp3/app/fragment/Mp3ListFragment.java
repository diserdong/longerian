package so.mp3.app.fragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import so.mp3.app.Host;
import so.mp3.app.MusicPlayer;
import so.mp3.app.widget.Mp3Adapter;
import so.mp3.http.SougouClient;
import so.mp3.http.parser.DownloadLinkParser;
import so.mp3.http.parser.SearchParser;
import so.mp3.http.request.DownloadLinkRequest;
import so.mp3.http.request.SearchRequest;
import so.mp3.http.response.DownloadLinkResponse;
import so.mp3.http.response.SearchResponse;
import so.mp3.player.R;
import so.mp3.type.Mp3;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class Mp3ListFragment extends SherlockFragment implements Observer {

	private static final String TAG = "SearchResultListFragment";
	public static final String NODE = "node";
	
	private Host host;
	private OnSongSelectedListener listener;
	
	private MusicPlayer mp;
	
	private SearchTask searchTask;
	private DownloadLinkTask downloadLinkTask;
	private PlayTask playTask;
	
	private EditText query;
	private ImageButton search;
	private ListView songList;
	
	private TextView title;
	private ImageButton playOrPause;
	private ImageButton download;
	private SeekBar progress;
	
	private Mp3 currentMp3;
	
	private long enqueue;
    private DownloadManager dm;
	
	public static Mp3ListFragment newInstance() {
		Mp3ListFragment f = new Mp3ListFragment();
        return f;
    }
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnSongSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSongSelectedListener");
        }
        try {
        	host = (Host) activity;
        } catch (ClassCastException e) {
        	throw new ClassCastException(activity.toString() + " must implement Host");
        }
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mp = new MusicPlayer();
	    mp.addObserver(this);
	    dm = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
	    getActivity().registerReceiver(downloadCompletionReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.songs_layout, null);
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
		        listener.onSongSelected(((Mp3Adapter) (parent.getAdapter())).getItems(), 
		        		position);
		        prepareMp3((Mp3) parent.getItemAtPosition(position));
			}
		});
    	title = (TextView) view.findViewById(R.id.title);
    	playOrPause = (ImageButton) view.findViewById(R.id.play_or_pause);
    	playOrPause.setEnabled(false);
        playOrPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mp.isPlaying()) {
					pause();
				} else {
					start();
				}
			}
		});
        progress = (SeekBar) view.findViewById(R.id.progress);
        progress.setEnabled(false);
        download = (ImageButton) view.findViewById(R.id.download);
        download.setEnabled(false);
        download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentMp3 != null && currentMp3.getMp3Link() != null) {
					download(currentMp3);
				}
			}
		});
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
		if(playTask != null) {
			playTask.cancel(true);
		}
	}
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		mp.release();
		mp.deleteObserver(this);
		getActivity().unregisterReceiver(downloadCompletionReceiver);
	}

	private void refreshPage(List<Mp3> songs) {
		Mp3Adapter adapter = new Mp3Adapter(getActivity(),
				R.layout.song_item,
				R.id.title, 
				songs);
		songList.setAdapter(adapter);
	}
	
	private void prepareMp3(Mp3 music) {
		currentMp3 = music;
		title.setText(music.getTitle());
		if(TextUtils.isEmpty(music.getMp3Link())) {
			downloadLinkTask = new DownloadLinkTask();
			downloadLinkTask.execute(music.getPlayerLink());
		} else {
			prepareDownloadAndPlay(music.getMp3Link());
		}
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
				}
			}
		}
		
	}
	
	private class DownloadLinkTask extends AsyncTask<String, Void, DownloadLinkResponse> {

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
					Toast.makeText(getActivity(), R.string.search_result_empty, Toast.LENGTH_LONG).show();
				} else {
					prepareDownloadAndPlay(result.getLink());
				}
			}
		}
		
	}
	
	private class PlayTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			host.showIndeterminateProgressBar();
		}

		@Override
		protected Void doInBackground(String... params) {
			mp.play(params[0]);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(mp.isPlaying()) {
				playOrPause.setImageResource(R.drawable.av_pause);
				playOrPause.setEnabled(true);
				progress.setEnabled(true);
			}
			host.hideIndeterminateProgressBar();
		}
		
	}
	
	private void prepareDownloadAndPlay(String link) {
		currentMp3.setMp3Link(link);
		playOrPause.setImageResource(R.drawable.av_play);
		playOrPause.setEnabled(false);
		progress.setEnabled(false);
		download.setEnabled(true);
		play(link);
	}
	
	private void play(String link) {
		playTask = new PlayTask();
		playTask.execute(link);
	}
	
	private void start() {
		playOrPause.setImageResource(R.drawable.av_pause);
		mp.start();
	}
	
	private void pause() {
		playOrPause.setImageResource(R.drawable.av_play);
		mp.pause();
	}
	
	private void stop() {
		playOrPause.setImageResource(R.drawable.av_play);
		progress.setProgress(0);
		playOrPause.setEnabled(false);
		progress.setEnabled(false);
		mp.stop();
	}
	
	private void complete() {
		playOrPause.setImageResource(R.drawable.av_play);
		progress.setProgress(0);
	}
	
	private void download(Mp3 mp3) {//TODO 存到自定义的文件夹
		Request request = new Request(Uri.parse(mp3.getMp3Link()));
		request.setTitle(mp3.getTitle())
        .setDescription(mp3.getSinger() + "/" + mp3.getAlbum())
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
        		mp3.getTitle() + "-" + mp3.getSinger() + ".mp3");
	    enqueue = dm.enqueue(request);
	}
	
	private void updateMusicProgress(SeekBar progress, int current, int bufferPercent, int duration) {
    	progress.setSecondaryProgress(bufferPercent);
        int currentPercent = progress.getMax() * current / duration;
        progress.setProgress(currentPercent);
    }

	@Override
	public void update(Observable observable, Object data) {
		Bundle b = (Bundle) data;
		if(b.getBoolean("completion", false)) {
			complete();
			return ;
		}
		if(b.getBoolean("error", false)) {
			Toast.makeText(getActivity(), R.string.song_available, Toast.LENGTH_LONG).show();
			return ;
		}
		updateMusicProgress(progress, b.getInt("current"), b.getInt("bufferPercent"), b.getInt("duration"));
	}

	public interface OnSongSelectedListener {
        public void onSongSelected(List<Mp3> songs, int position);
    }
	
	BroadcastReceiver downloadCompletionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                Query query = new Query();
                query.setFilterById(downloadId);
                Cursor c = dm.query(query);
                if (c.moveToFirst()) {
                    Toast.makeText(getActivity(), statusMessage(c), Toast.LENGTH_LONG).show();
                }
            }
        }
        
        private String statusMessage(Cursor c) {//TODO 给予更详细的提示
            String msg="???";
            switch(c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
              case DownloadManager.STATUS_FAILED:
                msg="Download failed!" + c.getColumnIndex(DownloadManager.COLUMN_REASON);
                break;
              case DownloadManager.STATUS_PAUSED:
                msg="Download paused!" + c.getColumnIndex(DownloadManager.COLUMN_REASON);
                break;
              case DownloadManager.STATUS_PENDING:
                msg="Download pending!" + c.getColumnIndex(DownloadManager.COLUMN_REASON);
                break;
              case DownloadManager.STATUS_RUNNING:
                msg="Download in progress!" + c.getColumnIndex(DownloadManager.COLUMN_REASON);
                break;
              case DownloadManager.STATUS_SUCCESSFUL:
                msg="Download complete!" + c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                break;
              default:
                msg="Download is nowhere in sight";
                break;
            }
            
            return(msg);
          }
        
        private void showDownload() {
            Intent i = new Intent();
            i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
            startActivity(i);
        }
    };
	
}
