package me.longerian.abc.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ZipTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//			try {
//				File zipFile = ZipUtils.zip(new File[] { } , new File("apkss"));
//				if(zipFile != null) {
//					System.out.println(zipFile.getAbsolutePath());
//				} else {
//					System.out.println("zipFile is null");
//				}
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			try {
				List<File> files = ZipUtils.unZip(new File("apks"), new File("apksss"));
				for(File f : files) {
					System.out.println(f.getAbsolutePath());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
