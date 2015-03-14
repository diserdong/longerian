package me.longerian.abcandroid.audio;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class CustomAudioPlayer extends Activity implements
		OnCompletionListener, OnTouchListener, OnClickListener {

	private MediaPlayer mediaPlayer;
	private View theView;
	private Button stopButton, startButton;
	private int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dj);
		stopButton = (Button) this.findViewById(R.id.StopButton);
		startButton = (Button) this.findViewById(R.id.StartButton);
		startButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		theView = this.findViewById(R.id.theview);
		theView.setOnTouchListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mediaPlayer = MediaPlayer.create(getApplicationContext(),
				R.raw.ab_cave_ambient);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mediaPlayer.stop();
		mediaPlayer.release();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mediaPlayer.start();
		mediaPlayer.seekTo(position);
	}

	@Override
	public void onClick(View v) {
		if (v == stopButton) {
			mediaPlayer.pause();
		} else if (v == startButton) {
			mediaPlayer.start();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (mediaPlayer.isPlaying()) {
				position = (int) (event.getX() * mediaPlayer.getDuration() / theView
						.getWidth());
				Log.v("SEEK", "" + position);
				mediaPlayer.seekTo(position);
			}
		}
		return true;
	}

}
