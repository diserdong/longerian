package me.longerian.abcandroid.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.os.StatFs;

public class FileUtil {

	public static boolean writeToFile(File file, byte[] bytes) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		boolean bool = true;
		try {
			fos.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			bool = false;
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bool;
	}

	public static File getSDcard() {
		File file = null;
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			file = android.os.Environment.getExternalStorageDirectory();
		}
		return file;
	}

	public static long getAvbSize(File file) {
		if (file == null)
			return -1;
		if (!file.exists() || !file.isDirectory()) {
			return 0;
		}

		StatFs stat = new StatFs(file.getAbsolutePath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long AvailableExternalMemorySize = availableBlocks * blockSize;
		// long TotalExternalMemorySize = totalBlocks * blockSize;
		return AvailableExternalMemorySize;
	}

	public static long getAvailableInternalSpace() {
		return getAvailableSpace(Environment.getDataDirectory().getPath());
	}

	public static long getTotalInternalSpace() {
		return getTotalSpace(Environment.getDataDirectory().getPath());
	}

	public static long getAvailableExternalSpace() {
		return getAvailableSpace(Environment.getExternalStorageDirectory()
				.getPath());
	}

	public static long getTotalExternalSpace() {
		return getTotalSpace(Environment.getExternalStorageDirectory()
				.getPath());
	}

	public static long getAvailableSpace(String path) {
		StatFs stat = new StatFs(path);
		long spaceInByte = (long) stat.getBlockSize()
				* (long) stat.getAvailableBlocks();
		long spaceInMega = spaceInByte / (1024 * 1024);
		return spaceInMega;
	}

	public static long getTotalSpace(String path) {
		StatFs stat = new StatFs(path);
		long spaceInByte = (long) stat.getBlockSize()
				* (long) stat.getBlockCount();
		long spaceInMega = spaceInByte / (1024 * 1024);
		return spaceInMega;
	}
	
}
