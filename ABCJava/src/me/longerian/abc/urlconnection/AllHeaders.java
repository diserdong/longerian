package me.longerian.abc.urlconnection;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.util.Date;

public class AllHeaders {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			URL u = new URL("http://www.baidu.com/");
			URLConnection uc = u.openConnection();
			for(int i = 1; ; i++) {
				String header = uc.getHeaderField(i);
				if(header == null) break;
				System.out.println(uc.getHeaderFieldKey(i) + ": " + header);
			}
		} catch (MalformedInputException mie) {
			mie.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
