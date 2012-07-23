package so.mp3.app.downloader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import so.mp3.app.IndexActivity;
import so.mp3.player.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.RemoteViews;

public class DownloadTask extends AsyncTask<String, Integer, InputStream> {

	public final static int ERROR_NONE = 0;
	public final static int ERROR_SD_NO_MEMORY = 1;
	public final static int ERROR_BLOCK_INTERNET = 2;
	public final static int ERROR_UNKONW = 3;
	
	private static final int MAX_BUFFER_SIZE = 1024; // 1kb
	public static final int DOWNLOADING = 0;
	public static final int COMPLETE = 1;
	
	private Context ctx;
	private DownloadTaskListener listener;
	private String targetUrl;
	private int taskId;
	private String fileName;
	
	private HttpURLConnection conn;
	private InputStream stream; // to read
	private ByteArrayOutputStream out; // to write

	private double fileSize;
	private double downloaded; // number of bytes downloaded
	private int status = DOWNLOADING; // status of current process
	
	private NotificationManager mNotificationManager;
	private Notification notification;

	public DownloadTask(Context context, String url, int id, String fn) {
		ctx = context;
		targetUrl = url;
		taskId = id;
		fileName = fn;
		conn = null;
		fileSize = 0;
		downloaded = 0;
		status = DOWNLOADING;
		mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void setListener(DownloadTaskListener listener) {
		this.listener = listener;
	}
	
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public boolean isOnline() {
		try {
			ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			return cm.getActiveNetworkInfo().isConnectedOrConnecting();
		} catch (Exception e) {
			return false;
		}
	}
	
	public void startDownload() {
		execute(targetUrl);
	}

	@Override
	protected InputStream doInBackground(String... url) {

		try {
			if (isOnline() == true) {
				conn = (HttpURLConnection) new URL(url[0]).openConnection();
				fileSize = conn.getContentLength();
				out = new ByteArrayOutputStream((int) fileSize);
				conn.connect();

				stream = conn.getInputStream();
				// loop with step 1kb
				while (status == DOWNLOADING) {
					byte buffer[];
					if (fileSize - downloaded > MAX_BUFFER_SIZE) {
						buffer = new byte[MAX_BUFFER_SIZE];
					} else {
						buffer = new byte[(int) (fileSize - downloaded)];
					}
					int read = stream.read(buffer);
					if (read == -1) {
						publishProgress(100);
						break;
					}
					// writing to buffer
					out.write(buffer, 0, read);
					downloaded += read;
					// update progress bar
					publishProgress((int) ((downloaded / fileSize) * 100));
				}// end of while
				if (status == DOWNLOADING) {
					status = COMPLETE;
				}
				return (InputStream) new ByteArrayInputStream(out
								.toByteArray());
			}
			else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onProgressUpdate(Integer... changed) {
		showNotification(taskId, changed[0], fileName);
	}

	@Override
	protected void onPreExecute() {
		if(listener != null) {
			listener.preDownload(taskId);
		}
		showNotification(taskId, 0, fileName);
	}

	@Override
	protected void onPostExecute(InputStream result) {
		if (result != null) {
			if(listener != null) {
				saveDownloadFile(result);
				listener.finishDownload(taskId);
			}
		}
		hideNotification(taskId);
	}
	
	public static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	public static final String DOWNLOAD_ROOT = SDCARD_ROOT + "PocketMp3/Downloads/";
    public static final String ACTION_VIEW_DOWNLOAD = "so.mp3.app.IndexActivity.index"; 
	
	private boolean saveDownloadFile(InputStream result) {
		File file = new File(DOWNLOAD_ROOT, fileName);
		return FileUtil.saveStreamAsFile(result, file);
	}
	
	private void showNotification(int notificationId, int progress, String title) {
		String tickerText = "Download for " + title + " started";
		int icon = android.R.drawable.stat_sys_download;
		if(notification == null) {
			Intent notificationIntent = new Intent(ACTION_VIEW_DOWNLOAD);
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent contentIntent1 = PendingIntent.getActivity(ctx, 0, notificationIntent, 0);
			
			notification = new Notification(icon, tickerText, System.currentTimeMillis());
			notification.flags|=Notification.FLAG_ONGOING_EVENT;
			notification.contentIntent=contentIntent1;
		}
		RemoteViews contentView = new RemoteViews(ctx.getPackageName(), R.layout.downloader_progress_notification);
		contentView.setTextViewText(R.id.notification_title, title);
		contentView.setTextViewText(R.id.notification_percent, progress + "%");
		contentView.setProgressBar(R.id.notification_progress, 100, progress, false);
		notification.contentView = contentView;
		mNotificationManager.notify(notificationId, notification);
	}
	
	private void hideNotification(int notificationId) {
		mNotificationManager.cancel(notificationId);
	}
	
}