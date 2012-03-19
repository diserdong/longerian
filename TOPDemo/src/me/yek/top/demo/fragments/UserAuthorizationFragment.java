package me.yek.top.demo.fragments;

import me.yek.top.demo.R;
import me.yek.top.demo.api.client.TaobaoClient;
import me.yek.top.demo.api.pojo.TopAuthorizationCallback;
import me.yek.top.demo.api.pojo.TopParameters;
import me.yek.top.demo.util.LogUtil;
import me.yek.top.demo.util.PreferenceUtil;
import me.yek.top.demo.util.SessionValidtionUtil;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UserAuthorizationFragment extends Fragment {

	private UserAuthorizationTask task;
	private WebView mWebView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.d(this.getClass().getName(), " in onCreateView");
		View layout = inflater.inflate(R.layout.authorization_fragment, container, false);
		return layout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onActivityCreated");
		mWebView = (WebView) getView().findViewById(R.id.authorization_view);
		mWebView.setWebViewClient(new TaobaoWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true); 
		mWebView.getSettings().setBuiltInZoomControls(true);
		doAuthorization();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(task != null) {
			task.cancel(true);
		}
	}
	
	private void doAuthorization() {
		task = new UserAuthorizationTask();
		task.execute();
	}
	
	private class UserAuthorizationTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(true);
			}
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			mWebView.loadUrl(TaobaoClient.sessionKeyUrl + "?appkey=" + TaobaoClient.appkey + "&encode=utf-8");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
		}
		
	}
	
	private class TaobaoWebViewClient extends WebViewClient {

		@Override
		public void onPageFinished(WebView view, String url) {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(true);
			}
			if(url.startsWith("http://localhost/")) {
				Uri callback = Uri.parse(url);
				TopAuthorizationCallback topAuthorization = new TopAuthorizationCallback();
				topAuthorization.setTopAppkey(callback.getQueryParameter("top_appkey"));
				topAuthorization.setTopParameters(callback.getQueryParameter("top_parameters"));
				topAuthorization.setTopSession(callback.getQueryParameter("top_session"));
				topAuthorization.setTopSign(callback.getQueryParameter("top_sign"));
				topAuthorization.setEncode(callback.getQueryParameter("encode"));
				LogUtil.d(this.getClass().getName(), topAuthorization.toString());
				if(SessionValidtionUtil.validateTopAuthorizationCallback(topAuthorization)) {
					SharedPreferences pref = getActivity().getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE);
					PreferenceUtil.setSession(pref, topAuthorization.getTopSession());
					TopParameters topParameters = SessionValidtionUtil.convertMap2TopParameters(SessionValidtionUtil.convertBase64String2Map(topAuthorization.getTopParameters()));
					PreferenceUtil.setExpiresIn(pref, topParameters.getExpiresIn());
					PreferenceUtil.setRefreshToken(pref, topParameters.getRefreshToken());
					PreferenceUtil.setReExpiresIn(pref, topParameters.getReExpiresIn());
					PreferenceUtil.printSharedPreference(pref);
					getActivity().setResult(Activity.RESULT_OK);
					getActivity().finish();
				}
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
		}
		
	}
	
}
