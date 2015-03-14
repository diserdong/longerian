package me.longerian.abcandroid.audioplay;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class PlayIndicator extends FrameLayout implements OnClickListener {

	public enum STATUS {
		PLAYING,
		IDLE
	}
	
	public interface OnStatusChangedListener {
		public void onStatusChanged(STATUS status);
	}
	
	private ImageView play;
	private ImageView stop;
	private ImageView playing;
	private Animation rotating;
	private STATUS currentStatus;
	private OnStatusChangedListener listener;
	
	public PlayIndicator(Context context) {
		super(context);
		init(context);
	}
	
	public PlayIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public PlayIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		this.rotating = AnimationUtils.loadAnimation(context, R.anim.push_progress_anim);
		this.rotating.setDuration(3 * 1000);
		this.rotating.setInterpolator(new LinearInterpolator());
		this.playing = new ImageView(context);
		this.playing.setImageResource(R.drawable.ic_playing_audio);
		this.addView(playing, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
		
		this.play = new ImageView(context);
		this.play.setImageResource(R.drawable.ic_play_audio_selector);
		this.play.setDuplicateParentStateEnabled(true);
		this.addView(play, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
		
		this.stop = new ImageView(context);
		this.stop.setImageResource(R.drawable.ic_stop_audio_selector);
		this.stop.setDuplicateParentStateEnabled(true);
		this.addView(stop, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
		
		this.setOnClickListener(this);
		setDefaultStatus();
	}
	
	private void setDefaultStatus() {
		this.currentStatus = STATUS.IDLE;
		this.play.setVisibility(View.VISIBLE);
		this.playing.setVisibility(View.INVISIBLE);
		this.stop.setVisibility(View.INVISIBLE);
	}
	
	private void swapToPlaying() {
		this.currentStatus = STATUS.PLAYING;
		this.play.setVisibility(View.INVISIBLE);
		this.stop.setVisibility(View.VISIBLE);
		this.playing.setVisibility(View.VISIBLE);
		this.rotating.reset();
		this.rotating.startNow();
		this.playing.startAnimation(rotating);
	}
	
	private void swapToIdle() {
		this.currentStatus = STATUS.IDLE;
		this.play.setVisibility(View.VISIBLE);
		this.playing.setVisibility(View.INVISIBLE);
		this.stop.setVisibility(View.INVISIBLE);
		this.rotating.cancel();
		this.playing.clearAnimation();
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		//do nothing
		if(l.equals(this)) {
			super.setOnClickListener(l);
		} 
	}

	public void setOnStatusChangedListener(OnStatusChangedListener l) {
		listener = l;
	}
	
	@Override
	public void onClick(View v) {
		if(currentStatus == STATUS.IDLE) {
			swapToPlaying();
		} else {
			swapToIdle();
		}
		if(listener != null) {
			listener.onStatusChanged(currentStatus);
		}
			
	}

}
