package me.longerian.abc.urlconnection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;

public class SourceViewer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			URL u = new URL("http://www.baidu.com/");
			URLConnection uc = u.openConnection();
			InputStream raw = uc.getInputStream();
			InputStream buffer = new BufferedInputStream(raw);
			Reader r = new InputStreamReader(buffer);
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
