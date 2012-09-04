package com.tencent.phonemgr.filetype;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.tencent.phonemgr.utils.bitmap.ImageResizer;

public class ImageFile implements FileItem {

	public static final String JPG_FILE = ".jpg";
	public static final String PNG_FILE = ".png";
	
	private boolean isDir;
	private File file;
	
	public ImageFile(File file) {
		this.file = file;
		this.isDir = false;
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
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
						null, 
						android.provider.MediaStore.Images.Media.DATA + " = '" + file.getAbsolutePath() + "'", 
						null, 
						null);
				if(cursor != null && cursor.moveToFirst()) {
					long id = cursor.getLong(cursor.getColumnIndex(android.provider.MediaStore.Images.Media._ID));
					cursor.close();
					Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(
							context.getContentResolver(), 
							id,
							MediaStore.Images.Thumbnails.MICRO_KIND,
							null);
					thumbnailBitmap = bitmap;
				}
		}
		if(thumbnailBitmap == null) {
			thumbnailBitmap = ImageResizer.decodeSampledBitmapFromFile(file.getAbsolutePath(), 40, 40);
		}
		return thumbnailBitmap;
	}

	@Override
	public void open(Activity activity) {
	     Intent intent = new Intent(Intent.ACTION_VIEW);
	     intent.setDataAndType(Uri.fromFile(file), "image/*");
	     activity.startActivity(intent);     
	}

	@Override
	public void close() {
	}

	@Override
	public void setOnLoadListener(OnLoadListener listener) {
		
	}
	
	@Override
	public File getParentDir() {
		return file.getParentFile();
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
