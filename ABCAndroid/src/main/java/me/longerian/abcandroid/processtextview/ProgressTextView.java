package me.longerian.abcandroid.processtextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ProgressTextView extends TextView implements Runnable {

	private static final String ONE_DOT = ".  ";
	private static final String TWO_DOT = ".. ";
	private static final String THREE_DOT = "...";
	private static final long DELAY = 500L;
	private int count = 1;
	private String orgText;
	
	public ProgressTextView(Context context) {
		super(context);
		init();
	}
	
	public ProgressTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public ProgressTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		orgText = getText().toString();
		setText(orgText + THREE_DOT);
	}
	
	public void startProgress() {
		post(this);
	}

	@Override
	public void run() {
		String text = orgText;
		switch(count % 3) {
		case 0:
			text = text + THREE_DOT;
			break;
		case 1:
			text = text + ONE_DOT;
			break;
		case 2:
			text = text + TWO_DOT;
			break;
		}
		count++;
		setText(text);
		postDelayed(this, DELAY);
	}
	
	public void stopProgress() {
		removeCallbacks(this);
	}
	
}
