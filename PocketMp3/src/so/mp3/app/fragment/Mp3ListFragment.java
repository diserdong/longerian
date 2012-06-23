package so.mp3.app.fragment;

import java.util.List;

import so.mp3.app.Host;
import so.mp3.app.widget.Mp3Adapter;
import so.mp3.http.SougouClient;
import so.mp3.http.parser.SearchParser;
import so.mp3.http.request.SearchRequest;
import so.mp3.http.response.SearchResponse;
import so.mp3.player.R;
import so.mp3.type.Mp3;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class Mp3ListFragment extends SherlockFragment {

	private static final String TAG = "Mp3ListFragment";
	
	private Host host;
	private OnSongSelectedListener listener;
	
	private SearchTask searchTask;
	
	private EditText query;
	private ImageButton search;
	private ListView songList;
	
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
	}
    
    @Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void refreshPage(List<Mp3> songs) {
		Mp3Adapter adapter = new Mp3Adapter(getActivity(),
				R.layout.song_item,
				R.id.title, 
				songs);
		songList.setAdapter(adapter);
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

	public interface OnSongSelectedListener {
        public void onSongSelected(List<Mp3> songs, int position);
    }
	
}
