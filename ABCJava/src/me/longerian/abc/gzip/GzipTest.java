package me.longerian.abc.gzip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		/**
		try {
			gzipFile("D:\\main.log", "D:\\gzip_main.log");
			ungzipFile("D:\\gzip_main.log", "D:\\ungzip_main.log");
		} catch (IOException e) {
			e.printStackTrace();
		}
		**/
		try {
//			byte[] gzipped = Compressor.compress("this is a string before compressingcompressingcompressing".getBytes());
			byte[] gzipped = Compressor.compress("jfdouernklkljfjpapserldl;fkeopwr     dfwserthjterkjkjkjhuuikkk".getBytes());
			System.out.println(new String(gzipped) + " length " + gzipped.length);
			byte[] ungizpped = Compressor.decompress(gzipped);
			System.out.println(new String(ungizpped) + " length " + ungizpped.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void gzipFile(String src, String dst) throws IOException {
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(dst);
		GZIPOutputStream gfos = new GZIPOutputStream(fos);
		byte[] buffer = new byte[512];
		int readed = 0;
		while((readed = fis.read(buffer)) != -1) {
			gfos.write(buffer, 0, readed);
		}
		gfos.finish();
		gfos.flush();
		fis.close();
		fos.close();
		gfos.close();
	}
	
	private static void ungzipFile(String src, String dst) throws IOException {
		FileInputStream fis = new FileInputStream(src);
		GZIPInputStream gfis = new GZIPInputStream(fis);
		FileOutputStream fos = new FileOutputStream(dst);
		byte[] buffer = new byte[512];
		int readed = 0;
		while((readed = gfis.read(buffer)) != -1) {
			fos.write(buffer, 0, readed);
		}
		fis.close();
		fos.close();
		gfis.close();
	}

}
