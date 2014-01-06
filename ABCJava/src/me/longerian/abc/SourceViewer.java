package me.longerian.abc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

public class SourceViewer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//proxy usage one: setting system property
//		System.setProperty("http.proxyHost", "127.0.0.1");
//		System.setProperty("http.proxyPort", "8888");
		
		//proxy usage two: use Proxy
//		SocketAddress proxyAddress = new InetSocketAddress("127.0.0.1", 8888);
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
		
		try {
//			URL u = new URL("http://www.google.com/");
//			InputStream in = u.openStream();
//			in = new BufferedInputStream(in);
//			Reader r = new InputStreamReader(in);
//			int c;
//			while((c = r.read()) != -1) System.out.write(c);
//			
//			System.out.println("");
			
			URL u = new URL("http://112.90.140.215:10001/version");
			URLConnection uc = u.openConnection();
			InputStream in = uc.getInputStream();
			in = new BufferedInputStream(in);
			Reader r = new InputStreamReader(in);
			int c;
			while((c = r.read()) != -1) System.out.write(c);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
