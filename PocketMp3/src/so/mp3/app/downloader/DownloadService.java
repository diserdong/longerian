package so.mp3.app.downloader;

import java.util.LinkedList;
import java.util.Queue;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

/**
 * This is an example of implementing an application service that runs locally
 * in the same process as the application.  The {@link Controller}
 * class shows how to interact with the service. 
 *
 * <p>Notice the use of the {@link NotificationManager} when interesting things
 * happen in the service.  This is generally how background services should
 * interact with the user, rather than doing something more disruptive such as
 * calling startActivity().
 * 
 * <p>For applications targeting Android 1.5 or beyond, you may want consider
 * using the {@link android.app.IntentService} class, which takes care of all the
 * work of creating the extra thread and dispatching commands to it.
 */
public class DownloadService extends Service implements DownloadTaskListener {
	
	public static final String TARGET_URL = "url";
	public static final String TARGET_NAME = "name";
	
    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;
    
    private final class ServiceHandler extends Handler {
    	
    	public static final int QUEUED_FOR_DOWNLOAD = 1001;
    	public static final int POLLED_FOR_START = 1002;
    	
    	private Queue<DownloadTask> mTaskQueue = new LinkedList<DownloadTask>();
    	
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        
        @Override
        public void handleMessage(Message msg) {
        	switch(msg.what) {
        	case QUEUED_FOR_DOWNLOAD:
        		Bundle arguments = (Bundle)msg.obj;
                String url = arguments.getString(TARGET_URL);
                String name = arguments.getString(TARGET_NAME);
                if ((msg.arg2&Service.START_FLAG_REDELIVERY) == 0) {
                	url = "New cmd #" + msg.arg1 + ": " + url;
                } else {
                	url = "Re-delivered #" + msg.arg1 + ": " + url;
                }
                DownloadTask newTask = new DownloadTask(getApplicationContext(), arguments.getString(TARGET_URL), msg.arg1, name);
                newTask.setListener(DownloadService.this);
                Log.i("ServiceStartArguments", "Queued with #" + msg.arg1);
                mTaskQueue.add(newTask);
                if(mTaskQueue.size() == 1) {
                	Message notifyStart = mServiceHandler.obtainMessage();
                	notifyStart.what = ServiceHandler.POLLED_FOR_START;
                    mServiceHandler.sendMessage(notifyStart);
                }
        		break;
        	case POLLED_FOR_START:
        		DownloadTask queuedTask = mTaskQueue.poll();
        		if(queuedTask != null) {
        			queuedTask.startDownload();
        		}
        		break;
    		default:
    			break;
        	}
        }

    };
    
    @Override
    public void onCreate() {
        Toast.makeText(this, "service_created",
                Toast.LENGTH_SHORT).show();
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceStartArguments",
                "Starting #" + startId + ": " + intent.getExtras());
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.arg2 = flags;
        msg.obj = intent.getExtras();
        msg.what = ServiceHandler.QUEUED_FOR_DOWNLOAD;
        mServiceHandler.sendMessage(msg);
        Log.i("ServiceStartArguments", "Sending: " + msg);
        
        // For the start fail button, we will simulate the process dying
        // for some reason in onStartCommand().
        if (intent.getBooleanExtra("fail", false)) {
            // Don't do this if we are in a retry... the system will
            // eventually give up if we keep crashing.
            if ((flags&START_FLAG_RETRY) == 0) {
                // Since the process hasn't finished handling the command,
                // it will be restarted with the command again, regardless of
                // whether we return START_REDELIVER_INTENT.
                Process.killProcess(Process.myPid());
            }
        }
        
        // Normally we would consistently return one kind of result...
        // however, here we will select between these two, so you can see
        // how they impact the behavior.  Try killing the process while it
        // is in the middle of executing the different commands.
        return intent.getBooleanExtra("redeliver", false)
                ? START_REDELIVER_INTENT : START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mServiceLooper.quit();
        // Tell the user we stopped.
        Toast.makeText(DownloadService.this, "service_destroyed",
                Toast.LENGTH_SHORT).show();
    }

	@Override
    public IBinder onBind(Intent intent) {
        return null;
    }

	@Override
	public void updateProcess(int taskId, int progress) {
	}

	@Override
	public void finishDownload(int taskId) {
		if(mServiceLooper.getThread().isAlive()) {
			Message notifyStart = mServiceHandler.obtainMessage();
			notifyStart.what = ServiceHandler.POLLED_FOR_START;
			mServiceHandler.sendMessage(notifyStart);
		}
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		stopSelf(taskId);
	}

	@Override
	public void preDownload(int taskId) {
	}

	@Override
	public void errorDownload(int error) {
		
	}
	
}

