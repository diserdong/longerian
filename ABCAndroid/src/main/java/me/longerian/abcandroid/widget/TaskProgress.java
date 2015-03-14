package me.longerian.abcandroid.widget;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TaskProgress extends FrameLayout {

	private ProgressBar progress;
	private TextView indicator;
	private float textSize = 16;
	
	public TaskProgress(Context context) {
		super(context);
		init(context, null);
	}
	
	public TaskProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	public TaskProgress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TaskProgress);
		float value = a.getDimension(R.styleable.TaskProgress_textSize, 16f);
		Log.d(VIEW_LOG_TAG, value + "");
		if(value > 0) {
			textSize = value;
		}
		a.recycle();
		    
		this.progress = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		this.progress.setMax(100);
		this.progress.setIndeterminate(false);
		this.progress.setProgressDrawable(context.getResources().getDrawable(R.drawable.download_progressbar));
		this.addView(progress, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		
		this.indicator = new TextView(context);
		this.indicator.setTextColor(Color.rgb(255, 255, 255));
		this.indicator.setGravity(Gravity.CENTER);
		this.indicator.setTextSize(textSize);
		this.addView(indicator, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
	}
	
	public void setProgress(int progressValue) {
		if(progressValue < 0) {
			progressValue = 0;
		}
		if(progressValue > 100) {
			progressValue = 100;
		}
		if(progress != null) {
			progress.setProgress(progressValue);
		}
		if(indicator != null) {
			String rawPercent = getContext().getString(R.string.progress_relative);
			indicator.setText(String.format(rawPercent, progressValue));
		}
	}
	
	public void stopProgress() {
		if(indicator != null) {
			indicator.setText("continue");
		}
	}

}
