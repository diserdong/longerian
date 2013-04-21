package me.longerian.abcandroid.dialog;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;

public class DialogStyleActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make us non-modal, so that others can receive touch events.
        getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);
        // ...but notify us that it happened.
        getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        // Note that flag changes must happen *before* the content view is set.
        setContentView(R.layout.activity_dialog_style);
    }
	
	@Override
	  public boolean onTouchEvent(MotionEvent event) {
	    // If we've received a touch notification that the user has touched
	    // outside the app, finish the activity.
	    if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
	      finish();
	      return true;
	    }

	    // Delegate everything else to Activity.
	    return super.onTouchEvent(event);
	  }
	
}
