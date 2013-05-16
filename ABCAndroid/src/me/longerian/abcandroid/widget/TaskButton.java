package me.longerian.abcandroid.widget;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskButton extends LinearLayout {

	public TaskButton(Context context) {
		super(context);
		init(context);
	}
	
	public TaskButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		this.setBackgroundResource(R.drawable.btn_download_selector);
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setGravity(Gravity.CENTER);
	}
	
	public void onReadyToDownload(double apkSize) {
		this.removeAllViews();
		ImageView icDownload = new ImageView(getContext());
		icDownload.setImageResource(R.drawable.ic_download);
		LinearLayout.LayoutParams icDownloadLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		icDownloadLp.setMargins(3, 0, 3, 0);
		this.addView(icDownload, icDownloadLp);
		
		ImageView spliter = new ImageView(getContext());
		spliter.setImageResource(R.drawable.download_divider);
		LinearLayout.LayoutParams spliterLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		spliterLp.setMargins(3, 0, 3, 0);
		this.addView(spliter, spliterLp);
		
		TextView size = new TextView(getContext());
		size.setText(String.format("%1$.2fM", apkSize / 1024));
		size.setTextColor(Color.rgb(255, 255, 255));
		LinearLayout.LayoutParams sizeLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		sizeLp.setMargins(3, 0, 3, 0);
		this.addView(size, sizeLp);
	}
	
	public void onReadyToUpgrade(double apkSize) {
		this.removeAllViews();
		TextView note = new TextView(getContext());
		note.setText("升级");
		note.setTextColor(Color.rgb(255, 255, 255));
		LinearLayout.LayoutParams noteLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		noteLp.setMargins(3, 0, 3, 0);
		this.addView(note, noteLp);
		
		ImageView spliter = new ImageView(getContext());
		spliter.setImageResource(R.drawable.download_divider);
		LinearLayout.LayoutParams spliterLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		spliterLp.setMargins(3, 0, 3, 0);
		this.addView(spliter, spliterLp);
		
		TextView size = new TextView(getContext());
		size.setText(String.format("%1$.2fM", apkSize / 1024));
		size.setTextColor(Color.rgb(255, 255, 255));
		LinearLayout.LayoutParams sizeLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		sizeLp.setMargins(3, 0, 3, 0);
		this.addView(size, sizeLp);
	}
	
	public void onDownloadComplete() {
		this.removeAllViews();
		TextView note = new TextView(getContext());
		note.setText("安装");
		note.setTextColor(Color.rgb(255, 255, 255));
		LinearLayout.LayoutParams noteLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		noteLp.setMargins(3, 0, 3, 0);
		this.addView(note, noteLp);
	}
	
	public void onReadyToOpen() {
		this.removeAllViews();
		TextView note = new TextView(getContext());
		note.setText("打开");
		note.setTextColor(Color.rgb(255, 255, 255));
		LinearLayout.LayoutParams noteLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		noteLp.setMargins(3, 0, 3, 0);
		this.addView(note, noteLp);
	}
	
}
