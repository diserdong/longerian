package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import com.tencent.phonemgr.R;

public class VideoFile implements FileItem {

	public static final String THREEGP_FILE = ".3gp";
	public static final String MP4_FILE = ".mp4";
	public static final String FLV_FILE = ".flv";
	
	private boolean isDir;
	private File file;
	private BitmapDrawable thumbnail;
	
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
	public Drawable getLogo(Context context) {
		//TODO cache image 
		if(android.os.Build.VERSION.SDK_INT >= 5) {
			if(thumbnail == null) {
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
					thumbnail = new BitmapDrawable(context.getResources(), bitmap);
				} else {
					thumbnail = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_video);
				}
			}
		} else {
			thumbnail = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_video);
		}
		return thumbnail;
	}

	@Override
	public void open(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_VIEW);  
	    intent.setDataAndType(Uri.fromFile(file), "video/*");
	    activity.startActivity(intent);
	}

	@Override
	public void close() {
		if(thumbnail != null) {
			if(thumbnail.getBitmap() != null) {
				thumbnail.getBitmap().recycle();
			}
		}
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

}
