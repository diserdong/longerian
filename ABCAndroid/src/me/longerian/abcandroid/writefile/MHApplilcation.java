package me.longerian.abcandroid.writefile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

public class MHApplilcation extends Application {

	private static final String TAG = "HelperApplilcation";
	private final int APP_ID = 537033987;

	public void onCreate() {
		super.onCreate();
		Thread.setDefaultUncaughtExceptionHandler(new MUncaughtExphandler());
	}

	private class MUncaughtExphandler implements UncaughtExceptionHandler {

		private Map<String, String> infos = new HashMap<String, String>();  
		private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  
		
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			collectDeviceInfo(getApplicationContext());
			saveCrashInfo2File(ex);
			
			android.os.Process.killProcess(android.os.Process.myPid());  
            System.exit(1);  
		}
		
		public void collectDeviceInfo(Context ctx) {  
	        try {  
	            PackageManager pm = ctx.getPackageManager();  
	            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
	            if (pi != null) {  
	                String versionName = pi.versionName == null ? "null" : pi.versionName;  
	                String versionCode = pi.versionCode + "";  
	                infos.put("versionName", versionName);  
	                infos.put("versionCode", versionCode);  
	            }  
	        } catch (NameNotFoundException e) {  
	            Log.e(TAG, "an error occured when collect package info", e);  
	        }  
	        Field[] fields = Build.class.getDeclaredFields();  
	        for (Field field : fields) {  
	            try {  
	                field.setAccessible(true);  
	                infos.put(field.getName(), field.get(null).toString());  
	                Log.d(TAG, field.getName() + " : " + field.get(null));  
	            } catch (Exception e) {  
	                Log.e(TAG, "an error occured when collect crash info", e);  
	            }  
	        }  
	    } 
		
		private String saveCrashInfo2File(Throwable ex) {  
			ex.printStackTrace();
	        StringBuffer sb = new StringBuffer();  
	        for (Map.Entry<String, String> entry : infos.entrySet()) {  
	            String key = entry.getKey();  
	            String value = entry.getValue();  
	            sb.append(key + "=" + value + "\n");  
	        }  
	          
	        Writer writer = new StringWriter();  
	        PrintWriter printWriter = new PrintWriter(writer);  
	        ex.printStackTrace(printWriter);  
	        Throwable cause = ex.getCause();  
	        while (cause != null) {  
	            cause.printStackTrace(printWriter);  
	            cause = cause.getCause();  
	        }  
	        printWriter.close();  
	        String result = writer.toString();  
	        sb.append(result);  
	        try {  
	            long timestamp = System.currentTimeMillis();  
	            String time = formatter.format(new Date());  
	            String fileName = "crash-" + time + "-" + timestamp + ".txt";  
	            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
	                File path = Environment.getExternalStorageDirectory();
	                File file = new File(path, fileName);
	                Log.d(TAG, file.getAbsolutePath());
	                file.createNewFile();
	                FileOutputStream fos = new FileOutputStream(file);  
	                fos.write(sb.toString().getBytes());  
	                fos.close();  
	            }  
	            return fileName;  
	        } catch (Exception e) {  
	            Log.e(TAG, "an error occured while writing file...", e);  
	        }  
	        return null;  
	    }  
	}  
		
}
