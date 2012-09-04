package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.tencent.phonemgr.R;

public class VideoFile implements FileItem {

	public static final String THREEGP_FILE = ".3gp";
	public static final String MP4_FILE = ".mp4";
	public static final String FLV_FILE = ".flv";
	
	private boolean isDir;
	private File file;
	
	public VideoFile(File file) {
		this.file = file;
		this.isDir = false;
	}
	
	@Override
	public File getParentDir() {
		return file.getParentFile();
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public Bitmap getBitmapLogo(Context context) {
		Bitmap thumbnailBitmap = null;
		if(android.os.Build.VERSION.SDK_INT >= 5) {
				Cursor cursor = context.getContentResolver().query(
						android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 
						null, 
						android.provider.MediaStore.Video.Media.DATA + " = '" + file.getAbsolutePath() + "'", 
						null, 
						null);
				if(cursor != null && cursor.moveToFirst()) {
					long id = cursor.getLong(cursor.getColumnIndex(android.provider.MediaStore.Video.Media._ID));
					cursor.close();
					Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(
							context.getContentResolver(), 
							id,
							MediaStore.Images.Thumbnails.MICRO_KIND,
							null);
					thumbnailBitmap = bitmap;
				} else {
					thumbnailBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_video);
				}
		} else {
			thumbnailBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_video);
		}
		return thumbnailBitmap;
	}

	@Override
	public void open(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_VIEW);  
	    intent.setDataAndType(Uri.fromFile(file), "video/*");
	    activity.startActivity(intent);
	}

	@Override
	public void close() {
	}

	@Override
	public void setOnLoadListener(OnLoadListener listener) {

	}
	
	@Override
	public boolean isDir() {
		return isDir;
	}
	
	@Override
	public int compareTo(FileItem another) {
		if(another.isDir()) {
			return -1;
		} else {
			return this.getName().compareTo(another.getName());
		}
	}
	
	@Override
	public File getFile() {
		return file;
	}
	
}
