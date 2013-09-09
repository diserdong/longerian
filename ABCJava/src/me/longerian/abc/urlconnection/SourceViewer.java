package me.longerian.abc.urlconnection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.util.zip.GZIPInputStream;

public class SourceViewer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
//			URL u = new URL("http://nc.mir.wdjcdn.com/files/release2/WanDouJia_2.61.0.3538_homepage.exe");
			URL u = new URL("http://www.baidu.com/");
			URLConnection uc = u.openConnection();
			InputStream raw = uc.getInputStream();
//			raw = new GZIPInputStream(raw);
			InputStream buffer = new BufferedInputStream(raw);
			Reader r = new InputStreamReader(buffer, "UTF-8");
			int c;
			while((c = r.read()) != -1) {
				System.out.print((char) c);
			}
			buffer.close();
		} catch (MalformedInputException mie) {
			mie.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
