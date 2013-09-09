package me.longerian.abcandroid.processtextview;

import java.util.List;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class ProgressTextViewActivity extends Activity {

	private String TAG = "ProgressTextViewActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progresstext);
		ProgressTextView text = (ProgressTextView) findViewById(R.id.text);
		text.startProgress();
		
		Handler handler = new Handler();
		handler.postDelayed(delay, 3000);
	}
	
	private Runnable delay = new Runnable() {
		
		@Override
		public void run() {
			ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	        List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(2, ActivityManager.RECENT_WITH_EXCLUDED);
	        for(RecentTaskInfo rti : recentTasks) {
	        	Log.d(TAG, rti.baseIntent.toString());
//	        	Log.d(TAG, rti.origActivity.flattenToString());
	        }
			RecentTaskInfo recentTask = recentTasks.get(1); // task 0 is current task
			Intent restartTaskIntent = new Intent(recentTask.baseIntent);
			
			final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		    am.moveTaskToFront(recentTask.id, ActivityManager.MOVE_TASK_WITH_HOME);
		    
//		    if (restartTaskIntent != null) {
//		        restartTaskIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
//		        try {
//		        	startActivity(restartTaskIntent);
//		        } catch (ActivityNotFoundException e) {
//		            Log.w(TAG, "Unable to launch recent task", e);
//		        }
//		    }
		}
	};
	
}
