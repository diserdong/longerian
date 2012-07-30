package so.mp3.app.fragment;

import so.mp3.player.R;
import so.mp3.type.OnlineMp3;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;

public class SongOptionDialogFragment extends SherlockDialogFragment implements OnClickListener {

	public interface OnOptionSelectedListener {
		
		public void onDownloadOptionSelected(OnlineMp3 mp3);
		public void onShareOptionSelected(OnlineMp3 mp3);
		
	}

	private OnOptionSelectedListener mListener;
	private OnlineMp3 mp3;
	
	public static SongOptionDialogFragment newInstance(OnlineMp3 mp3) {
		SongOptionDialogFragment f = new SongOptionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("mp3", mp3);
        f.setArguments(args);
        return f;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnOptionSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnOptionSelectedListener");
        }
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mp3 = getArguments().getParcelable("mp3");
		setStyle(STYLE_NO_TITLE, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.song_option, null);
		TextView download = (TextView) view.findViewById(R.id.action_download);
		TextView share = (TextView) view.findViewById(R.id.action_share);
		download.setOnClickListener(this);
		share.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.action_download:
			mListener.onDownloadOptionSelected(mp3);
			break;
		case R.id.action_share:
			mListener.onShareOptionSelected(mp3);
			break;
		default:	
			break;
		}
		dismiss();
	}

}
