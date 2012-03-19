package me.yek.top.demo.fragments;

import me.yek.top.demo.R;
import me.yek.top.demo.api.client.TaobaoClient;
import me.yek.top.demo.api.parser.UserGetParser;
import me.yek.top.demo.api.pojo.User;
import me.yek.top.demo.api.request.UserGetRequest;
import me.yek.top.demo.api.response.UserGetResponse;
import me.yek.top.demo.util.LogUtil;
import me.yek.top.demo.util.PreferenceUtil;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

	public static final String title = "个人信息";
	private SharedPreferences pref;
	private UserGetTask task;
	private User user;
	
	private TextView nick;
	private TextView userId;
	private TextView uid;
	private TextView created;
	private TextView lastVisit;
	private TextView type;
	private TextView goodNum;
	private TextView level;
	private TextView score;
	private TextView totalNum;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtil.d(this.getClass().getName(), " in onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onCreate");
		pref = getActivity().getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE);
		task = new UserGetTask();
		UserGetRequest rqst = new UserGetRequest();
		rqst.setBuyerNick(PreferenceUtil.getUserName(pref));
		task.execute(rqst);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.d(this.getClass().getName(), " in onCreateView");
		View layout = inflater.inflate(R.layout.profile, container, false); 
		nick = (TextView) layout.findViewById(R.id.nick);
		userId = (TextView) layout.findViewById(R.id.user_id);
		uid = (TextView) layout.findViewById(R.id.uid);
		created = (TextView) layout.findViewById(R.id.created);
		lastVisit = (TextView) layout.findViewById(R.id.last_visit);
		type  = (TextView) layout.findViewById(R.id.type);
		goodNum = (TextView) layout.findViewById(R.id.good_num);
		level  = (TextView) layout.findViewById(R.id.level);
		score = (TextView) layout.findViewById(R.id.score);
		totalNum  = (TextView) layout.findViewById(R.id.total_num);
		if(user != null) {
			nick.setText(user.getNick());
			userId.setText(String.valueOf(user.getUserId()));
			uid.setText(user.getUid());
			created.setText(user.getCreated().toString());
			lastVisit.setText(user.getLastVisit().toString());
			type.setText(user.getType());
			goodNum.setText(String.valueOf(user.getBuyerCredit().getGoodNum()));
			level.setText(String.valueOf(user.getBuyerCredit().getLevel()));
			score.setText(String.valueOf(user.getBuyerCredit().getScore()));
			totalNum.setText(String.valueOf(user.getBuyerCredit().getTotalNum()));
		}
		return layout; 
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtil.d(this.getClass().getName(), " in onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtil.d(this.getClass().getName(), " in onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.d(this.getClass().getName(), " in onPause");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		LogUtil.d(this.getClass().getName(), " in onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtil.d(this.getClass().getName(), " in onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.d(this.getClass().getName(), " in onDestroy");
		if(task != null) {
			task.cancel(true);
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		LogUtil.d(this.getClass().getName(), " in onDetach");
	}

	private class UserGetTask extends AsyncTask<UserGetRequest, Void, UserGetResponse>{

		@Override
		protected void onPreExecute() {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(true);
			}
		}

		@Override
		protected UserGetResponse doInBackground(UserGetRequest... request) {
			return (UserGetResponse) new TaobaoClient(getActivity().getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE)).excute(request[0], new UserGetParser());
		}

		@Override
		protected void onPostExecute(UserGetResponse result) {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
			if(result.getUser() != null) {
				user = result.getUser();
			}
		}
		
	}
	
}
