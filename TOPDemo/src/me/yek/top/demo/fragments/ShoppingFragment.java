package me.yek.top.demo.fragments;

import me.yek.top.demo.R;
import me.yek.top.demo.api.client.TaobaoClient;
import me.yek.top.demo.util.LogUtil;
import me.yek.top.demo.util.MobileUtil;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShoppingFragment extends Fragment {
	
	public static final String title = "购物页面";
	private LoadWebViewTask task;
	private WebView mWebView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.d(this.getClass().getName(), " in onCreateView");
		View layout = inflater.inflate(R.layout.shopping, container, false);
		mWebView = (WebView) layout.findViewById(R.id.shopping);
		return layout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onActivityCreated");
		task = new LoadWebViewTask();
		task.execute("http://a.m.taobao.com/i10848312395.htm" +
				"?sid=" + "t" + MobileUtil.getDeviceId(getActivity().getApplicationContext()) +
				"&ttid=" + TaobaoClient.TTID + 
				"&imei=" + MobileUtil.getDeviceId(getActivity().getApplicationContext()) +
				"&imsi=" + MobileUtil.getSIMId(getActivity().getApplicationContext()));
	}

	@Override
	public void onPause() {
		LogUtil.d(this.getClass().getName(), " in onPause");
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(task != null) {
			task.cancel(true);
		}
	}
	
	private class LoadWebViewTask extends AsyncTask<String, Void, Void>{

		@Override
		protected void onPreExecute() {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(true);
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			if(mWebView != null) {
				mWebView.setWebViewClient(new TaobaoWebViewClient());
				mWebView.getSettings().setJavaScriptEnabled(true); 
				mWebView.getSettings().setBuiltInZoomControls(true);
				mWebView.loadUrl(params[0]);
			}
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
			super.onPageFinished(view, url);
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(true);
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}
		
	}

}
