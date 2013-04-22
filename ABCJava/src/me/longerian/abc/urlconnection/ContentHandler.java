package me.longerian.abc.urlconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;

public class ContentHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			URL u = new URL("http://www.baidu.com/");
			URLConnection uc = u.openConnection();
			Class[] types = {String.class, Reader.class, InputStream.class};
			Object o = uc.getContent(types);
			
			if(o instanceof String) {
				System.out.println(o);
			} else if(o instanceof Reader) {
				System.out.println("Reader");
				int c;
				Reader r = (Reader) o;
				while ((c = r.read()) != -1) System.out.print((char) c);
			} else if(o instanceof InputStream) {
				System.out.println("InputStream");
				int c;
				InputStream in = (InputStream) o;
				while ((c = in.read()) != -1) System.out.print((char) c);
			} else if(o == null) {
				System.out.println("none of the requested types were available.");
			} else {
				System.out.println("unexpected typed " + o.getClass());
			}
		} catch (MalformedInputException mie) {
			mie.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
