package me.longerian.abc.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	private static final String TAG = "ZipUtils";
	private static final int BUFFER_SIZE = 8192;

	public static File zip(File[] files, File zipFile)
			throws FileNotFoundException, IOException {
		if (files == null || files.length == 0 || zipFile == null) {
			return null;
		}
		BufferedInputStream origin = null;
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
				new FileOutputStream(zipFile)));
		try {
			byte data[] = new byte[BUFFER_SIZE];
			for (int i = 0; i < files.length; i++) {
				FileInputStream fi = new FileInputStream(files[i]);
				origin = new BufferedInputStream(fi, BUFFER_SIZE);
				try {
					ZipEntry entry = new ZipEntry(files[i].getName());
					out.putNextEntry(entry);
					int count;
					while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
						out.write(data, 0, count);
					}
					out.flush();
				} finally {
					if (origin != null) {
						try {
							origin.close();
						} catch (IOException e) {
						}
					}
				}
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return zipFile;
	}

	public static List<File> unZip(File zipFile, File destDir)
			throws FileNotFoundException, IOException {
		List<File> files = new ArrayList<File>();
		if(zipFile == null || zipFile.isDirectory() || destDir == null || destDir.isFile()) {
			return files;
		}
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFile));
		BufferedOutputStream dest = null;
		ZipInputStream zis = new ZipInputStream(bis);
		ZipEntry entry;
		try {
			while ((entry = zis.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					File childDir = new File(destDir, entry.getName());
					childDir.mkdirs();
				} else {
					File destFile = new File(destDir, entry.getName());
					if (!destFile.getParentFile().exists()) {
						destFile.getParentFile().mkdirs();
					}
					int count;
					byte data[] = new byte[BUFFER_SIZE];
					FileOutputStream fos = new FileOutputStream(destFile);
					try {
						dest = new BufferedOutputStream(fos, BUFFER_SIZE);
						while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
							dest.write(data, 0, count);
						}
						dest.flush();
					} finally {
						if(dest != null) {
							try {
								dest.close(); 
							} catch (IOException e) {
							}
						}
					}
					files.add(destFile);
				}
			}
		} finally {
			if (zis != null) {
				try {
					zis.close();
				} catch (IOException e) {
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
		}
		return files;
	}

	//TODO not ok
	public static void extractFile(String zipFilePath, String dstDirPath,
			String targetFileName) {
		ZipInputStream zin = null;
		try {
			zin = new ZipInputStream(new FileInputStream(zipFilePath));
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.getName().endsWith(targetFileName)) {
//					String path = dstDirPath + File.separator + ze.getName();
//					File parent = new File(path).getParentFile();
//					if (!parent.exists()) {
//						parent.mkdirs();
//					}
					FileOutputStream fout = new FileOutputStream(dstDirPath
							+ File.separator + targetFileName, false);
					byte data[] = new byte[BUFFER_SIZE];
					BufferedInputStream bi = new BufferedInputStream(zin);
					BufferedOutputStream br = new BufferedOutputStream(fout);
					try {
						for (int c = bi.read(data); c != -1; c = bi.read(data)) {
							br.write(data, 0, c);
						}
						zin.closeEntry();
					} finally {
						br.close();
					}
					break;
				} else {
					continue;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				zin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void unZip(String zipFilePath, String destDir)
			throws ZipException, IOException {
				destDir = destDir.endsWith("/") ? destDir : destDir + "/";
				BufferedInputStream bis=new BufferedInputStream(new FileInputStream(zipFilePath));
				BufferedOutputStream dest = null;
				ZipInputStream zis = new ZipInputStream(bis);
				ZipEntry entry;
				while((entry = zis.getNextEntry()) != null) {
					if (entry.isDirectory()) {
						File childDir=new File(destDir+entry.getName());
						childDir.mkdirs();
					}else{
						File destFile=new File(destDir+entry.getName());
						if (!destFile.getParentFile().exists()){
							destFile.getParentFile().mkdirs();
						}
						int count;
						byte data[] = new byte[BUFFER_SIZE];
						FileOutputStream fos = new FileOutputStream(destFile);
						dest = new BufferedOutputStream(fos, BUFFER_SIZE);
						while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
							dest.write(data, 0, count);
						}
						dest.flush();
						dest.close();
					}
				}
					zis.close();
					bis.close();
			}
}
