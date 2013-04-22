package me.longerian.abc.urlconnection;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.util.Date;

public class HeaderViewer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			URL u = new URL("http://www.baidu.com/");
			URLConnection uc = u.openConnection();
			System.out.println("Content-type: " + uc.getContentType());
			System.out.println("Content-encoding: " + uc.getContentEncoding());
			System.out.println("Date: " + new Date(uc.getDate()));
			System.out.println("Last modified: " + new Date(uc.getLastModified()));
			System.out.println("Expiration date: " + new Date(uc.getExpiration()));
			System.out.println("Content-length: " + uc.getContentLength());
		} catch (MalformedInputException mie) {
			mie.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
