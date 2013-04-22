package me.longerian.abc.urlconnection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.sql.Date;

public class LastModified {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			URL u = new URL("http://www.baidu.com/");
			HttpURLConnection uc = (HttpURLConnection) u.openConnection();
			uc.setRequestMethod("HEAD");
			System.out.println(uc + " was last modified at " + new Date(uc.getLastModified()));
			System.out.println(uc.getResponseMessage() + "\t" + uc.getResponseCode());
		} catch (MalformedInputException mie) {
			mie.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
